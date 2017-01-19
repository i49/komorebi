package com.github.i49.komorebi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.i49.komorebi.publication.MediaType;
import com.github.i49.komorebi.publication.Metadata;
import com.github.i49.komorebi.publication.Publication;
import com.github.i49.komorebi.publication.PublicationResource;
import com.github.i49.komorebi.publication.PublicationWriter;
import com.github.i49.komorebi.publication.PublicationWriterFactory;

/**
 * Publication generator that generates a publication from a set of sources.
 */
public class PublicationGenerator {
	
	private boolean ascending = true;
	
	public void setDocumentOrder(boolean ascending) {
		this.ascending = ascending;
	}
	
	public void generate(Path sourceDir, Path target) throws Exception {
		if (!Files.isDirectory(sourceDir)) {
			throw new NotDirectoryException(sourceDir.toString());
		}
		if (target == null) {
			target = getDefaultTargetPath(sourceDir);
		}
		Publication publication = generateBook(sourceDir);
		writeBook(target, publication);
	}
	
	private Publication generateBook(Path sourceDir) throws IOException {
		List<Path> sources = searchSources(sourceDir, this.ascending);
		Metadata metadata = loadMetadata(sourceDir);
		Publication publication = new Publication(metadata);
		for (Path source: sources) {
			Path filename = source.getFileName();
			PublicationResource resource = new PublicationResource(filename.toString(), MediaType.APPLICATION_XHTML_XML);
			publication.getResources().add(resource);
		}
		return publication;
	}
	
	private Metadata loadMetadata(Path sourceDir) throws IOException {
		Path path = sourceDir.resolve("metadata.yml");
		if (Files.exists(path)) {
			try (InputStream stream = Files.newInputStream(path)) {
				return Metadata.load(stream);
			}
		} else {
			return new Metadata();
		}
	}
	
	private List<Path> searchSources(Path directory, boolean ascending) throws IOException {
		List<Path> sources = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.txt")) {
			for (Path path : stream) {
				if (!Files.isDirectory(path)) {
					sources.add(path);
				}
			}
		}
		if (ascending) {
			Collections.sort(sources);
		} else {
			Collections.sort(sources, Collections.reverseOrder());
		}
		return sources;
	}
	
	private void writeBook(Path target, Publication publication) throws Exception {
		PublicationWriterFactory factory = new PublicationWriterFactory();
		try (PublicationWriter writer = factory.createWriter(Files.newOutputStream(target))) {
			writer.write(publication);
		}
	}

	private static Path getDefaultTargetPath(Path sourceDir) {
		String filename = sourceDir.getFileName().toString();
		Path parent = sourceDir.getParent();
		return parent.resolve(filename + ".epub");
	}
}

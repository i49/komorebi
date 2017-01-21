package com.github.i49.komorebi.generator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.i49.komorebi.common.MediaType;
import com.github.i49.komorebi.common.MediaTypes;
import com.github.i49.komorebi.common.Order;
import com.github.i49.komorebi.publication.ContentProvider;
import com.github.i49.komorebi.publication.Metadata;
import com.github.i49.komorebi.publication.Publication;
import com.github.i49.komorebi.publication.PublicationResource;
import com.github.i49.komorebi.publication.PublicationWriter;
import com.github.i49.komorebi.publication.PublicationWriterFactory;
import com.github.i49.komorebi.publication.StreamContentProvider;

/**
 * Publication generator that generates a publication from a set of sources.
 */
public class PublicationGenerator {
	
	private Order order = Order.ASCENDING;
	private static final String METADATA_FILENAME = "metadata.yaml";
	
	public void setDocumentOrder(Order order) {
		this.order = order;
	}
	
	public void generate(Path sourceDir, Path target) throws Exception {
		if (!Files.isDirectory(sourceDir)) {
			throw new NotDirectoryException(sourceDir.toString());
		}
		if (target == null) {
			target = getDefaultTargetPath(sourceDir);
		}
		doGenerate(sourceDir, target);
	}
	
	private void doGenerate(Path sourceDir, Path target) throws Exception {
		Publication publication = buildPublication(sourceDir);
		writePublication(target, publication);
	}
	
	private Metadata loadMetadata(Path sourceDir) throws IOException {
		Path path = sourceDir.resolve(METADATA_FILENAME);
		if (Files.exists(path)) {
			try (InputStream stream = Files.newInputStream(path)) {
				return Metadata.load(stream);
			}
		} else {
			return new Metadata();
		}
	}
	
	private Publication buildPublication(Path sourceDir) throws IOException {
	
		URI baseURI = sourceDir.toUri();
		Metadata metadata = loadMetadata(sourceDir);
		Publication publication = new Publication(metadata);
		ContentProvider provider = new StreamContentProvider(baseURI);
		
		List<URI> pages = new ArrayList<>();
		Files.walk(sourceDir).filter(Files::isRegularFile).forEach(path->{
			URI uri = path.toUri();
			MediaType mediaType = MediaTypes.guess(uri);
			if (mediaType == null) {
				return;
			}
			URI identifier = baseURI.relativize(uri);
			PublicationResource resource = createResource(identifier, mediaType, provider);
			publication.getResources().add(resource);
			if (mediaType == MediaType.TEXT_HTML || mediaType == MediaType.APPLICATION_XHTML_XML) {
				pages.add(identifier);
			}
		});
		
		return addPages(publication, pages);
	}
	
	private PublicationResource createResource(URI identifier, MediaType mediaType, ContentProvider provider) {
		PublicationResource resource = new PublicationResource(identifier, mediaType);
		resource.setContentProvider(provider);
		return resource;
	}
	
	private Publication addPages(Publication publication, List<URI> pages) {
		if (this.order == Order.ASCENDING) {
			Collections.sort(pages);
		} else {
			Collections.sort(pages, Collections.reverseOrder());
		}
		publication.getPages().addAll(pages);
		return publication;
	}
	
	private void writePublication(Path target, Publication publication) throws Exception {
		PublicationWriterFactory factory = new PublicationWriterFactory();
		try (PublicationWriter writer = factory.createWriter(Files.newOutputStream(target))) {
			writer.write(publication);
		}
	}

	private static Path getDefaultTargetPath(Path sourceDir) {
		String filename = sourceDir.getFileName().toString();
		Path parent = sourceDir.toAbsolutePath().getParent();
		return parent.resolve(filename + ".epub");
	}
}

package com.github.i49.komorebi;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
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
		doGenerate(sourceDir, target);
	}
	
	private void doGenerate(Path sourceDir, Path target) throws Exception {
		Publication publication = buildPublication(sourceDir);
		writePublication(target, publication);
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
	
	private Publication buildPublication(Path sourceDir) throws IOException {
	
		Metadata metadata = loadMetadata(sourceDir);
		Publication publication = new Publication(metadata);
		
		List<PublicationResource> topLevel = new ArrayList<>();
		Files.walk(sourceDir).filter(Files::isRegularFile).forEach(path->{
			Path relativePath = sourceDir.relativize(path);
			MediaType mediaType = guessMediaType(relativePath);
			if (mediaType == null) {
				return;
			}
			if (mediaType == MediaType.TEXT_HTML || mediaType == MediaType.APPLICATION_XHTML_XML) {
				PublicationResource resource = createTopLevelResource(relativePath, mediaType);
				topLevel.add(resource);
				publication.getTopLevelResources().add(resource);
			} else {
				PublicationResource resource = createSupportResource(relativePath, mediaType);
				publication.getSupportResources().add(resource);
			}
		});
		
		return publication;
	}
	
	private PublicationResource createTopLevelResource(Path path, MediaType mediaType) {
		PublicationResource resource = new PublicationResource(getResourceName(path), mediaType);
		return resource;
	}
	
	private PublicationResource createSupportResource(Path path, MediaType mediaType) {
		PublicationResource resource = new PublicationResource(getResourceName(path), mediaType);
		return resource;
	}
	
	private static String getResourceName(Path path) {
		String string = path.toString();
		return string.replaceAll("\\\\", "/");
	}
	
	private void writePublication(Path target, Publication publication) throws Exception {
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
	
	private static MediaType guessMediaType(Path path) {
		String filename = path.getFileName().toString();
		int lastIndex = filename.lastIndexOf(".");
		if (lastIndex >= 0) {
			String extension = filename.substring(lastIndex + 1);
			return guessMediaTypeByFileExtension(extension);
		}
		return null;
	}
	
	private static MediaType guessMediaTypeByFileExtension(String extension) {
		MediaType mediaType = null;
		switch (extension.toLowerCase()) {
		case "htm":
		case "html":
			mediaType = MediaType.TEXT_HTML;
			break;
		case "xhtml":
			mediaType = MediaType.APPLICATION_XHTML_XML;
			break;
		case "css":
			mediaType = MediaType.TEXT_CSS;
			break;
		case "gif":
			mediaType = MediaType.IMAGE_GIF;
			break;
		case "jpg":
		case "jpeg":
			mediaType = MediaType.IMAGE_JPEG;
			break;
		case "png":
			mediaType = MediaType.IMAGE_PNG;
			break;
		case "svg":
			mediaType = MediaType.IMAGE_SVG_XML;
			break;
		}
		return mediaType;
	}
}

package com.github.i49.spine.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.i49.spine.api.AuxiliaryResource;
import com.github.i49.spine.api.ContentDocument;
import com.github.i49.spine.api.Epub;
import com.github.i49.spine.api.CoreMediaType;
import com.github.i49.spine.api.Metadata;
import com.github.i49.spine.api.Publication;
import com.github.i49.spine.api.PublicationResourceFactory;

/**
 * A builder to build a publication.
 */
class PublicationBuilder {

	private final Path sourceDir;
	private final URI baseURI;
	private Order order = Order.ASCENDING;
	private Publication publication;
	private final PublicationResourceFactory factory;

	private static final String METADATA_FILENAME = "metadata.yaml";
	private static final Pattern COVER_IMAGE_PATTERN = Pattern.compile("cover.(png|gif|jpg|jpeg)");
	
	PublicationBuilder(Path sourceDir) {
		this.sourceDir = sourceDir;
		this.baseURI = sourceDir.toUri();
		this.factory = Epub.createResourceFactory();
	}
	
	void setDocumentOrder(Order order) {
		this.order = order;
	}
	
	Publication build() throws IOException {
		this.publication = Epub.createPublication();
		loadMetadata(this.publication.getMetadata());
		addResources();
		sortContentDocuments(this.order);
		return publication;
	}

	private void loadMetadata(Metadata metadata) throws IOException {
		Path path = this.sourceDir.resolve(METADATA_FILENAME);
		if (Files.exists(path)) {
			MetadataLoader loader = MetadataLoader.from(path);
			loader.loadTo(metadata);
		}
	}
	
	private void addResources() throws IOException {
		Files.walk(this.sourceDir).filter(Files::isRegularFile).forEach(path->{
			addResource(path.toUri());
		});
	}
	
	private void addResource(URI location) {
		CoreMediaType mediaType = CoreMediaType.guessMediaType(location);
		if (mediaType == null) {
			return;
		}
		
		URI identifier = this.baseURI.relativize(location);
		if (mediaType == CoreMediaType.APPLICATION_XHTML_XML) {
			ContentDocument document = factory.createXhtmlContentDocument(identifier, location);
			document.setLinear(true);
			this.publication.getContentList().add(document);
		} else {
			AuxiliaryResource resource = factory.createAuxiliaryResource(identifier, mediaType, location);
			this.publication.getAuxiliaryResources().add(resource);
			Matcher m = COVER_IMAGE_PATTERN.matcher(identifier.toString());
			if (m.matches()) {
				this.publication.setCoverImage(resource);
			}
		}
	}
	
	protected void sortContentDocuments(Order order) {
		List<ContentDocument> documents = this.publication.getContentList();
		if (order == Order.ASCENDING) {
			Collections.sort(documents);
		} else {
			Collections.sort(documents, Collections.reverseOrder());
		}
	}
}

package com.github.i49.spine.generator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.i49.spine.common.MediaType;
import com.github.i49.spine.common.MediaTypes;
import com.github.i49.spine.common.Order;
import com.github.i49.spine.publication.Chapter;
import com.github.i49.spine.publication.ContentProvider;
import com.github.i49.spine.publication.Metadata;
import com.github.i49.spine.publication.Publication;
import com.github.i49.spine.publication.PublicationResource;
import com.github.i49.spine.publication.StreamContentProvider;

/**
 * A builder to build a publication.
 */
class PublicationBuilder {

	private final Path sourceDir;
	private final URI baseURI;
	private Order order = Order.ASCENDING;
	private ContentProvider contentProvider;
	private Publication publication;
	private List<Chapter> chapters = new ArrayList<>();

	private static final String METADATA_FILENAME = "metadata.yaml";
	private static final Pattern COVER_IMAGE_PATTERN = Pattern.compile("cover.(png|gif|jpg|jpeg)");
	
	private static final Set<MediaType> docTypes;
	
	static {
		docTypes = new HashSet<>();
		docTypes.add(MediaType.TEXT_HTML);
		docTypes.add(MediaType.APPLICATION_XHTML_XML);
	}
	
	PublicationBuilder(Path sourceDir) {
		this.sourceDir = sourceDir;
		this.baseURI = sourceDir.toUri();
		this.contentProvider = new StreamContentProvider(this.baseURI);
	}
	
	void setDocumentOrder(Order order) {
		this.order = order;
	}
	
	Publication build() throws IOException {
		Metadata m = loadMetadata();
		this.publication = new Publication(m);
		addResources();
		addChapters();
		return publication;
	}

	private Metadata loadMetadata() throws IOException {
		Path path = this.sourceDir.resolve(METADATA_FILENAME);
		if (Files.exists(path)) {
			MetadataLoader loader = new MetadataLoader();
			return loader.load(path);
		} else {
			return new Metadata();
		}
	}
	
	private void addResources() throws IOException {
		Files.walk(this.sourceDir).filter(Files::isRegularFile).forEach(path->{
			addResource(path.toUri());
		});
	}
	
	private PublicationResource addResource(URI location) {
		MediaType mediaType = MediaTypes.guess(location);
		if (mediaType == null) {
			return null;
		}
		
		URI identifier = this.baseURI.relativize(location);
		PublicationResource resource = new PublicationResource(identifier, mediaType);
		resource.setContentProvider(this.contentProvider);
		
		this.publication.getResources().add(resource);
		
		if (mediaType == MediaType.TEXT_HTML || mediaType == MediaType.APPLICATION_XHTML_XML) {
			this.chapters.add(new Chapter(resource));
		}
		
		Matcher m = COVER_IMAGE_PATTERN.matcher(identifier.toString());
		if (m.matches()) {
			this.publication.setCoverImageResource(resource);
		}

		return resource;
	}
	
	private void addChapters() {
		Collections.sort(this.chapters, new ChapterComparator());
		this.publication.getChapters().addAll(this.chapters);
	}
	
	private class ChapterComparator implements Comparator<Chapter> {
		@Override
		public int compare(Chapter c1, Chapter c2) {
			int result = c1.getIdentifier().compareTo(c2.getIdentifier()); 
			return order == Order.ASCENDING ? result : -result;
		}
	}
}

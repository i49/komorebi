package com.github.i49.komorebi.publication;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A publication.
 */
public class Publication {

	private final Metadata metadata;

	private final Set<PublicationResource> resources = new HashSet<>();
	private final List<URI> pages = new ArrayList<>();
	
	private PublicationResource coverImageResource;
	
	public Publication() {
		this(new Metadata());
	}
	
	public Publication(Metadata metadata) {
		this.metadata = metadata;
	}
	
	public Metadata getMetadata() {
		return metadata;
	}
	
	public Set<PublicationResource> getResources() {
		return resources;
	}
	
	public List<URI> getPages() {
		return pages;
	}
	
	/**
	 * Returns a resource that represents a cover image.
	 * @return the cover image resource.
	 */
	public PublicationResource getCoverImageResource() {
		return coverImageResource;
	}

	public void setCoverImageResource(PublicationResource resource) {
		this.coverImageResource = resource;
	}	
}

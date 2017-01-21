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
	private final List<PublicationResource> topLevelResources = new ArrayList<>();
	private final List<PublicationResource> supportResources = new ArrayList<>();
	
	private final Set<PublicationResource> resources = new HashSet<>();
	private final List<URI> pages = new ArrayList<>();
	
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
	
	public List<PublicationResource> getTopLevelResources() {
		return topLevelResources;
	}
	
	public List<PublicationResource> getSupportResources() {
		return supportResources;
	}
}

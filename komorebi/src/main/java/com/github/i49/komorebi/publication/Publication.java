package com.github.i49.komorebi.publication;

import java.util.ArrayList;
import java.util.List;

/**
 * A publication.
 */
public class Publication {

	private final Metadata metadata;
	private final List<PublicationResource> resources = new ArrayList<>();
	
	public Publication() {
		this(new Metadata());
	}
	
	public Publication(Metadata metadata) {
		this.metadata = metadata;
	}
	
	public Metadata getMetadata() {
		return metadata;
	}
	
	public List<PublicationResource> getResources() {
		return resources;
	}
}

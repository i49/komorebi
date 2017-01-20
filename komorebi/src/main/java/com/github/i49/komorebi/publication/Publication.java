package com.github.i49.komorebi.publication;

import java.util.ArrayList;
import java.util.List;

/**
 * A publication.
 */
public class Publication {

	private final Metadata metadata;
	private final List<PublicationResource> topLevelResources = new ArrayList<>();
	private final List<PublicationResource> supportResources = new ArrayList<>();
	
	public Publication() {
		this(new Metadata());
	}
	
	public Publication(Metadata metadata) {
		this.metadata = metadata;
	}
	
	public Metadata getMetadata() {
		return metadata;
	}
	
	public List<PublicationResource> getTopLevelResources() {
		return topLevelResources;
	}
	
	public List<PublicationResource> getSupportResources() {
		return supportResources;
	}
}

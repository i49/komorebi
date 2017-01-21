package com.github.i49.komorebi.publication;

import java.net.URI;

/**
 * A chapter that references a publication resource.
 */
public class Chapter {

	private final PublicationResource resource;
	private boolean primary;
	
	public Chapter(PublicationResource resource) {
		this.resource = resource;
		this.primary = true;
	}
	
	public PublicationResource getResource() {
		return resource;
	}
	
	public URI getIdentifier() {
		return getResource().getIdentifier();
	}
	
	public boolean isPrimary() {
		return primary;
	}
	
	public void setPrimary(boolean linear) {
		this.primary = linear;
	}
	
	@Override
	public String toString() {
		return getIdentifier().toString();
	}
}

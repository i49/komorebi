package com.github.i49.spine.api;

import java.net.URI;

/**
 * A resource that contains content or instructions that contribute to the logic and rendering of 
 * a publication.
 */
public interface PublicationResource extends Comparable<PublicationResource> {

	URI getIdentifier();
	
	CoreMediaType getMediaType();
}

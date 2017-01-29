package com.github.i49.spine.api;

import java.util.List;
import java.util.Set;

/**
 * A publication.
 */
public interface Publication {

	/**
	 * Return the metadata of this publication.
	 * @return the metadata of this publication.
	 */
	Metadata getMetadata();
	
	/**
	 * Return the list of content documents.
	 * @return the list of content documents.
	 */
	List<ContentDocument> getContentList();

	/**
	 * Returns auxiliary resources that help the rendering of this publication.
	 * @return the set of auxiliary resources.
	 */
	Set<AuxiliaryResource> getAuxiliaryResources();

	/**
	 * Returns a resource that represents a cover image.
	 * @return the cover image resource.
	 */
	PublicationResource getCoverImage();

	/**
	 * Assigns a resource that represents a cover image.
	 * @param resource the resource that represents a a cover image.
	 */
	void setCoverImage(PublicationResource resource);
}

package com.github.i49.spine.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.i49.spine.api.AuxiliaryResource;
import com.github.i49.spine.api.ContentDocument;
import com.github.i49.spine.api.Metadata;
import com.github.i49.spine.api.Publication;
import com.github.i49.spine.api.PublicationResource;

/**
 * An implementation of {@link Publication}.
 */
class PublicationImpl implements Publication {

	private final Metadata metadata = new Metadata();
	private final Set<AuxiliaryResource> auxiliaryResources = new HashSet<>();
	private final List<ContentDocument> contents = new ArrayList<>();
	
	private PublicationResource coverImage;
	
	PublicationImpl() {
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}
	
	@Override
	public List<ContentDocument> getContentList() {
		return contents;
	}


	@Override
	public Set<AuxiliaryResource> getAuxiliaryResources() {
		return auxiliaryResources;
	}
	
	@Override
	public PublicationResource getCoverImage() {
		return coverImage;
	}

	@Override
	public void setCoverImage(PublicationResource resource) {
		this.coverImage = resource;
	}	
}

package com.github.i49.komorebi.publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublicationResource {

	private final String name;
	private final MediaType mediaType;
	
	private Optional<String> id = Optional.empty();
	private boolean linear;
	private final List<String> properties = new ArrayList<>();

	public PublicationResource(String name, MediaType mediaType) {
		if (name == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.name = name;
		this.mediaType = mediaType;
		this.id = Optional.empty();
		this.linear = true;
	}

	public String getName() {
		return name;
	}
	
	public MediaType getMediaType() {
		return mediaType;
	}
	
	public Optional<String> getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = Optional.ofNullable(id);
	}
	
	public boolean isLinear() {
		return linear;
	}
	
	public void setLinear(boolean linear) {
		this.linear = linear;
	}
	
	public List<String> getProperties() {
		return properties;
	}
}

package com.github.i49.komorebi.publication;

import java.net.URI;

import com.github.i49.komorebi.common.MediaType;
import com.github.i49.komorebi.content.Content;

/**
 * A resource that contains content or instructions that contribute to the logic and rendering of 
 * a publication.
 */
public class PublicationResource {

	private final URI identifier;
	private final MediaType mediaType;
	private ContentProvider provider;

	public PublicationResource(URI identifier, MediaType mediaType) {
		if (identifier == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.identifier = identifier;
		this.mediaType = mediaType;
	}

	public URI getIdentifier() {
		return identifier;
	}
	
	public MediaType getMediaType() {
		return mediaType;
	}
	
	public Content getContent() {
		if (this.provider == null) {
			return null;
		}
		return this.provider.getContent(getIdentifier(), getMediaType());
	}
	
	public void setContentProvider(ContentProvider provider) {
		this.provider = provider;
	}
	
	@Override
	public String toString() {
		return getIdentifier().toString();
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicationResource other = (PublicationResource) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
}

package com.github.i49.spine.core;

import java.net.URI;

import com.github.i49.spine.api.CoreMediaType;
import com.github.i49.spine.api.PublicationResource;

/**
 * A skeletal class implementing {@link PublicaionResource}.
 */
abstract class AbstractPublicationResource implements PublicationResource {

	private final URI identifier;
	private final CoreMediaType mediaType;
	
	protected AbstractPublicationResource(URI identifier, CoreMediaType mediaType) {
		if (identifier == null || mediaType == null) {
			throw new NullPointerException();
		}
		this.identifier = identifier;
		this.mediaType = mediaType;
	}

	@Override
	public URI getIdentifier() {
		return identifier;
	}
	
	@Override
	public CoreMediaType getMediaType() {
		return mediaType;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PublicationResource other = (PublicationResource)obj;
		return getIdentifier().equals(other.getIdentifier());
	}

	@Override
	public int compareTo(PublicationResource other) {
		return getIdentifier().compareTo(other.getIdentifier());
	}
}

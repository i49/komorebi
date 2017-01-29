package com.github.i49.spine.api;

import java.net.URI;

public interface PublicationResourceFactory {

	XhtmlContentDocument createXhtmlContentDocument(URI identifier, URI location);
	
	AuxiliaryResource createAuxiliaryResource(URI identifier, CoreMediaType mediaType, URI location);
}

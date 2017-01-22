package com.github.i49.komorebi.publication;

import java.net.URI;

import com.github.i49.komorebi.common.MediaType;
import com.github.i49.komorebi.content.Content;

public interface ContentProvider {

	Content getContent(URI identifier, MediaType mediaType);
}

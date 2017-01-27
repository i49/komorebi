package com.github.i49.reflow.publication;

import java.net.URI;

import com.github.i49.reflow.common.MediaType;
import com.github.i49.reflow.content.Content;

public interface ContentProvider {

	Content getContent(URI identifier, MediaType mediaType);
}

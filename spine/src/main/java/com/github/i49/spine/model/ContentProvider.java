package com.github.i49.spine.model;

import java.net.URI;

import com.github.i49.spine.common.MediaType;
import com.github.i49.spine.content.Content;

public interface ContentProvider {

	Content getContent(URI identifier, MediaType mediaType);
}

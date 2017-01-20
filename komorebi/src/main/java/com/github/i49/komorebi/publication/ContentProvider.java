package com.github.i49.komorebi.publication;

public interface ContentProvider {

	Content getContent(String name, MediaType mediaType);
}

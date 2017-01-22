package com.github.i49.komorebi.publication;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.function.Supplier;

import com.github.i49.komorebi.common.MediaType;
import com.github.i49.komorebi.content.Content;
import com.github.i49.komorebi.content.StreamContentFactory;

public class StreamContentProvider implements ContentProvider {
	
	private final URI baseURI;
	private final StreamContentFactory factory = new StreamContentFactory();
	
	public StreamContentProvider(URI baseURI) {
		this.baseURI = baseURI;
	}

	@Override
	public Content getContent(URI identifier, MediaType mediaType) {
		final URI location = baseURI.resolve(identifier);
		return factory.createContent(mediaType, new ContentSource(location));
	}
	
	private static class ContentSource implements Supplier<InputStream> {
		
		private final URI location;
		
		public ContentSource(URI location) {
			this.location = location;
		}

		@Override
		public InputStream get() {
			try {
				return location.toURL().openStream();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
}

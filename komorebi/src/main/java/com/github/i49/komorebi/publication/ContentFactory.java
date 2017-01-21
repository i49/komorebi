package com.github.i49.komorebi.publication;

import java.io.InputStream;
import java.util.function.Supplier;

import com.github.i49.komorebi.common.MediaType;

public class ContentFactory {

	public Content createContent(MediaType mediaType, Supplier<InputStream> source) {
		return new SimpleContent(mediaType, source);
	}

	private class SimpleContent implements OctetContent {
		
		private final MediaType mediaType;
		private final Supplier<InputStream> source;
		
		public SimpleContent(MediaType mediaType, Supplier<InputStream> source) {
			this.mediaType = mediaType;
			this.source = source;
		}

		@Override
		public MediaType getMediaType() {
			return mediaType;
		}

		@Override
		public InputStream openStream() {
			return source.get();
		}
	}
}

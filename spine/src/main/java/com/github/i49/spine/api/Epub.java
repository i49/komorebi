package com.github.i49.spine.api;

import java.io.OutputStream;

import com.github.i49.spine.api.spi.EpubProvider;

/**
 * Factory class for creating EPUB processing objects. 
 */
public final class Epub {

	/**
	 * Creates an instance of {@link Publication}.
	 * @return created {@link Publication} instance.
	 */
	public static Publication createPublication() {
		return EpubProvider.provider().createPublication();
	}
	
	/**
	 * Creates an instance of {@link PublicationResourceFactory}.
	 * @return created {@link PublicationResourceFactory} instance.
	 */
	public static PublicationResourceFactory createResourceFactory() {
		return EpubProvider.provider().createResourceFactory();
	}
	
	public static PublicationWriter createWriter(OutputStream stream) {
		return createWriterFactory().createWriter(stream);
	}
	
	public static PublicationWriterFactory createWriterFactory() {
		return EpubProvider.provider().createWriterFactory();
	}
	
	private Epub() {
	}
}

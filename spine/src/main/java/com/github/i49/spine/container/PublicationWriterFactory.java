package com.github.i49.spine.container;

import java.io.OutputStream;

/**
 * A factory class to create {@link PublicationWriter} instances.
 */
public class PublicationWriterFactory {

	/**
	 * Creates an instance of {@link PublicationWriter}.
	 * @param stream the stream to which a publication will be written.
	 * @return the instance of {@link PublicationWriter}.
	 */
	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3PublicationWriter(stream);
		} catch (Exception e) {
			return null;
		}
	}
}

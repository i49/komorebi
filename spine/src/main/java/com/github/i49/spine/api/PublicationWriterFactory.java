package com.github.i49.spine.api;

import java.io.OutputStream;

/**
 * A factory class to create {@link PublicationWriter} instances.
 */
public interface PublicationWriterFactory {

	/**
	 * Creates an instance of {@link PublicationWriter}.
	 * @param stream the stream to which a publication will be written.
	 * @return the instance of {@link PublicationWriter}.
	 */
	PublicationWriter createWriter(OutputStream stream);
}

package com.github.i49.spine.api;

import java.io.Closeable;

/**
 * Common interface of publication writers.
 */
public interface PublicationWriter extends Closeable {
	
	void write(Publication publication) throws Exception;
}

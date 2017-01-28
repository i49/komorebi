package com.github.i49.spine.container;

import java.io.Closeable;

import com.github.i49.spine.model.Publication;

/**
 * Common interface of publication writers.
 */
public interface PublicationWriter extends Closeable {
	
	void write(Publication publication) throws Exception;
}

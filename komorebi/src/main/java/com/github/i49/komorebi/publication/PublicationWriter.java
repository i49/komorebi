package com.github.i49.komorebi.publication;

import java.io.Closeable;

/**
 * Common interface of publication writers.
 */
public interface PublicationWriter extends Closeable {
	
	void write(Publication publication) throws Exception;
}

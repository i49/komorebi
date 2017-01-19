package com.github.i49.komorebi.epub;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

/**
 * {@link OutputStream} for each ZIP entry.
 */
class ZipEntryOutputStream extends FilterOutputStream {
	
	private final ZipOutputStream stream;

	public ZipEntryOutputStream(ZipOutputStream stream) {
		super(stream);
		this.stream = stream;
	}

	@Override
	public void close() throws IOException {
		this.stream.closeEntry();
	}
}

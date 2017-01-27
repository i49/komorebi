package com.github.i49.spine.epub;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * An implementation class of {@link Archiver}.
 */
class ZipArchiver implements Archiver {

	private final ZipOutputStream zstream;
	private boolean closed;
	
	ZipArchiver(OutputStream stream) {
		this.zstream = new ZipOutputStream(stream);
		this.closed = false;
	}

	@Override
	public void close() throws IOException {
		if (!closed) {
			this.zstream.close();
			this.closed = true;
		}
	}

	@Override
	public OutputStream append(String name) throws IOException {
		ZipEntry entry = new ZipEntry(name);
		return newEntryStream(entry, true);
	}
	
	@Override
	public void appendRaw(String name, byte[] content) throws IOException {
		ZipEntry entry = new ZipEntry(name);
		CRC32 crc = new CRC32();
		crc.update(content);
		entry.setCrc(crc.getValue());
		entry.setSize(content.length);
		try (OutputStream stream = newEntryStream(entry, false)) {
			stream.write(content);	
		}
	}
	
	private OutputStream newEntryStream(ZipEntry entry, boolean compressing) throws IOException {
		final int method = compressing ? ZipEntry.DEFLATED : ZipEntry.STORED;
		entry.setMethod(method);
		this.zstream.putNextEntry(entry);
		return new ZipEntryOutputStream();
	}

	/**
	 * {@link OutputStream} for each ZIP entry.
	 */
	private class ZipEntryOutputStream extends FilterOutputStream {
		
		public ZipEntryOutputStream() {
			super(zstream);
		}

		@Override
		public void close() throws IOException {
			zstream.closeEntry();
		}
	}
}

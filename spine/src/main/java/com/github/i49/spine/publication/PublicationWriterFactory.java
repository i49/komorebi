package com.github.i49.spine.publication;

import java.io.OutputStream;

import com.github.i49.spine.container.Epub3Writer;

public class PublicationWriterFactory {

	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3Writer(stream);
		} catch (Exception e) {
			return null;
		}
	}
}

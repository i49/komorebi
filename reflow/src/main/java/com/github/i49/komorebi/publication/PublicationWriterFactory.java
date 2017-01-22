package com.github.i49.komorebi.publication;

import java.io.OutputStream;

import com.github.i49.komorebi.epub.Epub3Writer;

public class PublicationWriterFactory {

	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3Writer(stream);
		} catch (Exception e) {
			return null;
		}
	}
}

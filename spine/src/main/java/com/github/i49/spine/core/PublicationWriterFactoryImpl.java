package com.github.i49.spine.core;

import java.io.OutputStream;

import com.github.i49.spine.api.PublicationWriter;
import com.github.i49.spine.api.PublicationWriterFactory;

public class PublicationWriterFactoryImpl implements PublicationWriterFactory {

	@Override
	public PublicationWriter createWriter(OutputStream stream) {
		try {
			return new Epub3PublicationWriter(stream);
		} catch (Exception e) {
			return null;
		}
	}
}

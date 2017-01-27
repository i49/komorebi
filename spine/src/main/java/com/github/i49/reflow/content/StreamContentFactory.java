package com.github.i49.reflow.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.reflow.common.MediaType;

public class StreamContentFactory {
	
	private final DocumentBuilder xmlDocumentBuilder;
	
	public StreamContentFactory() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			this.xmlDocumentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	public Content createContent(MediaType mediaType, Supplier<InputStream> source) {
		if (mediaType == MediaType.APPLICATION_XHTML_XML) {
			return new XmlContentImpl(mediaType, source);
		} else {
			return new OctecContentImpl(mediaType, source);
		}
	}

	protected class OctecContentImpl implements OctetContent {
		
		private final MediaType mediaType;
		private final Supplier<InputStream> source;
		
		public OctecContentImpl(MediaType mediaType, Supplier<InputStream> source) {
			this.mediaType = mediaType;
			this.source = source;
		}

		@Override
		public MediaType getMediaType() {
			return mediaType;
		}

		@Override
		public InputStream openStream() {
			return source.get();
		}
	}
	
	protected class XmlContentImpl extends OctecContentImpl implements XmlContent {

		public XmlContentImpl(MediaType mediaType, Supplier<InputStream> source) {
			super(mediaType, source);
		}

		@Override
		public Document getDocument() {
			try (InputStream s = openStream()) {
				return xmlDocumentBuilder.parse(s);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} catch (SAXException e) {
				throw new UncheckedSaxException(e);
			}
		}
	}
}

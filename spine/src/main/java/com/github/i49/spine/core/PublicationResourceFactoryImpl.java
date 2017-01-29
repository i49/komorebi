package com.github.i49.spine.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.i49.spine.api.AuxiliaryResource;
import com.github.i49.spine.api.ContentDocument;
import com.github.i49.spine.api.CoreMediaType;
import com.github.i49.spine.api.PublicationResourceFactory;
import com.github.i49.spine.api.UncheckedSaxException;
import com.github.i49.spine.api.XhtmlContentDocument;

public class PublicationResourceFactoryImpl implements PublicationResourceFactory {

	private final DocumentBuilder xmlDocumentBuilder;
	
	public PublicationResourceFactoryImpl() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			this.xmlDocumentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public XhtmlContentDocument createXhtmlContentDocument(URI identifier, URI location) {
		if (identifier == null || location == null) {
			throw new NullPointerException();
		}
		return new SimpleXhtmlContentDocument(identifier, location);
	}

	@Override
	public AuxiliaryResource createAuxiliaryResource(URI identifier, CoreMediaType mediaType, URI location) {
		if (identifier == null || mediaType == null || location == null) {
			throw new NullPointerException();
		}
		return new SimpleAuxiliaryResource(identifier, mediaType, location);
	}

	private static class SimpleAuxiliaryResource extends AbstractPublicationResource implements AuxiliaryResource {

		private final URI location;

		public SimpleAuxiliaryResource(URI identifier, CoreMediaType mediaType, URI location) {
			super(identifier, mediaType);
			this.location = location;
		}

		@Override
		public InputStream openOctetStream() throws IOException {
			return this.location.toURL().openStream();
		}
	}
	
	private static abstract class AbstractContentDocument extends SimpleAuxiliaryResource implements ContentDocument {

		private boolean linear;
		
		public AbstractContentDocument(URI identifier, CoreMediaType mediaType, URI location) {
			super(identifier, mediaType, location);
		}
		
		@Override
		public boolean isLinear() {
			return linear;
		}
		
		@Override
		public void setLinear(boolean linear) {
			this.linear = linear;
		}
	}
	
	private class SimpleXhtmlContentDocument extends AbstractContentDocument implements XhtmlContentDocument {

		public SimpleXhtmlContentDocument(URI identifier, URI location) {
			super(identifier, CoreMediaType.APPLICATION_XHTML_XML, location);
		}

		@Override
		public Document getDocument() {
			try (InputStream s = openOctetStream()) {
				return xmlDocumentBuilder.parse(s);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} catch (SAXException e) {
				throw new UncheckedSaxException(e);
			}
		}
	}
}

package com.github.i49.komorebi.epub;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.komorebi.publication.Metadata;

class PackageXmlBuilder {

	private static final String DEFAULT_NAMESPACE_URI = "http://www.idpf.org/2007/opf";
	private static final String DC_NAMESPACE_URI = "http://purl.org/dc/elements/1.1/";
	
	private static final String UNIQUE_IDENTIFIER = "pub-id";
	
	private final DocumentBuilder builder;
	private Document doc;
	
	PackageXmlBuilder(DocumentBuilder builder) {
		this.builder = builder;
	}
	
	Document build(Metadata metadata) {
		this.doc = builder.newDocument();
		this.doc.appendChild(root(metadata));
		return doc;
	}
	
	private Element root(Metadata metadata) {
		Element root = doc.createElementNS(DEFAULT_NAMESPACE_URI, "package");
		root.setAttribute("version", "3.1");
		root.setAttribute("unique-identifier", UNIQUE_IDENTIFIER);
		root.appendChild(metadata(metadata));
		root.appendChild(manifest());
		root.appendChild(spine());
		return root;
	}
	
	private Element metadata(Metadata metadata) {
		Element e = doc.createElementNS(DEFAULT_NAMESPACE_URI, "metadata");
		e.setAttribute("xmlns:dc", DC_NAMESPACE_URI);

		if (metadata.getTitle() != null) {
			Element title = createMetadata("dc:title", metadata.getTitle());
			title.setAttribute("id", "title");
			e.appendChild(title);
		}
		
		if (metadata.getIdentifier() != null) {
			Element identifer = createMetadata("dc:identifer", metadata.getIdentifier());
			identifer.setAttribute("id", UNIQUE_IDENTIFIER);
			e.appendChild(identifer);
		}

		if (metadata.getLanguage() != null) {
			Element language = createMetadata("dc:language", metadata.getLanguage().toLanguageTag());
			e.appendChild(language);
		}
		
		if (metadata.getPublisher() != null) {
			Element publisher = createMetadata("dc:publisher", metadata.getPublisher());
			e.appendChild(publisher);
		}
		
		for (String creator: metadata.getCreators()) {
			Element child = createMetadata("dc:creator", creator);
			e.appendChild(child);
		}
		return e;
	}
	
	private Element createMetadata(String name, String value) {
		Element e = doc.createElement(name);
		e.appendChild(doc.createTextNode(value));
		return e;
	}
	
	private Element manifest() {
		Element e = doc.createElementNS(DEFAULT_NAMESPACE_URI, "manifest");
		return e;
	}

	private Element spine() {
		Element e = doc.createElementNS(DEFAULT_NAMESPACE_URI, "spine");
		return e;
	}
}

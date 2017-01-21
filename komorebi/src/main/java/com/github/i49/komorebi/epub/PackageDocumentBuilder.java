package com.github.i49.komorebi.epub;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.komorebi.publication.Metadata;
import com.github.i49.komorebi.publication.Publication;
import com.github.i49.komorebi.publication.PublicationResource;

/**
 * A builder class to build a document carrying bibliographical and structural metadata 
 * about a given publication.
 */
class PackageDocumentBuilder {

	private static final String DEFAULT_NAMESPACE_URI = "http://www.idpf.org/2007/opf";
	private static final String DC_NAMESPACE_URI = "http://purl.org/dc/elements/1.1/";
	
	private static final String VERSION = "3.1";
	private static final String UNIQUE_IDENTIFIER = "publication-id";
	private static final String ID_PREFIX = "item";
	
	private final DocumentBuilder builder;
	private Document doc;
	
	private Map<URI, String> identifiers = new HashMap<>();
	private int nextNumber;
	
	PackageDocumentBuilder(DocumentBuilder builder) {
		this.builder = builder;
		this.nextNumber = 1;
	}
	
	Document build(Publication publication) {
		this.doc = builder.newDocument();
		this.doc.appendChild(root(publication));
		return doc;
	}
	
	private Element root(Publication publication) {
		Element root = doc.createElementNS(DEFAULT_NAMESPACE_URI, "package");
		root.setAttribute("version", VERSION);
		root.setAttribute("unique-identifier", UNIQUE_IDENTIFIER);
		root.appendChild(metadata(publication.getMetadata()));
		root.appendChild(manifest(publication.getResources()));
		root.appendChild(spine(publication.getResources(), publication.getPages()));
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
	
	private Element manifest(Set<PublicationResource> resources) {
		Element manifest = doc.createElementNS(DEFAULT_NAMESPACE_URI, "manifest");
		for (PublicationResource resource: resources) {
			Element item = doc.createElementNS(DEFAULT_NAMESPACE_URI, "item");
			String id = generateId();
			item.setAttribute("id", id);
			item.setAttribute("href", resource.getIdentifier().toString());
			item.setAttribute("media-type", resource.getMediaType().toString());
			manifest.appendChild(item);
			this.identifiers.put(resource.getIdentifier(), id);
		}
		return manifest;
	}

	private Element spine(Set<PublicationResource> resources, List<URI> pages) {
		Element spine = doc.createElementNS(DEFAULT_NAMESPACE_URI, "spine");
		for (URI page: pages) {
			Element itemref = doc.createElementNS(DEFAULT_NAMESPACE_URI, "itemref");
			String idref = this.identifiers.get(page);
			itemref.setAttribute("idref", idref);
			spine.appendChild(itemref);
		}
		return spine;
	}
	
	private String generateId() {
		return ID_PREFIX + this.nextNumber++;		
	}
}

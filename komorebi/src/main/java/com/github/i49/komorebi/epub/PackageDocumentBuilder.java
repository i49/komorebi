package com.github.i49.komorebi.epub;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.komorebi.publication.Chapter;
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
	
	private static final String VERSION = "3.0";
	private static final String UNIQUE_IDENTIFIER = "publication-id";
	private static final String ID_PREFIX = "item";
	
	private Publication publication;
	private Document doc;
	
	private Map<URI, String> identifiers = new HashMap<>();
	private int nextNumber;
	
	/**
	 * Construct this builder.
	 * @param publication the publication for which a publication document will be built.
	 * @param builder the XML document builder.
	 */
	PackageDocumentBuilder(Publication publication, DocumentBuilder builder) {
		this.publication = publication;
		this.doc = builder.newDocument();
		this.nextNumber = 1;
	}
	
	/**
	 * Builds the package document.
	 * @return built package document.
	 */
	Document build() {
		this.doc.appendChild(root());
		return this.doc;
	}
	
	/**
	 * Creates a package element at the root of the document.
	 * @return created element.
	 */
	private Element root() {
		Element root = doc.createElementNS(DEFAULT_NAMESPACE_URI, "package");
		root.setAttribute("version", VERSION);
		root.setAttribute("unique-identifier", UNIQUE_IDENTIFIER);
	
		root.appendChild(metadata());
		root.appendChild(manifest());
		root.appendChild(spine());
		
		return root;
	}
	
	/**
	 * Creates a metadata element.
	 * @return created metadata element.
	 */
	private Element metadata() {
		Metadata m = publication.getMetadata();
		Element e = doc.createElementNS(DEFAULT_NAMESPACE_URI, "metadata");
		e.setAttribute("xmlns:dc", DC_NAMESPACE_URI);

		if (m.getTitle() != null) {
			Element title = createMetadata("dc:title", m.getTitle());
			title.setAttribute("id", "title");
			e.appendChild(title);
		}
		
		if (m.getIdentifier() != null) {
			Element identifer = createMetadata("dc:identifier", m.getIdentifier());
			identifer.setAttribute("id", UNIQUE_IDENTIFIER);
			e.appendChild(identifer);
		}

		if (m.getLanguage() != null) {
			Element language = createMetadata("dc:language", m.getLanguage().toLanguageTag());
			e.appendChild(language);
		}
		
		if (m.getPublisher() != null) {
			Element publisher = createMetadata("dc:publisher", m.getPublisher());
			e.appendChild(publisher);
		}
		
		for (String creator: m.getCreators()) {
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
	
	/**
	 * Creates a manifest element.
	 * @return created manifest element.
	 */
	private Element manifest() {
		Element manifest = doc.createElementNS(DEFAULT_NAMESPACE_URI, "manifest");
		for (PublicationResource resource: publication.getResources()) {
			manifest.appendChild(item(resource));
		}
		return manifest;
	}
	
	/**
	 * Creates an item element in manifest. 
	 * @param resource the resource for which an item will be created.
	 * @return created item element.
	 */
	private Element item(PublicationResource resource) {
		String id = nextId();
		this.identifiers.put(resource.getIdentifier(), id);
		
		Element item = doc.createElementNS(DEFAULT_NAMESPACE_URI, "item");
		item.setAttribute("id", id);
		item.setAttribute("href", resource.getIdentifier().toString());
		item.setAttribute("media-type", resource.getMediaType().toString());
		
		String properties = itemProperties(resource);
		if (properties != null) {
			item.setAttribute("properties", properties);
		}

		return item;
	}
	
	private String itemProperties(PublicationResource resource) {
		List<String> properties = new ArrayList<>();
		if (resource == publication.getCoverImageResource()) {
			properties.add("cover-image");
		}
		if (properties.isEmpty()) {
			return null;
		}
		return properties.stream().collect(Collectors.joining(" "));
	}

	/**
	 * Creates a spine element.
	 * @return created spine element.
	 */
	private Element spine() {
		Element spine = doc.createElementNS(DEFAULT_NAMESPACE_URI, "spine");
		for (Chapter chapter: publication.getChapters()) {
			spine.appendChild(itemref(chapter));
		}
		return spine;
	}
	
	/**
	 * Creates an itemref element in spine. 
	 * @param chapter the chapter for which an itemref will be created.
	 * @return created itemref element.
	 */
	private Element itemref(Chapter chapter) {
		Element itemref = doc.createElementNS(DEFAULT_NAMESPACE_URI, "itemref");
		String idref = this.identifiers.get(chapter.getIdentifier());
		itemref.setAttribute("idref", idref);
		if (!chapter.isPrimary()) {
			itemref.setAttribute("linear", "no");
		}
		return itemref;
	}
	
	/**
	 * Generates a next id.
	 * @return generated id.
	 */
	private String nextId() {
		return ID_PREFIX + this.nextNumber++;		
	}
}

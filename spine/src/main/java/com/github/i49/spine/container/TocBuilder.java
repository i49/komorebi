package com.github.i49.spine.container;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.i49.spine.common.MediaType;
import com.github.i49.spine.content.Content;
import com.github.i49.spine.content.XmlContent;
import com.github.i49.spine.model.Toc;

class TocBuilder implements ContentProcessor {

	private final Toc.Builder builder;
	
	private static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
	private static final Pattern HEADING_PATTERN = Pattern.compile("h[1-6]"); 
	
	TocBuilder() {
		this.builder = Toc.builder();
	}
	
	@Override
	public Content processContent(Content content) {
		if (content.getMediaType() == MediaType.APPLICATION_XHTML_XML &&
			content instanceof XmlContent) {
			Document doc = ((XmlContent)content).getDocument();
			processDocument(doc);
		}
		return content;
	}
	
	public Toc getToc() {
		return builder.build();
	}
	
	private void processDocument(Document doc) {
		NodeList found = doc.getElementsByTagNameNS(XHTML_NAMESPACE, "body");
		if (found.getLength() > 0) {
			Node body = found.item(0);
			if (body.getNodeType() == Node.ELEMENT_NODE) {
				processElement((Element)body);
			}
		}
	}
	
	private void processElement(Element e) {
		String tagName = e.getTagName();
		Matcher m = HEADING_PATTERN.matcher(tagName);
		if (m.matches() && XHTML_NAMESPACE.equals(e.getNamespaceURI())) {
			appendHeading(e);
		}
		NodeList children = e.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				processElement((Element)child);
			}
		}
	}
	
	private void appendHeading(Element heading) {
		int level = Integer.valueOf(heading.getTagName().substring(1));
		String label = heading.getTextContent();
		URI location = getLocation(heading);
		this.builder.append(level, label, location);
	}
	
	private URI getLocation(Element heading) {
		String id = heading.getAttribute("id");
		if (id.isEmpty()) {
			return null;
		}
		try {
			return new URI(null, null, id);
		} catch (URISyntaxException e) {
			return null;
		}
	}
}

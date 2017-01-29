package com.github.i49.spine.core;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

class ContainerXmlBuilder {
	
	private static final String DEFAULT_NAMESPACE_URI = "urn:oasis:names:tc:opendocument:xmlns:container";
	
	private final DocumentBuilder builder;
	
	ContainerXmlBuilder(DocumentBuilder builder) {
		this.builder = builder;
	}
	
	Document build(String packageDir) {
		Document doc = builder.newDocument();
	
		Element root = doc.createElementNS(DEFAULT_NAMESPACE_URI, "container");
		root.setAttribute("version", "1.0");
		doc.appendChild(root);
		
		Element rootfiles = doc.createElementNS(DEFAULT_NAMESPACE_URI, "rootfiles");
		root.appendChild(rootfiles);
		
		Element rootfile = doc.createElementNS(DEFAULT_NAMESPACE_URI, "rootfile");
		rootfile.setAttribute("full-path", packageDir + "package.opf");
		rootfile.setAttribute("media-type", "application/oebps-package+xml");
		rootfiles.appendChild(rootfile);
		
		return doc;
	}
}

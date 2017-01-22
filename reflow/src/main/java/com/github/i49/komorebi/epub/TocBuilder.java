package com.github.i49.komorebi.epub;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.komorebi.common.MediaType;
import com.github.i49.komorebi.content.Content;
import com.github.i49.komorebi.content.XmlContent;
import com.github.i49.komorebi.publication.Chapter;
import com.github.i49.komorebi.publication.Publication;
import com.github.i49.komorebi.publication.PublicationResource;
import com.github.i49.komorebi.publication.Toc;

class TocBuilder {
	
	private final Publication pub;

	TocBuilder(Publication pub) {
		this.pub = pub;
	}
	
	Toc buildToc() {
		for (Chapter chapter: pub.getChapters()) {
			PublicationResource resource = chapter.getResource();
			if (resource.getMediaType() == MediaType.APPLICATION_XHTML_XML) {
				Content content = resource.getContent();
				if (content instanceof XmlContent) {
					parseContent((XmlContent)content);
				}
			}
		}
		return null;
	}
	
	void parseContent(XmlContent content) {
		Document doc = content.getDocument();
		Element root = doc.getDocumentElement();
	}
}

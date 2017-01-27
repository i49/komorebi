package com.github.i49.reflow.epub;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.i49.reflow.common.MediaType;
import com.github.i49.reflow.content.Content;
import com.github.i49.reflow.content.XmlContent;
import com.github.i49.reflow.publication.Chapter;
import com.github.i49.reflow.publication.Publication;
import com.github.i49.reflow.publication.PublicationResource;
import com.github.i49.reflow.publication.Toc;

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

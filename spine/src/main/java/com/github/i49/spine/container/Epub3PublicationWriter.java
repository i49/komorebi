package com.github.i49.spine.container;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.github.i49.spine.content.Content;
import com.github.i49.spine.content.OctetContent;
import com.github.i49.spine.model.Publication;
import com.github.i49.spine.model.PublicationResource;
import com.github.i49.spine.model.Toc;

/**
 * Publication writer that is defined as EPUB3 specification.
 */
class Epub3PublicationWriter implements PublicationWriter {

	private static final String MIMETYPE = "application/epub+zip";
	private static final String DEFAULT_PACKAGE_DIR = "EPUB/";
	private static final String PACKAGE_DOCUMENT_NAME = "package.opf";
	private static final int BUFFER_SIZE = 128 * 1024;

	private final Transformer transformer;
	private final Archiver archiver;
	private final List<ContentProcessor> processors;
	private TocBuilder tocBuilder;
	
	private final DocumentBuilder documentBuilder;
	
	private String packageDir = DEFAULT_PACKAGE_DIR;
	
	public Epub3PublicationWriter(OutputStream stream) throws Exception {
		this.documentBuilder = createDocumentBuilder();
		this.transformer = createTransformer();
		this.archiver = new ZipArchiver(stream);
		this.processors = new ArrayList<>();
	}

	@Override
	public void write(Publication pub) throws Exception {
		this.tocBuilder = new TocBuilder();
		addProcessor(this.tocBuilder);
		writeAll(pub);
	}

	@Override
	public void close() throws IOException {
		this.archiver.close();
	}
	
	private void writeAll(Publication pub) throws Exception {
		writeMimeType();
		writeContainerXml();
		writePackageDocument(pub);
		writeAllResources(pub);
		Toc toc = this.tocBuilder.getToc();
	}
	
	private void writeMimeType() throws IOException {
		byte[] content = MIMETYPE.getBytes();
		archiver.appendRaw("mimetype", content);
	}
	
	private void writeContainerXml() throws IOException, TransformerException {
		ContainerXmlBuilder builder = new ContainerXmlBuilder(documentBuilder); 
		Document doc = builder.build(packageDir);
		writeXmlEntry("META-INF/container.xml", doc);
	}

	private void writePackageDocument(Publication publication) throws IOException, TransformerException {
		PackageDocumentBuilder builder = new PackageDocumentBuilder(publication, documentBuilder); 
		Document doc = builder.build();
		writeXmlEntry(this.packageDir + PACKAGE_DOCUMENT_NAME, doc);
	}

	private void writeAllResources(Publication publication) throws IOException {
		for (PublicationResource resource: publication.getResources()) {
			String entryName = this.packageDir + resource.getIdentifier().toString();
			Content content = resource.getContent();
			content = processContent(content);
			if (content instanceof OctetContent) {
				writeOctetContent(entryName, (OctetContent)content);
			}
		}
	}
	
	private Content processContent(Content content) {
		for (ContentProcessor processor: this.processors) {
			content = processor.processContent(content);
		}
		return content;
	}
	
	private void writeOctetContent(String entryName, OctetContent content) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		try (InputStream in = content.openStream(); OutputStream out = archiver.append(entryName)) {
			int length = 0;
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
		}
	}
	
	private void writeXmlEntry(String entryName, Document doc) throws IOException, TransformerException {
		try (OutputStream stream = archiver.append(entryName)) {
			writeXmlDeclaration(stream);	
			transform(doc, stream);
		}
	}
	
	private void writeXmlDeclaration(OutputStream stream) throws IOException {
		writeText(stream, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
	}
	
	private void writeText(OutputStream stream, String text) throws IOException {
		stream.write(text.getBytes());
	}
	
	private void transform(Document doc, OutputStream stream) throws TransformerException {
		DOMSource source = new DOMSource(doc);
		StreamResult target = new StreamResult(stream);
		this.transformer.transform(source, target);
	}
	
	private void addProcessor(ContentProcessor processor) {
		this.processors.add(processor);
	}
	
	private static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		return factory.newDocumentBuilder();
	}
	
	private static Transformer createTransformer() throws TransformerConfigurationException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer t = factory.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		return t;
	}
}

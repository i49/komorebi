package com.github.i49.reflow.epub;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import com.github.i49.reflow.content.Content;
import com.github.i49.reflow.content.OctetContent;
import com.github.i49.reflow.publication.Publication;
import com.github.i49.reflow.publication.PublicationResource;
import com.github.i49.reflow.publication.PublicationWriter;
import com.github.i49.reflow.publication.Toc;

public class Epub3Writer implements PublicationWriter {

	private static final String MIMETYPE = "application/epub+zip";
	private static final String DEFAULT_PACKAGE_DIR = "EPUB/";
	private static final String PACKAGE_DOCUMENT_NAME = "package.opf";
	private static final int BUFFER_SIZE = 128 * 1024;

	private final Transformer transformer;
	private final Archiver archiver;
	
	private final DocumentBuilder documentBuilder;
	
	private String packageDir = DEFAULT_PACKAGE_DIR;
	
	public Epub3Writer(OutputStream stream) throws Exception {
		this.documentBuilder = createDocumentBuilder();
		this.transformer = createTransformer();
		this.archiver = new ZipArchiver(stream);
	}

	@Override
	public void write(Publication pub) throws Exception {
		writeMimeType();
		writeContainerXml();
		writePackageDocument(pub);
		writeAllResources(pub);
		buildToc(pub);
	}

	@Override
	public void close() throws IOException {
		this.archiver.close();
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

	private void buildToc(Publication pub) {
		TocBuilder builder = new TocBuilder(pub);
		Toc toc = builder.buildToc();
	}
	
	private void writeAllResources(Publication publication) throws IOException {
		for (PublicationResource resource: publication.getResources()) {
			String entryName = this.packageDir + resource.getIdentifier().toString();
			Content content = resource.getContent();
			if (content instanceof OctetContent) {
				writeOctetContent(entryName, (OctetContent)content);
			}
		}
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

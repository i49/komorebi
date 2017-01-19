package com.github.i49.komorebi.publication;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Metadata of a book.
 */
public class Metadata {

	private String title;
	private String identifier;
	private Locale language;
	
	private String publisher;
	private final List<String> creators = new ArrayList<>();
	
	private LocalDateTime date;
	private LocalDateTime lastModified;

	public Metadata() {
		this.identifier = generateIdentifier();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public Locale getLanguage() {
		return language;
	}
	
	public void setLanguage(Locale language) {
		this.language = language;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public LocalDateTime getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}
	
	public List<String> getCreators() {
		return creators;
	}
	
	public static Metadata load(InputStream s) throws IOException {
		return MetadataLoader.load(s);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (getTitle() != null)
			b.append("Title: ").append(getTitle()).append("\n");
		if (getIdentifier() != null)
			b.append("Identifier: ").append(getIdentifier()).append("\n");
		if (getLanguage() != null)
			b.append("Language: ").append(getLanguage()).append("\n");
		for (String creator : getCreators())
			b.append("Creator: ").append(creator).append("\n");
		if (getPublisher() != null)
			b.append("Publisher: ").append(getPublisher());										
		return b.toString();
	}
	
	private static String generateIdentifier() {
		UUID uuid = UUID.randomUUID();
		return "urn:uuid:" + uuid.toString();
	}
}

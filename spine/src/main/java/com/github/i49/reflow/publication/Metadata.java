package com.github.i49.reflow.publication;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Metadata of a publication.
 */
public class Metadata {

	private String identifier;
	private final List<String> titles = new ArrayList<>();
	private final List<Locale> languages = new ArrayList<>();
	
	private final List<String> creators = new ArrayList<>();
	private final List<String> publishers = new ArrayList<>();
	
	private OffsetDateTime date;
	private OffsetDateTime lastModified;

	public Metadata() {
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<String> getTitles() {
		return titles;
	}
	
	public List<Locale> getLanguages() {
		return languages;
	}
	
	public List<String> getCreators() {
		return creators;
	}
	
	public List<String> getPublishers() {
		return publishers;
	}
	
	public OffsetDateTime getDate() {
		return date;
	}
	
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}
	
	public OffsetDateTime getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(OffsetDateTime lastModified) {
		this.lastModified = lastModified;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (getIdentifier() != null) {
			b.append("Identifier: ").append(getIdentifier()).append("\n");
		}
		for (String title: getTitles()) {
			b.append("Title: ").append(title).append("\n");
		}
		for (Locale language: getLanguages()) {
			b.append("Language: ").append(language.toLanguageTag()).append("\n");
		}
		for (String creator : getCreators()) {
			b.append("Creator: ").append(creator).append("\n");
		}
		for (String publisher: getPublishers()) {
			b.append("Publisher: ").append(publisher).append("\n");
		}
		if (getDate() != null) {
			b.append("Date: ").append(getDate()).append("\n");
		}
		if (getLastModified() != null) {
			b.append("Last Modified: ").append(getLastModified()).append("\n");
		}
		return b.toString();
	}
}

package com.github.i49.spine.core;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.github.i49.spine.api.Metadata;

class MetadataImpl implements Metadata {

	private String identifier;
	private final List<String> titles = new ArrayList<>();
	private final List<Locale> languages = new ArrayList<>();
	
	private final List<String> creators = new ArrayList<>();
	private final List<String> publishers = new ArrayList<>();
	
	private OffsetDateTime date;
	private OffsetDateTime lastModified;
	
	public MetadataImpl() {
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public List<String> getTitles() {
		return titles;
	}
	
	@Override
	public List<Locale> getLanguages() {
		return languages;
	}
	
	@Override
	public List<String> getCreators() {
		return creators;
	}
	
	@Override
	public List<String> getPublishers() {
		return publishers;
	}
	
	@Override
	public OffsetDateTime getDate() {
		return date;
	}
	
	@Override
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}
	
	@Override
	public OffsetDateTime getLastModified() {
		return lastModified;
	}
	
	@Override
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

package com.github.i49.spine.api;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

/**
 * Metadata of a publication.
 */
public interface Metadata {

	String getIdentifier();

	void setIdentifier(String identifier);

	List<String> getTitles();
	
	List<Locale> getLanguages();
	
	List<String> getCreators();
	
	List<String> getPublishers();
	
	OffsetDateTime getDate();
	
	void setDate(OffsetDateTime date);
	
	OffsetDateTime getLastModified();
	
	void setLastModified(OffsetDateTime lastModified);
}

package com.github.i49.spine.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.github.i49.spine.api.Metadata;

/**
 * A loader class to load publication metadata from YAML files.
 */
class MetadataLoader {
	
	private final InputStream stream;
	
	private MetadataLoader(InputStream stream) {
		this.stream = stream;
	}
	
	/**
	 * Creates an instance of this class.
	 * @param path the path of the file describing metadata.
	 * @return created instance. 
	 * @throws IOException if the file specified is not found.
	 */
	public static MetadataLoader from(Path path) throws IOException {
		return from(Files.newInputStream(path));
	}
	
	public static MetadataLoader from(InputStream stream) {
		return new MetadataLoader(stream);
	}
	
	public void loadTo(Metadata metadata) throws IOException {
		try (InputStream s = this.stream) {
			load(metadata);
		}
	}

	public void load(Metadata m) {
		Yaml yaml = new Yaml();
		Map<?, ?> map = yaml.loadAs(this.stream, Map.class);
		if (map == null) {
			return;
		}
		if (map.containsKey("identifier")) {
			m.setIdentifier((String)map.get("identifier"));
		}
		if (map.containsKey("title")) {
			m.getTitles().addAll(getStringOrList(map, "title"));
		}
		if (map.containsKey("language")) {
			for (String language: getStringOrList(map, "language")) {
				m.getLanguages().add(Locale.forLanguageTag(language));
			}	
		}
		if (map.containsKey("creator")) {
			m.getCreators().addAll(getStringOrList(map, "creator"));
		}
		if (map.containsKey("publisher")) {
			m.getPublishers().addAll(getStringOrList(map, "publisher"));
		}
		if (map.containsKey("date")) {
			m.setDate(getDateTime(map, "date"));
		}
		if (map.containsKey("modified")) {
			m.setLastModified(getDateTime(map, "modified"));
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getStringOrList(Map<?, ?> map, String key) {
		List<String> list = new ArrayList<>();
		Object value = map.get(key);
		if (value != null) {
			if (value instanceof String) {
				list.add((String)value);
			} else if (value instanceof List) {
				list.addAll((List<String>)value);
			}
		}
		return list;
	}
	
	private OffsetDateTime getDateTime(Map<?, ?> map, String key) {
		Date date = (Date)map.get(key);
		return OffsetDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
	}
}

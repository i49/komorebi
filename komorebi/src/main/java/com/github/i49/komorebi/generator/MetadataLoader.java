package com.github.i49.komorebi.generator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.github.i49.komorebi.publication.Metadata;

class MetadataLoader {
	
	private final Metadata m = new Metadata();
	
	MetadataLoader() {
	}
	
	public Metadata load(Path path) throws IOException {
		try (InputStream s = Files.newInputStream(path)) {
			return load(s);
		}
	}

	public Metadata load(InputStream s) {
		Yaml yaml = new Yaml();
		Map<?, ?> map = yaml.loadAs(s, Map.class);
		if (map != null) {
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
		}
		return m;
	}
	
	private List<String> getStringOrList(Map<?, ?> map, String key) {
		List<String> values = new ArrayList<>();
		Object value = map.get(key);
		if (value != null) {
			if (value instanceof String) {
				values.add((String)value);
			} else if (value instanceof List) {
				values.addAll((List<String>)values);
			}
		}
		return values;
	}
}

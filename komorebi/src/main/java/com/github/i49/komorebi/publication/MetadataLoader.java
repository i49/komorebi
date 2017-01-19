package com.github.i49.komorebi.publication;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

final class MetadataLoader {

	public static Metadata load(InputStream s) {
		Metadata m = new Metadata();
		Yaml yaml = new Yaml();
		Map<?, ?> map = yaml.loadAs(s, Map.class);
		if (map != null) {
			if (map.containsKey("title")) {
				m.setTitle((String)map.get("title"));
			}
			if (map.containsKey("identifier")) {
				m.setIdentifier((String)map.get("identifier"));
			}
			if (map.containsKey("language")) {
				m.setLanguage(Locale.forLanguageTag((String)map.get("language")));
			}
			if (map.containsKey("publisher")) {
				m.setPublisher((String)map.get("publisher"));
			}
			if (map.containsKey("creator")) {
				List<?> creators = (List<?>)map.get("creator");
				for (Object creator: creators) {
					m.getCreators().add((String)creator);
				}
			}
		}
		return m;
	}
	
	private MetadataLoader() {
	}
}

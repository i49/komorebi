package com.github.i49.reflow.generator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

import com.github.i49.reflow.generator.MetadataLoader;
import com.github.i49.reflow.publication.Metadata;

public class MetadataLoaderTest {

	@Test
	public void loadNormal() throws IOException {
		MetadataLoader loader = new MetadataLoader();
		try (InputStream s = getClass().getResourceAsStream("metadata.yaml")) {
			Metadata m = loader.load(s);
			assertThat(m.getIdentifier(), equalTo("idpf.epub31.samples.moby-dick.xhtml"));
			assertThat(m.getTitles().size(), equalTo(1));
			assertThat(m.getTitles().get(0), equalTo("Moby-Dick"));
			assertThat(m.getLanguages().size(), equalTo(1));
			assertThat(m.getLanguages().get(0).toLanguageTag(), equalTo("en-US"));
			assertThat(m.getCreators().size(), equalTo(1));
			assertThat(m.getCreators().get(0), equalTo("Herman Melville"));
			assertThat(m.getPublishers().size(), equalTo(1));
			assertThat(m.getPublishers().get(0), equalTo("Harper & Brothers, Publishers"));
			assertThat(m.getDate(), equalTo(OffsetDateTime.of(1851, 11, 14, 0, 0, 0, 0, ZoneOffset.UTC)));
			assertThat(m.getLastModified(), equalTo(OffsetDateTime.of(2016, 2, 5, 14, 40, 0, 0, ZoneOffset.UTC)));
		}
	}
}

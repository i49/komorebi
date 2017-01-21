package com.github.i49.komorebi.publication;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class MetadataLoaderTest {

	@Test
	public void loadNormal() throws IOException {
		try (InputStream s = getClass().getResourceAsStream("metadata.yaml")) {
			Metadata m = MetadataLoader.load(s);
			assertThat(m.getIdentifier(), equalTo("idpf.epub31.samples.moby-dick.xhtml"));
			assertThat(m.getTitle(), equalTo("Moby-Dick"));
			assertThat(m.getLanguage().toLanguageTag(), equalTo("en-US"));
			assertThat(m.getPublisher(), equalTo("Harper & Brothers, Publishers"));
			assertThat(m.getCreators().size(), equalTo(1));
			assertThat(m.getCreators().get(0), equalTo("Herman Melville"));
		}
	}
}

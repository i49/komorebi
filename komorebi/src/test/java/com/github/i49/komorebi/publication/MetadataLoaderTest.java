package com.github.i49.komorebi.publication;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class MetadataLoaderTest {

	@Test
	public void loadNormal() throws IOException {
		try (InputStream s = getClass().getResourceAsStream("metatada.yml")) {
			Metadata m = MetadataLoader.load(s);
		}
	}
}

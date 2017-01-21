package com.github.i49.komorebi.generator;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.i49.komorebi.generator.PublicationGenerator;

public class PublicationGeneratorTest {

	private static final Path TARGET_PATH = Paths.get("target");
	private static final Path SAMPLE_BASE_PATH = TARGET_PATH.resolve(Paths.get("test-classes", "samples"));
	
	@Test
	public void sample01() throws Exception {
		PublicationGenerator generator = new PublicationGenerator();
		generator.generate(getSourcePath("sample01"), getTargetPath("sample01.zip"));
	}

	private static Path getSourcePath(String name) {
		return SAMPLE_BASE_PATH.resolve(name);
	}
	
	private static Path getTargetPath(String name) {
		return TARGET_PATH.resolve(name);
	}
}

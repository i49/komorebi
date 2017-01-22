package com.github.i49.reflow;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.i49.reflow.Application;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ApplicationTest {

	@Test
	public void sample01() throws Exception {
		run("sample01");
	}

	private void run(String sourceDir) throws Exception {
		Path sourcePath = Paths.get("target", "test-classes", "samples", sourceDir);
		Path outputPath = Paths.get("target", sourceDir + ".zip");
		Application app = new Application();
		int result = app.run(sourcePath.toString(), "-output", outputPath.toString());
	
		assertThat(result, equalTo(0));
	}
}

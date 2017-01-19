package com.github.i49.komorebi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Application {
	
	private final PrintStream out = System.out;

	private int run(String[] args) throws Exception {
		printHelp();
		return 0;
	}

	private int printHelp() throws IOException {
		try (InputStream s = getClass().getResourceAsStream("help.txt")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(s, StandardCharsets.UTF_8));
			reader.lines().forEach(line->out.println(line));
		}
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			System.exit(new Application().run(args));
		} catch (Exception e) {
			System.err.print(e);
			System.exit(1);
		}
	}
}

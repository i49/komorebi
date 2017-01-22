package com.github.i49.komorebi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import com.github.i49.komorebi.generator.PublicationGenerator;

public class Application {
	
	private final PrintStream out = System.out;

	public int run(String... args) throws Exception {
		LinkedList<String> argList = new LinkedList<String>(Arrays.asList(args));
		return run(argList);
	}

	private int run(LinkedList<String> args) throws Exception {
		Path target = null;
		Path sourceDir = null;
		PublicationGenerator generator = new PublicationGenerator();
		while (!args.isEmpty()) {
			String arg = args.remove();
			if (arg.startsWith("-")) {
				if (arg.equals("-help")) {
					return printHelp();
				} else if (arg.equals("-output")) {
					target = Paths.get(args.remove());
				} else {
					System.err.println("Unknown option: " + arg);
					return printHelp();
				}
			} else if (sourceDir == null){
				sourceDir = Paths.get(arg);
			}
		}
		if (sourceDir == null) {
			System.err.println("Source directory is not specified.");
			return printHelp();
		} else {
			generator.generate(sourceDir, target);
		}
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

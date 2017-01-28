package com.github.i49.spine.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Application {
	
	private final PrintStream out = System.out;
	private final PrintStream err = System.err;

	public int run(String... args) {
		try {
			List<String> argList = Arrays.asList(args);
			if (argList.contains("-help")) {
				help();
				return 0;
			}
			Command command = new PublishCommand();
			return command.execute(argList.iterator());
		} catch (Exception e) {
			err.println("[ERROR] " + e.getMessage());
			help();
			return 1;
		}
	}

	private void help() {
		try (InputStream s = getClass().getResourceAsStream("help.txt")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(s, StandardCharsets.UTF_8));
			reader.lines().forEach(line->out.println(line));
		} catch (IOException e) {
			err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.exit(new Application().run(args));
	}
}

package com.github.i49.spine.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import com.github.i49.spine.util.PublicationGenerator;

public class PublishCommand extends Command {

	private Path source;
	private Path target;

	PublishCommand() {
	}
	
	@Override
	protected void parseArguments(Iterator<String> args) {
		while (args.hasNext()) {
			String arg = args.next();
			if (arg.startsWith("-")) {
				if (arg.equals("-output")) {
					this.target = Paths.get(args.next());
				} else {
					throw new CommandException("Unknown option: " + arg);
				}
			} else if (source == null) {
				this.source = Paths.get(arg);
			}
		}
		if (this.source == null) {
			throw new CommandException("Source directory is not specified.");
		}
	}

	@Override
	protected int run() {
		try {
			PublicationGenerator generator = new PublicationGenerator();
			generator.generate(source, target);
			return 0;
		} catch (Exception e) {
			throw new CommandException(e);
		}
	}
}

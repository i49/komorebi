package com.github.i49.reflow.content;

import java.io.InputStream;

public interface OctetContent extends Content {
	
	InputStream openStream();
}

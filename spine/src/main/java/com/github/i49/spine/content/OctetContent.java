package com.github.i49.spine.content;

import java.io.InputStream;

public interface OctetContent extends Content {
	
	InputStream openStream();
}

package com.github.i49.komorebi.content;

import java.io.InputStream;

public interface OctetContent extends Content {
	
	InputStream openStream();
}

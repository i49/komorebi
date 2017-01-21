package com.github.i49.komorebi.publication;

import java.io.InputStream;

public interface OctetContent extends Content {
	
	InputStream openStream();
}

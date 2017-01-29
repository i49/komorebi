package com.github.i49.spine.api;

import java.io.IOException;
import java.io.InputStream;

public interface AuxiliaryResource extends PublicationResource {

	InputStream openOctetStream() throws IOException;
}

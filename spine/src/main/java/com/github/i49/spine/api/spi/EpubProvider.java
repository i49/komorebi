package com.github.i49.spine.api.spi;

import com.github.i49.spine.api.Publication;
import com.github.i49.spine.api.PublicationResourceFactory;
import com.github.i49.spine.api.PublicationWriterFactory;
import com.github.i49.spine.core.EpubProviderImpl;

public interface EpubProvider {

	Publication createPublication();
	
	PublicationResourceFactory createResourceFactory();
	
	PublicationWriterFactory createWriterFactory();
	
	public static EpubProvider provider() {
		return new EpubProviderImpl();
	}
}

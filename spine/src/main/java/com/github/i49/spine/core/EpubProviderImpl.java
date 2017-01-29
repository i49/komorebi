package com.github.i49.spine.core;

import com.github.i49.spine.api.Publication;
import com.github.i49.spine.api.PublicationResourceFactory;
import com.github.i49.spine.api.PublicationWriterFactory;
import com.github.i49.spine.api.spi.EpubProvider;

/**
 * An implementation of {@link EpubProvider} interface.
 */
public class EpubProviderImpl implements EpubProvider {
	
	public EpubProviderImpl() {
	}

	@Override
	public Publication createPublication() {
		return new PublicationImpl();
	}

	@Override
	public PublicationResourceFactory createResourceFactory() {
		return new PublicationResourceFactoryImpl();
	}

	@Override
	public PublicationWriterFactory createWriterFactory() {
		return new PublicationWriterFactoryImpl();
	}
}

package com.github.i49.komorebi.content;

import org.xml.sax.SAXException;

public class UncheckedSaxException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UncheckedSaxException(SAXException cause) {
		super(cause);
	}
	
	public SAXException getCause() {
		return (SAXException)super.getCause();
	}
}

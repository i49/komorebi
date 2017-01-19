package com.github.i49.komorebi.publication;

/**
 * The media types which specify types and formats of the Publication Resources contained in a publication.
 */
public enum MediaType {

	APPLICATION_XHTML_XML("application", "xhtml+xml"),
	IMAGE_JPEG("image", "jpeg"),
	IMAGE_PNG("image", "png"),
	TEXT_HTML("text", "html"),
	TEXT_STYLESHEET("text", "css")
	;
	
	private final String type;
	private final String subtype;
	
	private MediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
	}
	
	@Override
	public String toString() {
		return type + "/" + subtype;
	}
}

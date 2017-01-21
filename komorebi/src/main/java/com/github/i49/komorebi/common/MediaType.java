package com.github.i49.komorebi.common;

/**
 * The media types which specify types and formats of the Publication Resources contained in a publication.
 */
public enum MediaType {

	APPLICATION_XHTML_XML("application", "xhtml+xml"),
	IMAGE_GIF("image", "gif"),
	IMAGE_JPEG("image", "jpeg"),
	IMAGE_PNG("image", "png"),
	IMAGE_SVG_XML("image", "svg+xml"),
	TEXT_CSS("text", "css"),
	TEXT_HTML("text", "html"),
	TEXT_JAVASCRIPT("text", "javascript")
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

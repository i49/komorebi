package com.github.i49.spine.api;

import java.net.URI;

/**
 * The media types which specify types and formats of the Publication Resources
 * contained in a publication.
 */
public enum CoreMediaType {

	IMAGE_GIF("image", "gif"),
	IMAGE_JPEG("image", "jpeg"),
	IMAGE_PNG("image", "png"),
	IMAGE_SVG_XML("image", "svg+xml"),

	APPLICATION_XHTML_XML("application", "xhtml+xml"),
	APPLICATION_NCX_XML("application", "x-dtbncx+xml"),
	APPLICATION_OPENTYPE("application", "vnd.ms-opentype"),
	APPLICATION_WOFF_FONT("application", "font-woff"),
	APPLICATION_SMIL_XML("application", "smil+xml"),
	APPLICAIION_PLS_XML("application", "pls+xml"),
	
	AUDIO_MPEG("audio", "mpeg"),
	AUDIO_MP4("audio", "mp4"),
	
	TEXT_CSS("text", "css"),
	TEXT_JAVASCRIPT("text", "javascript")
	;
	
	private final String type;
	private final String subtype;
	
	private CoreMediaType(String type, String subtype) {
		this.type = type;
		this.subtype = subtype;
	}
	
	@Override
	public String toString() {
		return type + "/" + subtype;
	}
	
	/**
	 * Guesses media type from a file extension.
	 * @param location the location of the resource.
	 * @return the media type found or {@code null} if not found.
	 */
	public static CoreMediaType guessMediaType(URI location) {
		if (location == null) {
			return null;
		}
		String path = location.getPath();
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}
		String extension = path.substring(lastIndex + 1);
		CoreMediaType mediaType = null;
		switch (extension.toLowerCase()) {
		case "xhtml":
			mediaType = CoreMediaType.APPLICATION_XHTML_XML;
			break;
		case "ncx":
			mediaType = CoreMediaType.APPLICATION_NCX_XML;
			break;
		case "otf":
		case "ttf":
			mediaType = CoreMediaType.APPLICATION_OPENTYPE;
			break;
		case "gif":
			mediaType = CoreMediaType.IMAGE_GIF;
			break;
		case "jpg":
		case "jpeg":
			mediaType = CoreMediaType.IMAGE_JPEG;
			break;
		case "png":
			mediaType = CoreMediaType.IMAGE_PNG;
			break;
		case "svg":
			mediaType = CoreMediaType.IMAGE_SVG_XML;
			break;
		case "css":
			mediaType = CoreMediaType.TEXT_CSS;
			break;
		case "js":
			mediaType = CoreMediaType.TEXT_JAVASCRIPT;
			break;
		}
		return mediaType;
	}
}

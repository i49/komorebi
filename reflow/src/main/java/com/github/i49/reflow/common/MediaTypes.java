package com.github.i49.reflow.common;

import java.net.URI;

public final class MediaTypes {
	
	public static MediaType guess(URI uri) {
		String path = uri.getPath();
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex < 0) {
			return null;
		}
		String extension = path.substring(lastIndex + 1);
		MediaType mediaType = null;
		switch (extension.toLowerCase()) {
		case "htm":
		case "html":
			mediaType = MediaType.TEXT_HTML;
			break;
		case "xhtml":
			mediaType = MediaType.APPLICATION_XHTML_XML;
			break;
		case "css":
			mediaType = MediaType.TEXT_CSS;
			break;
		case "gif":
			mediaType = MediaType.IMAGE_GIF;
			break;
		case "jpg":
		case "jpeg":
			mediaType = MediaType.IMAGE_JPEG;
			break;
		case "png":
			mediaType = MediaType.IMAGE_PNG;
			break;
		case "svg":
			mediaType = MediaType.IMAGE_SVG_XML;
			break;
		}
		return mediaType;
	}

	private MediaTypes() {
	}
}

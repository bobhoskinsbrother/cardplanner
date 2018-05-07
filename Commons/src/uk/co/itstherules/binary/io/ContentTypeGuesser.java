package uk.co.itstherules.binary.io;

import java.net.URLConnection;

public class ContentTypeGuesser {
	public static String guessFrom(String extension) {
		String mimeByExtension = URLConnection.guessContentTypeFromName(extension);
		if (mimeByExtension != null) {
			return mimeByExtension;
		}
		return "application/octet-stream";
	}
}

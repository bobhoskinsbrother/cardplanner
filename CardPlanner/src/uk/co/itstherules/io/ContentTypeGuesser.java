package uk.co.itstherules.io;

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

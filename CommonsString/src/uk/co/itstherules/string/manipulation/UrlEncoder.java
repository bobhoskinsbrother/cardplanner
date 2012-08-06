package uk.co.itstherules.string.manipulation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public final class UrlEncoder implements StringManipulator {
	@Override
	public String manipulate(String text) {
		try {
	        return URLEncoder.encode(text, "utf8");
        } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
        }
	}
}

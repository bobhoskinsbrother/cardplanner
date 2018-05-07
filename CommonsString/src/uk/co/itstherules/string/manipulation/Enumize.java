package uk.co.itstherules.string.manipulation;

import java.util.StringTokenizer;

public final class Enumize implements StringManipulator {

	public String manipulate(String text) {
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(text, " \\(\\)\\'_-\\/:*?\"<>|,\\.", false);
		while (tokenizer.hasMoreElements()) {
			String word = (String) tokenizer.nextElement();
			buffer.append(word.toUpperCase());
			if(tokenizer.hasMoreElements()) { buffer.append("_"); }
		}
		return buffer.toString();
	}
}

package uk.co.itstherules.string.manipulation;

import java.util.StringTokenizer;

public final class CamelCase implements StringManipulator {

	public String manipulate(String text) {
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(text, " _-\\/:*?\"<>|,\\.", false);
		while (tokenizer.hasMoreElements()) {
			String word = tokenizer.nextToken();
			String firstLetter = word.substring(0, 1);
			buffer.append(firstLetter.toUpperCase());
			buffer.append(word.substring(1, word.length()));
		}
		return buffer.toString();
	}
}

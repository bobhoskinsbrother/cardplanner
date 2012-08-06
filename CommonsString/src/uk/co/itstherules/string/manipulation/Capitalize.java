package uk.co.itstherules.string.manipulation;

import java.util.StringTokenizer;

public final class Capitalize implements StringManipulator {

	public String manipulate(String text) {
		StringBuffer buffer = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(text, " _-\\/:*?\"<>|,\\.", true);
		while (tokenizer.hasMoreElements()) {
			String word = (String) tokenizer.nextElement();
			if(word.length() > 1) {
				String firstLetter = word.substring(0, 1);
				buffer.append(firstLetter.toUpperCase());
				buffer.append(word.substring(1, word.length()).toLowerCase());
			} else {
				buffer.append(word);
			}
		}
		return buffer.toString();
	}
}

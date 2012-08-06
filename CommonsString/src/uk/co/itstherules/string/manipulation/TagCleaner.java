package uk.co.itstherules.string.manipulation;

public final class TagCleaner implements StringManipulator {

	public String manipulate(String fullString) {
		return fullString.replaceAll("\\<.*?\\>", "");
	}
}
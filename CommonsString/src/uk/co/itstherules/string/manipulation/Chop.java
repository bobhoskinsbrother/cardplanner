package uk.co.itstherules.string.manipulation;

public final class Chop implements StringManipulator {
	
	private final String modifier;

	public Chop(String modifier) {
		this.modifier = modifier;
	}

	public String manipulate(String fullString) {
		if(fullString.startsWith(modifier)) {
			return fullString.substring(modifier.length(), fullString.length());
		}
		return fullString;

	}

}

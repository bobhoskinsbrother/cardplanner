package uk.co.itstherules.string.manipulation;

public final class Chomp implements StringManipulator {

	private final String modifier;

	public Chomp(String modifier) {
		this.modifier = modifier;
	}
	
	public String manipulate(String fullString) {
		if(fullString.endsWith(modifier)) {
			return fullString.substring(0, fullString.length() - modifier.length());
		}
		return fullString;
	}

}

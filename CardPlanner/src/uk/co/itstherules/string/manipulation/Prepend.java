package uk.co.itstherules.string.manipulation;

public final class Prepend implements StringManipulator {

	private final String modifier;

	public Prepend(String modifier) {
		this.modifier = modifier;
	}

	public String manipulate(String fullString) {
		StringBuffer buffer = new StringBuffer(modifier);
		buffer.append(fullString);
		return buffer.toString();
	}
}

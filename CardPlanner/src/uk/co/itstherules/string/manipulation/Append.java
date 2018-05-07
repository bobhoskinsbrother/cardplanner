package uk.co.itstherules.string.manipulation;

public final class Append implements StringManipulator {

	private final String modifier;

	public Append(String modifier) {
		this.modifier = modifier;
	}

	public String manipulate(String fullString) {
		StringBuffer buffer = new StringBuffer(fullString);
		buffer.append(modifier);
		return buffer.toString();
	}

}

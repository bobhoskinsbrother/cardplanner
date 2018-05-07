package uk.co.itstherules.string.manipulation;

public final class CompositeStringManipulator implements StringManipulator {

	private final StringManipulator[] manipulators;

	public CompositeStringManipulator(StringManipulator... manipulators) {
		this.manipulators = manipulators;
	}
	
	public String manipulate(String text) {
		for (StringManipulator manipulator : manipulators) {
			text = manipulator.manipulate(text);
		}
		return text;
	}

}

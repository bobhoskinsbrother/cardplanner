package uk.co.itstherules.string.manipulation;


public final class ChopAndPrepend implements StringManipulator {

	private CompositeStringManipulator stringManipulator;

	public ChopAndPrepend(String toChop, String toPrepend) {
		this.stringManipulator = new CompositeStringManipulator(new Chop(toChop), new Prepend(toPrepend));
	}

	public String manipulate(String fullText) {
		return stringManipulator.manipulate(fullText);
	}
	
}

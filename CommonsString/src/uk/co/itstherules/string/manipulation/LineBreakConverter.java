package uk.co.itstherules.string.manipulation;

public final class LineBreakConverter implements StringManipulator {
	
	private final String replacement;
	private String toReplace;

	public LineBreakConverter(String replacement) {
		this.replacement = replacement;
		this.toReplace = System.getProperty("line.separator");
    }

	public String manipulate(String text) { return text.replaceAll(toReplace, replacement); }
}

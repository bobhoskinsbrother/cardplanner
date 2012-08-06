package uk.co.itstherules.string.manipulation;


public final class ExtensionFinder implements StringManipulator {

	private final String last;

	public ExtensionFinder(String last) {
		this.last = last;
    }
	
	public String manipulate(String text) {
		int lastIndexOf = text.lastIndexOf(last);
		if(lastIndexOf>-1) {
			return text.substring(lastIndexOf+1);
		} 
		return text;
    }

}

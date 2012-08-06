package uk.co.itstherules.string.manipulation;

public final class Constantize implements StringManipulator {

	@Override public String manipulate(String text) {
		if(text != null) {
			text = text.toUpperCase();
			text = text.replaceAll("[^A-Z0-9]", "_");
		}
	    return text;
    }
}
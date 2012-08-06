package uk.co.itstherules.string.manipulation;


public final class AppendageRemover implements StringManipulator {

	private String toRemove;

	public AppendageRemover(String toRemove) {
		this.toRemove = toRemove;
	}
	
	public String manipulate(String text) {
		if(text.endsWith(toRemove)){
			return text.substring(0, text.length() - toRemove.length());
		}
		return text;
	}

	
	
}

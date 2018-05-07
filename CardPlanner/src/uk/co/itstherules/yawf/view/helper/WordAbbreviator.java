package uk.co.itstherules.yawf.view.helper;

import uk.co.itstherules.string.manipulation.TagCleaner;

public final class WordAbbreviator {
	
	public String abbreviate(String string, int wordAmount) {
		string = new TagCleaner().manipulate(string);
		String[] stringArray = string.split(" ");
		wordAmount = Math.min(stringArray.length, wordAmount);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < wordAmount; i++) {
			if(i!=0) { buffer.append(" "); }
	        buffer.append(stringArray[i]);
        }
		if(stringArray.length>wordAmount) {
			buffer.append("...");
		}
		return buffer.toString();
	}
	
}

package uk.co.itstherules.yawf.view.helper;

public final class LetterAbbreviator {
	
	public String abbreviate(String string, int letterAmount) {
		string = string.replaceAll("\\<.*?\\>", "");
		int length = string.length();
		if(length > letterAmount) {
			letterAmount = Math.min(length, letterAmount);
			string = string.substring(0, letterAmount);
			int lastIndexOf = string.lastIndexOf(" ");
			if(lastIndexOf>-1) {
				string = string.substring(0, lastIndexOf);
			}
			StringBuffer buffer = new StringBuffer();
	        buffer.append(string);
			buffer.append("...");
			return buffer.toString();
		}
		return string;
	}
	
}

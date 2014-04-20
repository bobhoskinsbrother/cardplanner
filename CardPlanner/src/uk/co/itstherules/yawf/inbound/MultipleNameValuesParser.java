package uk.co.itstherules.yawf.inbound;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public final class MultipleNameValuesParser {
	
	private MultipleNameValuesParser() {}

	public static Map<String, String> parse(String input) {
		StringTokenizer nameValueTokenizer = new StringTokenizer(input, "[,]", false);
		Map<String, String> reply = new TreeMap<String, String>();
		while (nameValueTokenizer.hasMoreElements()) {
			String nameValue = (String) nameValueTokenizer.nextElement();
			StringTokenizer equalsTokenizer = new StringTokenizer(nameValue, "=", false);
			String key = (String) equalsTokenizer.nextElement();
			if(!equalsTokenizer.hasMoreTokens()) { 
				throw new IllegalArgumentException("There is no equals sign \"=\" for: "+key); 
			}
			String value = (String) equalsTokenizer.nextElement();
			if (key != null) {
				reply.put(key, value);
			}
		}
		return reply;
	}
}

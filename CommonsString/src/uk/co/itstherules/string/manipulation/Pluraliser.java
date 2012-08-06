package uk.co.itstherules.string.manipulation;

import java.util.HashMap;
import java.util.Map;

public class Pluraliser implements StringManipulator{
	
	private static ThreadLocal<Map<String, String>> pluralMap = new ThreadLocal<Map<String,String>>();

	public Pluraliser() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("person", "people");
		map.put("company", "companies");
		pluralMap.set(map);
    }
	
	public String manipulate(String input) {
		if(input.endsWith("s")) return input;
		StringBuffer buffer = new StringBuffer(input);
		buffer.append("s");
		return buffer.toString();
    }
	
}

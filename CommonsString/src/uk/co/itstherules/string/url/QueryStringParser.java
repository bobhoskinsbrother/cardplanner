package uk.co.itstherules.string.url;

import java.util.HashMap;
import java.util.Map;

public class QueryStringParser {
	
	public Map<String, String> parse(String queryString) throws IllegalArgumentException {
		return parse(queryString, "&");
	}

	public Map<String, String> parse(String queryString, String delimiter) throws IllegalArgumentException {
		if(queryString==null) {
			throw new IllegalArgumentException();
		}
		if(queryString.indexOf("?") > -1) {
			String[] split = queryString.split("\\?");
			queryString = split[1];
		}
		Map<String, String> map = new HashMap<String, String>();
		String[] keyValuePairs = queryString.split(delimiter);
		for (String keyValuePair : keyValuePairs) {
			String[] splitKeyValuePair = keyValuePair.split("=");
			String key = "";
			String value = "";
			try {
				key = splitKeyValuePair[0];
			} catch (Exception e) {
				key = "";
			}
			try {
				value = splitKeyValuePair[1];
			} catch (Exception e) {
			}
			if(!"".equals(key)) {
				if(!map.containsKey(key)) {
					map.put(key, value);
				} else {
					StringBuffer buffer = new StringBuffer();
					buffer.append(map.get(key));
					buffer.append(",");
					buffer.append(value);
					map.put(key, buffer.toString());
				}
			}
		}
		return map;
	}
	
}

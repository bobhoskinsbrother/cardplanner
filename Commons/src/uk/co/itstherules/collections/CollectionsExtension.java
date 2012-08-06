package uk.co.itstherules.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionsExtension {
	
	public static List<String> toList(String value) {
		String[] splitValues = value.split(",");
		return Arrays.asList(splitValues);
	}
	
	public static String[] toArray(Collection<String> values) {
		String aString[] = new String[values.size()];
		int i = 0;
		for (String string : values) {
	        aString[i] = string;
	        i++;
        }
		return aString;

    }

	
}

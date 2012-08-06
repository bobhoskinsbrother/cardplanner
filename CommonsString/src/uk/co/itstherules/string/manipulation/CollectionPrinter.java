package uk.co.itstherules.string.manipulation;

import java.util.Collection;

public final class CollectionPrinter {

    private CollectionPrinter() { }

	public static String print(Collection<?> set) {
		StringBuffer buffer = new StringBuffer();
		for (Object object : set) {
			buffer.append(object.toString());
			buffer.append(",");
		}
		if(set.size() > 0) {
			return buffer.substring(0, buffer.length()-1);
		}
		return buffer.toString();
	}
	
	public static String print(Object... set) {
		StringBuffer buffer = new StringBuffer();
		for (Object object : set) {
			buffer.append(object.toString());
			buffer.append(",");
		}
		if(set.length > 0) {
			return buffer.substring(0, buffer.length()-1);
		}
		return buffer.toString();
	}

}

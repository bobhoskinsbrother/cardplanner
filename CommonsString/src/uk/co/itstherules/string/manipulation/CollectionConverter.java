package uk.co.itstherules.string.manipulation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class CollectionConverter {

	public String convert(Collection<?> set) {
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
	
	public String convert(Object... set) {
		List<Object> list = Arrays.asList(set);
		return convert(list);
	}

}

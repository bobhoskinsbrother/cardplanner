package uk.co.itstherules.string.manipulation;

import java.util.Arrays;
import java.util.Collection;

public final class CollectionConverter {

	public String convert(Collection<?> set) {
		StringBuilder b = new StringBuilder();
		for (Object object : set) {
			b.append(object.toString());
			b.append(",");
		}
		if(set.size() > 0) {
			return b.substring(0, b.length()-1);
		}
		return b.toString();
	}
	
	public String convert(Object... set) {
        return convert(Arrays.asList(set));
	}

}

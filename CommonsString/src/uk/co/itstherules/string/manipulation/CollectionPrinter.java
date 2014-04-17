package uk.co.itstherules.string.manipulation;

import java.util.Collection;

public final class CollectionPrinter {

    private CollectionPrinter() { }

    private static final CollectionConverter CONVERTER = new CollectionConverter();

	public static String print(Collection<?> collection) {
        return CONVERTER.convert(collection);
	}
	
	public static String print(Object... array) {
        return CONVERTER.convert(array);
	}

}

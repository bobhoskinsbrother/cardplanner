package uk.co.itstherules.converter;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnumMapConverter {

	@SuppressWarnings({"rawtypes"})
    public Map<String, String> convert(Enum[] values) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (Enum enumValue : values) {
	        map.put(enumValue.name(), enumValue.toString());
        }
		return map;
    }
}

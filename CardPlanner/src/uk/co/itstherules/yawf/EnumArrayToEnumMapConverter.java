package uk.co.itstherules.yawf;

import java.util.LinkedHashMap;
import java.util.Map;

public final class EnumArrayToEnumMapConverter {
	
	public Map<String, Enum<?>> convert(Enum<?>[] values) {
		Map<String, Enum<?>> entities = new LinkedHashMap<String, Enum<?>>();
		for (Enum<?> currentEnum : values) {
	        entities.put(currentEnum.name(), currentEnum);
        }
		return entities;
    }

}

package uk.co.itstherules.yawf;

import java.util.ArrayList;
import java.util.List;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.SimpleEntityModel;

public final class EnumArrayToEntityListConverter {

	public List<Entity<?>> convert(Enum<?>[] values) {
		List<Entity<?>> entities = new ArrayList<Entity<?>>();
		for (Enum<?> currentEnum : values) {
	        entities.add(new SimpleEntityModel(String.valueOf(currentEnum.name()), currentEnum.toString()));
        }
		return entities;
    }
}

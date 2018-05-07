package uk.co.itstherules.yawf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.SimpleEntityModel;

public final class StringCollectionToEntityListConverter {

	public List<Entity<?>> convert(Collection<String> values) {
		List<Entity<?>> entities = new ArrayList<Entity<?>>();
		for (String current : values) {
	        entities.add(new SimpleEntityModel(current, current));
        }
		return entities;
    }
}

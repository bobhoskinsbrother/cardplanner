package uk.co.itstherules.yawf.model;

import java.util.Comparator;



public final class EntityComparator implements Comparator<Entity<?>> {

	@Override
    public int compare(Entity<?> o1, Entity<?> o2) {
		return o1.getTitle().compareTo(o2.getTitle());
    }
}

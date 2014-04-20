package uk.co.itstherules.yawf.model.comparator;

import java.util.Comparator;

import uk.co.itstherules.yawf.model.Entity;

public class IdentityLengthComparator implements Comparator<Entity<?>> {
	
	@Override public int compare(Entity<?> left, Entity<?> right) {
		return new StringLengthComparator().compare(left.getIdentity(), right.getIdentity());
	}
	
}

package uk.co.itstherules.yawf.model.comparator;

import java.util.Comparator;

import uk.co.itstherules.yawf.model.IdentityDeleteable;

public class OrderComparator implements Comparator<IdentityDeleteable<?>> {

	public int compare(IdentityDeleteable<?> left, IdentityDeleteable<?> right) {
		Integer order = left.getSortOrder();
		Integer otherOrder = right.getSortOrder();
		return order.compareTo(otherOrder);
    }
}

package uk.co.itstherules.cardplanner.model.atom;

import java.util.Comparator;
import java.util.Date;

import uk.co.itstherules.yawf.model.IdentityDeleteable;

public final class UpdateComparator implements Comparator<IdentityDeleteable<?>> {

	public int compare(IdentityDeleteable<?> one, IdentityDeleteable<?> two) {
		Date oneDate = one.getUpdated();
		Date twoDate = two.getUpdated();
		if(oneDate.after(twoDate)) { return -1; }
		if(oneDate.before(twoDate)) { return 1; } 
	    return 0;
    }
}

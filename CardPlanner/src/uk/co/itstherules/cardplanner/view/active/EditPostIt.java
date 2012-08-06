package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.PostItModel;
import uk.co.itstherules.cardplanner.view.PostItChange;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public final class EditPostIt extends PostItChange {

	@Override protected String action() {
	    return "Update";
    }

    public PostItModel model(ObjectCache objectCache, ValuesProvider provider) {
    	return objectCache.retrieveByIdentity(PostItModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived);
    }
}

package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.PostItModel;
import uk.co.itstherules.cardplanner.view.PostItChange;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public final class AddPostIt extends PostItChange {

	@Override protected String action() {
	    return "Create";
    }

    public PostItModel model(ObjectCache objectCache, ValuesProvider provider) {
    	return new PostItModel().defaultSetup(objectCache);
    }
}

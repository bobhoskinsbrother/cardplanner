package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.view.CardChange;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public final class EditCard extends CardChange {

	@Override protected String action() {
	    return "Update";
    }

    public CardModel model(ObjectCache objectCache, ValuesProvider provider) {
    	return objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived);
    }
}

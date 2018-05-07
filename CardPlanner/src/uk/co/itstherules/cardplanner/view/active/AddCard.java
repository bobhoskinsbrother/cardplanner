package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.view.CardChange;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public final class AddCard extends CardChange {

	@Override protected String action() {
	    return "Create";
    }

    public CardModel model(ObjectCache objectCache, ValuesProvider provider) {
        CardModel card = new CardModel().defaultSetup(objectCache);
        card.setParent(objectCache.<CardModel>retrieveByIdentity(CardModel.class, provider.getString("cardIdentity"), ObjectState.Active, ObjectState.Archived));
        return card;
    }
    
}
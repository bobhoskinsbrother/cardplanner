package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;

import java.util.Set;

public final class CardService {

	public Set<CardModel> children(ObjectCache objectCache, String identity) {
        CardModel card = getCardOrDefault(objectCache, identity);
        return objectCache.all(CardModel.class, new QueryConditions("AND").put("parent.identity", card.getIdentity()));
    }
    
	public CardModel getCardOrDefault(ObjectCache objectCache, String identity) {
		if(!"0".equals(identity)) {
			CardModel reply = objectCache.retrieveByIdentity(CardModel.class, identity);
			if(reply != null) { return reply; }
		}
		return invisibleCard(objectCache);
    }


    public CardModel invisibleCard(ObjectCache objectCache) {
        return objectCache.retrieveByIdentity(CardModel.class, Identities.INVISIBLE_CARD.getIdentity());
    }

    public Set<CardModel> everythingButInvisible(ObjectCache objectCache) {
        Set<CardModel> cards = objectCache.all(CardModel.class);
        final IdentityDeleteable<?> invisible = SpecialInstances.retrieve(objectCache, Identities.INVISIBLE_CARD);
        cards.remove(invisible);
        return cards;
    }
}

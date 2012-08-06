package uk.co.itstherules.cardplanner.model.filter;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.StatusModel;

public final class NotStatus implements CardFilterable<CardModel> {

	private static ThreadLocal<StatusModel> LOCAL = new ThreadLocal<StatusModel>();
	
	public NotStatus(StatusModel status) {
		LOCAL.set(status);
    }

	public boolean accept(CardModel card) {
		return !(card.getStatus().getIdentity().equals(LOCAL.get().getIdentity()));
    }
	
	
}

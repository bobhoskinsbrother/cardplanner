package uk.co.itstherules.cardplanner.model.business;

import java.util.Collections;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class Archiver {
	
	public void archive(ObjectCache objectCache, CardModel model) {
		recursivelyApplyAction(objectCache, Collections.singleton(model), new ArchiveAction());
	}

	public void deArchive(ObjectCache objectCache, CardModel model) {
		recursivelyApplyAction(objectCache, Collections.singleton(model), new DeArchiveAction());
	}
	
	private void recursivelyApplyAction(ObjectCache objectCache, Set<CardModel> cards, ArchiverAction action) {
		for (CardModel card : cards) {
			Set<CardModel> children = objectCache.all(CardModel.class, "parent.identity", card.getIdentity(), ObjectState.Active, ObjectState.Archived);
			if(!children.isEmpty()) {
				recursivelyApplyAction(objectCache, children, action);
			}
			action.action(card);
			objectCache.save(card);
		}
	}

	
	private class ArchiveAction implements ArchiverAction { public void action(CardModel card) { card.archive(); } }
	private class DeArchiveAction implements ArchiverAction { public void action(CardModel card) { card.activate(); } }

	private interface ArchiverAction {
		void action(CardModel card);
	}

}

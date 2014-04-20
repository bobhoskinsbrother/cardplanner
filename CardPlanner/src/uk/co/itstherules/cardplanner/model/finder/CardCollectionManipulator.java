package uk.co.itstherules.cardplanner.model.finder;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardModel;


public final class CardCollectionManipulator {
	
	private final Set<CardModel> cards;

	public CardCollectionManipulator(Set<CardModel> cards) {
		this.cards = cards;
    }
	
	private Set<CardModel> descendants(Set<CardModel> childCards, CardModel parent) {
		Set<CardModel> descendantCards = children(parent);
		for (CardModel descendant : descendantCards) {
			childCards.add(descendant);
        	descendants(childCards, descendant);
	    }
		return childCards;
	}
	
	public Set<CardModel> children(CardModel parent) {
		Set<CardModel> childCards = new LinkedHashSet<CardModel>();
		for (CardModel card : this.cards) {
			if(card.getParent() != null && (parent.getIdentity().equals(card.getParent().getIdentity()))) {
	        	childCards.add(card);
	        }
	    }
		return childCards;
	}
	
	public Set<CardModel> descendants(CardModel parent) {
		Set<CardModel> childCards = new LinkedHashSet<CardModel>();
		descendants(childCards, parent);
		return childCards;
	}


	public double rolledUpEffort(CardModel candidateParent) {
		Set<CardModel> childCards = children(candidateParent);
		double effort = 0.0;
		if(childCards.size() > 0) {
			for (CardModel descendant : childCards) {
				effort += rolledUpEffort(descendant);
		    }
			return effort;
		} else {
			return (candidateParent.getEffort().getType().asRate(candidateParent.getEffort().getAmount()));
		}
    }

	public double rolledUpValue(CardModel candidateParent) {
		Set<CardModel> cards = descendants(candidateParent);
		if (!cards.isEmpty()) {
			int effort = 0;
			for (CardModel card : cards) {
				effort += (card.getValue().getType().asRate(card.getValue().getAmount()));
			}
			return effort;
		}
		return (candidateParent.getValue().getType().asRate(candidateParent.getValue().getAmount()));
    }
}

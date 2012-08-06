package uk.co.itstherules.cardplanner.model.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.TagModel;
import uk.co.itstherules.yawf.model.comparator.OrderComparator;


public final class CardFilter {
	
	
	public Set<CardModel> filter(Set<CardModel> cards, Set<String> identities) {
		List<CardModel> reply = new ArrayList<CardModel>();
		for (CardModel card : cards) {
			for (String identity : identities) {
				if(identity.equals(card.getIdentity())){
					reply.add(card);
				}
            }
        }
		Collections.sort(reply, new OrderComparator());
		return new LinkedHashSet<CardModel>(reply);
	}
	
	public Set<CardModel> filter(Set<CardModel> cards, TagModel tag) {
		List<CardModel> reply = new ArrayList<CardModel>();
		for (CardModel cardModel : cards) {
	        Set<TagModel> tags = cardModel.getTags();
	        if(tags!=null) {
	        	for (TagModel tagModel : tags) {
	        		if(tag.equals(tagModel)){
	        			reply.add(cardModel);
	        		}
	        	}
	        }
        }
		Collections.sort(reply, new OrderComparator());
		return new LinkedHashSet<CardModel>(reply);
	}
	
	public Set<CardModel> filter(Set<CardModel> cards, PersonModel person) {
		List<CardModel> personalCards = new ArrayList<CardModel>();
		for (CardModel card : cards) {
			Set<PersonModel> peopleForCards = card.getPeople();
			for (PersonModel currentPerson : peopleForCards) {
	            if(currentPerson.getIdentity().equals(person.getIdentity())){
	            	personalCards.add(card);
	            	continue;
	            }
            }
        }
		Collections.sort(personalCards, new OrderComparator());
		return new LinkedHashSet<CardModel>(personalCards);
    }

	public Set<CardModel> filter(Set<CardModel> cards, CardFilterable<CardModel> cardFilterable) {
		List<CardModel> reply = new ArrayList<CardModel>();
		for (CardModel cardModel : cards) {
			if(cardFilterable.accept(cardModel)) {
            	reply.add(cardModel);
            }
        }
		Collections.sort(reply, new OrderComparator());
		return new LinkedHashSet<CardModel>(reply);
    }
}
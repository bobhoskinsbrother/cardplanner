package uk.co.itstherules.cardplanner.model.business;

import uk.co.itstherules.cardplanner.model.CardFactModel;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.LinkedHashSet;
import java.util.Set;

public final class Deleter {

	public void delete(ObjectCache objectCache, CardModel card, CardFactModel fact) {
		card.getFacts().remove(fact);
		objectCache.delete(fact);
		objectCache.save(card);
	}

	public void delete(ObjectCache objectCache, PersonModel person) {
		Set<CardModel> cards = person.getCards();
		if(!cards.isEmpty()) {
			for (CardModel card : cards) {
				delete(objectCache, card, person);
            }
		}
		objectCache.delete(person);
    }
	
	public void delete(ObjectCache objectCache, CardModel card, SimpleAttachmentModel attachment) {
		Set<SimpleAttachmentModel> attachments = card.getAttachments();
		if(attachments==null) {
			attachments = new LinkedHashSet<SimpleAttachmentModel>();
		}
		attachments.remove(attachment);
		card.setAttachments(attachments);
        objectCache.save(card);
	}
	
	public void delete(ObjectCache objectCache, CardModel card, PersonModel person) {
		Set<PersonModel> people = card.getPeople();
		if(people==null) {
			people = new LinkedHashSet<PersonModel>();
		}
		people.remove(person);
		card.setPeople(people);
        objectCache.save(card);
	}
	
	
}

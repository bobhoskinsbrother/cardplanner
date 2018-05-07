package uk.co.itstherules.cardplanner.model.business;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardFactModel;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class Changer {
	
	public void change(ObjectCache objectCache, CardModel card, CardFactModel fact) {
		Set<CardFactModel> cardFacts = card.getFacts();
		if(cardFacts==null) cardFacts = new LinkedHashSet<CardFactModel>();
		if(cardFacts.contains(fact)) { cardFacts.remove(fact); }
		cardFacts.add(fact); 
		card.setFacts(cardFacts);
		objectCache.save(card);
    }
	
	public void addPersonToCard(ObjectCache objectCache, CardModel card, PersonModel person) {
		Set<PersonModel> people = card.getPeople();
		if(people==null) {
			people = new LinkedHashSet<PersonModel>();
		}
		people.add(person);
		card.setPeople(people);
        objectCache.save(card);
	}
	
	public void addAttachmentToCard(ObjectCache objectCache, CardModel card, SimpleAttachmentModel attachment) {
		Set<SimpleAttachmentModel> attachments = card.getAttachments();
		if(attachments==null) {
			attachments = new LinkedHashSet<SimpleAttachmentModel>();
		}
		attachments.add(attachment);
		card.setAttachments(attachments);
        objectCache.save(card);
	}
	
}

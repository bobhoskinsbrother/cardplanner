package uk.co.itstherules.cardplanner.model.atom;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.model.atom.Entry;
import uk.co.itstherules.yawf.model.atom.EntryAuthor;
import uk.co.itstherules.yawf.model.atom.EntryContent;
import uk.co.itstherules.yawf.model.atom.EntryContributer;
import uk.co.itstherules.yawf.model.atom.Link;
import uk.co.itstherules.yawf.model.atom.Link.LinkRelValue;
import uk.co.itstherules.yawf.view.helper.UrlBuilder;

public final class CardEntryModel implements Entry {
	
	private final CardModel card;
	private final String root;

	public CardEntryModel(CardModel card, String root) {
		this.card = card;
		this.root = root;
    }

	public String getTitle() {
		return card.getTitle();
	}

	public Set<Link> getLinks() {
		UrlBuilder url = new UrlBuilder(root);
		Set<Link> set = new LinkedHashSet<Link>();
		set.add(new LinkModel("Show "+card.getTitle(), url.show("Cards", card.getIdentity(), card.getTitle()), LinkRelValue.ALTERNATE));
		CardModel parent = card.getParent();
		if(parent != null && !parent.getInvisible().booleanValue()) {
			set.add(new LinkModel("Show "+parent.getTitle(), url.show("Cards", parent.getIdentity(), parent.getTitle()), LinkRelValue.RELATED));
		}
		return set;
	}

	public String getId() {
		return card.getIdentity();
	}

	public Date getUpdated() {
		return card.getUpdated();
	}

	public Date getPublished() {
		return card.getAdded();
	}

	public EntryAuthor getAuthor() {
		return new EntryAuthorModel();
	}

	public Set<EntryContributer> getContributers() {
		Set<EntryContributer> set = new LinkedHashSet<EntryContributer>();
		Set<PersonModel> people = card.getPeople();
		for (PersonModel person : people) {
	        set.add(new PersonContributerModel(person));
        }
		return set;
	}

	public EntryContent getContent() {
		return new EntryContentModel(root, card.getBody());
	}
}

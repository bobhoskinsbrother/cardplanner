package uk.co.itstherules.cardplanner.model.atom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.yawf.model.atom.AtomModel;
import uk.co.itstherules.yawf.model.atom.Entry;
import uk.co.itstherules.yawf.model.atom.Generator;
import uk.co.itstherules.yawf.model.atom.Link;

public final class CardsAtomModel implements AtomModel {

	private final String title;
	private final Set<CardModel> cards;
	private final String subTitle;
	private String root;

	public CardsAtomModel(String title, String subTitle, String root, Set<CardModel> cards) {
		this.title = title;
		this.subTitle = subTitle;
		this.root = root;
		this.cards = cards;
    }
	
	public String getTitle() {
	    return title;
    }

	public String getSubtitle() {
	    return subTitle;
    }

	public Date getUpdated() {
		if(cards.size() > 0) {
			ArrayList<CardModel> cardsList = new ArrayList<CardModel>(cards);
			Collections.sort(cardsList, new UpdateComparator());
			return cardsList.get(0).getUpdated();
		}
	    return GregorianCalendar.getInstance().getTime();
    }

	public String getId() {
	    return "";
    }

	public Set<Link> getLinks() {
	    return new LinkedHashSet<Link>();
    }

	public String getRights() {
	    return "";
    }

	public Generator getGenerator() {
	    return new GeneratorModel("CardPlanner Feed Generator", root);
    }

	public Set<Entry> getEntries() {
	    Set<Entry> reply = new LinkedHashSet<Entry>();
	    for (CardModel card : cards) {
	    	if(!card.getInvisible().booleanValue()) {
	    		reply.add(new CardEntryModel(card, root));
	    	}
        }
		return reply;
    }
}

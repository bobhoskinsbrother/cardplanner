package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public final class TagModel extends IdentifiableDeleteableModel<TagModel> {

	public TagModel() {}
	
	@ManyToMany(mappedBy="tags") private Set<CardModel> cards;
	
    public TagModel defaultSetup(ObjectCache objectCache) {
		this.setTitle("");
		this.setSortOrder(0);
		return this;
	}
	
	public Set<CardModel> getCards() { return cards; }
	
	public TagModel(String title) {
		this.setTitle(title);
	}

	@Override
	public String toString() {
        return new StringBuilder(getIdentity()).append(getTitle()).append(", ").append(getSortOrder()).toString();
	}
}
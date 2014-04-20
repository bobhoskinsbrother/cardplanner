package uk.co.itstherules.cardplanner.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity
public final class TagModel extends IdentifiableDeleteableModel<TagModel> {

	public TagModel() {}
	
	@ManyToMany(mappedBy="tags") private Set<CardModel> cards;
	
    public TagModel defaultSetup(ObjectCache objectCache) {
		this.setIdentity("0");
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
		StringBuffer buffer = new StringBuffer();
		buffer.append(getIdentity());
		buffer.append(getTitle());
		buffer.append(", ");
		buffer.append(getSortOrder());
		return buffer.toString();
	}
}
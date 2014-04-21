package uk.co.itstherules.cardplanner.model;

import net.sf.oval.constraint.NoSelfReference;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.binder.TagsBinder;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.UseBinder;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity 
public final class CardModel extends IdentifiableDeleteableModel<CardModel>  {

	@QueryKey("body") private String body;
	@OneToOne(cascade=CascadeType.PERSIST) @QueryKey(value="effort", cache=CacheInstruction.FromCache) @NotNull private EffortModel effort;
	@OneToOne(cascade=CascadeType.PERSIST) @QueryKey(value="value", cache=CacheInstruction.FromCache) @NotNull private ValueModel value;
    @OneToOne @QueryKey(value="type", cache=CacheInstruction.FromCache) @NotNull private CardTypeModel type;
    @ManyToOne(cascade=CascadeType.PERSIST) @QueryKey(value="parent", cache=CacheInstruction.FromCache) @NoSelfReference private CardModel parent;
    @OneToMany(cascade=CascadeType.PERSIST) @QueryKey("facts") private Set<CardFactModel> facts;
    @OneToMany(mappedBy = "parent") private Set<CardModel> children;
    @OneToOne @QueryKey(value="status", cache=CacheInstruction.FromCache) @NotNull private StatusModel status;
	@ManyToMany @QueryKey("tags") @UseBinder(TagsBinder.class) private Set<TagModel> tags;
	@ManyToMany @QueryKey(value="people", cache=CacheInstruction.FromCache) private Set<PersonModel> people;
	@ManyToMany @QueryKey("attachments") private Set<SimpleAttachmentModel> attachments;

	@OneToOne(cascade=CascadeType.PERSIST) @QueryKey("storyBoard") private StoryBoardModel storyBoard;
	@QueryKey("expanded") private boolean expanded;

	public CardModel() { super(); }

    public CardModel defaultSetup(ObjectCache objectCache) {
		this.setTitle("");
		this.setSortOrder(0);
		this.body = "";
		this.parent = SpecialInstances.retrieve(objectCache, Identities.INVISIBLE_CARD);
		this.type = SpecialInstances.retrieve(objectCache, Identities.USER_STORY_ITEM_TYPE);
		this.effort = SpecialInstances.retrieve(objectCache, Identities.DEFAULT_EFFORT);
		this.value = SpecialInstances.retrieve(objectCache, Identities.DEFAULT_VALUE);
		this.status = SpecialInstances.retrieve(objectCache, Identities.THE_BACKLOG);
		this.tags = new LinkedHashSet<>();
		this.facts = new LinkedHashSet<>();
		this.people = new LinkedHashSet<>();
		this.attachments = new LinkedHashSet<>();
		this.expanded = true;
		this.storyBoard = new StoryBoardModel().defaultSetup(objectCache);
		this.storyBoard.setCard(this);
		return this;
    }

	public StoryBoardModel getStoryBoard() { return storyBoard; }
	public String getBody() { return body; }
	public ValueModel getValue() { return value; }
	public StatusModel getStatus() { return status; }
	public EffortModel getEffort() { return effort; }
	public Set<CardFactModel> getFacts() { return facts; }
	public Set<CardModel> getChildren() { return children; }
	public Set<TagModel> getTags() { return tags; }
	public Set<PersonModel> getPeople() { return people; }
	public Set<SimpleAttachmentModel> getAttachments() { return attachments; }
	public CardModel getParent() { return parent; }
	public CardTypeModel getType() { return type; }
	public boolean isExpanded() { return expanded; }

	public void setBody(String body) { this.body = body; }
	public void setValue(ValueModel value) { this.value = value; }
	public void setTags(Set<TagModel> tags) { this.tags = tags; }
	public void setFacts(Set<CardFactModel> facts) { this.facts = facts; }
	public void setAttachments(Set<SimpleAttachmentModel> attachments) { this.attachments = attachments; }
	public void setType(CardTypeModel type) { this.type = type; }
	public void setPeople(Set<PersonModel> people) { this.people = people; }
	public void setParent(CardModel parent) { this.parent = parent; }
	public void setStatus(StatusModel status) { this.status = status; }
	public void setEffort(EffortModel effort) { this.effort = effort; }
	public void setExpanded(boolean expanded) { this.expanded = expanded; }

	public boolean containsTag(TagModel tag){
		if(tags!=null) {
			return tags.contains(tag);
		}
		return false;
	}

    public String toString() {
    	return new Json<CardModel>().serialize(this, "people", "facts", "tags");
    }

    public CardModel clone() {
        CardModel clone = new CardModel();
        clone.setIdentity(getIdentity());
        clone.title = title;
        clone.objectState = objectState;
        clone.x = x;
        clone.y = y;
        clone.z = z;
        clone.setSortOrder(getSortOrder());
		clone.body = body;
		clone.parent = parent;
        clone.type = type;
		clone.effort = effort;
		clone.value = value;
		clone.status = status;
		clone.tags = new LinkedHashSet<TagModel>(tags);
		clone.facts = new LinkedHashSet<CardFactModel>(facts);
		clone.people = new LinkedHashSet<PersonModel>(people);
		clone.attachments = new LinkedHashSet<SimpleAttachmentModel>(attachments);
		clone.expanded = expanded;
		clone.storyBoard = storyBoard;
		return clone;
    }

    public double calculateEffort() {
        EffortModel effort = getEffort();
        return effort.getType().asRate(effort.getAmount());
    }

    public double calculateValue() {
        ValueModel value = getValue();
        return value.getType().asRate(value.getAmount());
    }
}
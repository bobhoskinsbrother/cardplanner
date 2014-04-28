package uk.co.itstherules.cardplanner.model;

import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.IdentityGenerator;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;

@javax.persistence.Entity
public final class LogModel implements IdentityDeleteable<LogModel>, Comparable<LogModel> {

    @Id @Column(length=36) @NotNull private String identity;
    @OneToOne(cascade = CascadeType.ALL) @NotNull private CardModel card;
    @OneToOne(cascade = CascadeType.ALL) @NotNull private StatusModel fromStatus;
    @OneToOne(cascade = CascadeType.ALL) @NotNull private StatusModel toStatus;
    @Temporal(TemporalType.TIMESTAMP) @NotNull private Date date;
    @Enumerated(EnumType.STRING) protected ObjectState objectState;
    @NotNull private String action;

    public LogModel() {}
	
	public LogModel(CardModel card, StatusModel fromStatus, StatusModel toStatus, String action) {
        IdentityGenerator.generateIdentity(this);
		this.card = card;
		this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.action = action;
		this.date = new Date(GregorianCalendar.getInstance().getTimeInMillis());
        this.objectState = ObjectState.Active;
	}

	public Date getDate() { return date; }
	public CardModel getCard() { return card; }
	public StatusModel getFromStatus() { return fromStatus; }
	public StatusModel getToStatus() { return toStatus; }
	public String getAction() { return action; }

    public int compareTo(LogModel other) {
        String identity = this.getIdentity();
        String otherIdentity = other.getIdentity();
        return identity.compareTo(otherIdentity);
    }

    @Override
	public String toString() {
		return new Json<Object>().serialize(this);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogModel)) return false;

        LogModel logModel = (LogModel) o;

        if (action != null ? !action.equals(logModel.action) : logModel.action != null) return false;
        if (card != null ? !card.equals(logModel.card) : logModel.card != null) return false;
        if (date != null ? !date.equals(logModel.date) : logModel.date != null) return false;
        if (fromStatus != null ? !fromStatus.equals(logModel.fromStatus) : logModel.fromStatus != null) return false;
        return !(toStatus != null ? !toStatus.equals(logModel.toStatus) : logModel.toStatus != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + (fromStatus != null ? fromStatus.hashCode() : 0);
        result = 31 * result + (toStatus != null ? toStatus.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    public LogModel defaultSetup(ObjectCache objectCache) { return this; }


    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Boolean getActive() { return Boolean.TRUE; }
    public Boolean getArchived() { return Boolean.FALSE; }
    public Boolean getDeleted() { return Boolean.FALSE; }
    public Boolean getInvisible() { return Boolean.FALSE; }
    public Boolean getPending() { return Boolean.FALSE; }
    public void activate() { }
    public void archive() { }
    public void delete() { }
    public String getIdentity() { return identity; }
    public void makeInvisible() { }
    public int getSortOrder() { return 0; }
    public void setSortOrder(Integer sortOrder) {
    }
    public void pending() {
    }
    public void updateTimestamp() {
    }
    public Date getUpdated() {
        return date;
    }
    public Date getAdded() {
        return date;
    }
}
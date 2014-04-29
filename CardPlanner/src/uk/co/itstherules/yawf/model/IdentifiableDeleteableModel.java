package uk.co.itstherules.yawf.model;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.serializer.Json;

@MappedSuperclass
public abstract class IdentifiableDeleteableModel<T> implements Entity<T>, Comparable<Entity<T>> {

	@Id @Column(length=36) @QueryKey("identity") @NotNull private String identity;
    @QueryKey("title") @NotBlank protected String title;
    @QueryKey("order") private Integer sortOrder = 0;
    @QueryKey("x") protected Integer x;
    @QueryKey("y") protected Integer y;
    @QueryKey("z") protected Integer z;

    @Enumerated(EnumType.STRING) @QueryKey("objectState") protected ObjectState objectState;

	private Date updated;
	private Date added;

	public IdentifiableDeleteableModel() {
        IdentityGenerator.generateIdentity(this);
        this.setSortOrder(0);
		this.title = "";
		this.added = GregorianCalendar.getInstance().getTime();
        updateTimestamp();
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.activate();
	}

    public final int getSortOrder() { return sortOrder; }
	public final void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
	public Integer getX() { return x; }
	public Integer getY() { return y; }
	public Integer getZ() { return z; }

	

	public Boolean getActive() { return objectState==ObjectState.Active; }
	public Boolean getExpired() { return objectState==ObjectState.Expired; }
	public Boolean getLocked() { return objectState==ObjectState.Locked; }
	public Boolean getPending() {  return objectState==ObjectState.Pending; }
	public Boolean getArchived() { return objectState==ObjectState.Archived; }
	public Boolean getDeleted() { return objectState == ObjectState.Deleted; }
	public Boolean getInvisible() { return objectState==ObjectState.Invisible; }

	public void activate() { objectState=ObjectState.Active; }
	public void archive() { objectState=ObjectState.Archived; }
	public void delete() { objectState=ObjectState.Deleted; }
	public void makeInvisible() { objectState=ObjectState.Invisible;	}

	public void expire() { objectState=ObjectState.Expired;	}
	public void lock() { objectState=ObjectState.Locked; }
	public void pending() { objectState=ObjectState.Pending; }
	
	public void setIdentity(String identity) { this.identity = identity; }

	public Date getUpdated() { return this.updated; }
	public Date getAdded() { return this.added; }
	public String getTitle() { return this.title; }
	public String getIdentity() { return identity; }

	public void updateTimestamp() { this.updated = GregorianCalendar.getInstance().getTime(); }
	public void setTitle(String title) { this.title = title; }
	public void setAdded(Date added) { this.added = added; }

	public int compareTo(Entity<T> other) {
        Assertion.checkNotNull(other);
        String identity = this.getIdentity();
		String otherIdentity = other.getIdentity();
		return identity.compareTo(otherIdentity);
	}

    public boolean equals(Entity<T> o) {
        if (o == null) { return false; }
        if (getIdentity() == null) return false;
        return getIdentity().equals(o.getIdentity());
    }

    public int hashCode() {
        if (getIdentity() != null) {
            return getIdentity().hashCode();
        } else {
            return super.hashCode();
        }
    }

    public String toString() {
		return new Json<IdentifiableDeleteableModel<T>>().serialize(this);
    }
}

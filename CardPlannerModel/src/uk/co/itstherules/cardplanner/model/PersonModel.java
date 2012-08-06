package uk.co.itstherules.cardplanner.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;
import uk.co.itstherules.yawf.security.objectcache.GroupModel;
import uk.co.itstherules.yawf.security.objectcache.UserModel;

@Entity  
public final class PersonModel extends IdentifiableDeleteableModel<PersonModel> {

	public enum Gender {Female, Male}
	
	@QueryKey("visible") private boolean visible = true;
	@QueryKey("firstName") @NotNull @NotBlank private String firstName;
	@QueryKey("lastName") @NotNull @NotBlank private String lastName;
	@QueryKey("initials") private String initials;
	@Enumerated(EnumType.STRING) @QueryKey("gender") private Gender gender;
	@ManyToOne @QueryKey(value="picture", cache=CacheInstruction.FromCache) SimpleAttachmentModel picture;
	@ManyToMany @QueryKey("organisations") private Set<OrganisationModel> organisations = new LinkedHashSet<OrganisationModel>();
	@ManyToMany(mappedBy="people") private Set<CardModel> cards;
	@OneToOne(cascade=CascadeType.PERSIST) @QueryKey("user") private UserModel user;

	public PersonModel() { 
		super(); 
		this.user = new UserModel();
		this.initials = "";
	}
	
	public PersonModel defaultSetup(ObjectCache objectCache) {
		this.picture = SpecialInstances.retrieve(objectCache, Identities.DEFAULT_ATTACHMENT);
		this.gender = Gender.Male;
	    return this;
	}

	public void addGroup(GroupModel group) { this.user.addGroup(group); }
	
	public boolean getVisible() { return this.visible; }
	public String getEmail() { return this.user.getEmail(); }
	public String getFirstName() { return this.firstName; }
	public Gender getGender() { return this.gender; }
	public Set<GroupModel> getGroups() { return this.user.getGroups(); }
	public String getInitials() { return this.initials; }
	public String getLastName() { return this.lastName; }
	public Set<OrganisationModel> getOrganisations() { return new LinkedHashSet<OrganisationModel>(this.organisations); }
	public String getPassword() { return this.user.getPassword(); }
	public SimpleAttachmentModel getPicture() { return this.picture; }
	public UserModel getUser() { return this.user; }
	public String getUserName() { return this.user.getUserName(); }
	public Set<CardModel> getCards() { return this.cards; }
	public boolean isAccountNonExpired() { return !getExpired(); }
	public boolean isAccountNonLocked() { return !getLocked(); }
	public boolean isCredentialsNonExpired() { return !getExpired(); }
	public boolean isEnabled() { return getActive(); }
	public void setGroups(Set<GroupModel> groups) { this.user.setGroups(groups); }
	public void setInitials(String initials) { this.initials = initials; }
	public void setPassword(String password) { this.user.setPassword(password); }
	public void setPicture(SimpleAttachmentModel picture) { this.picture = picture;  }
	public void setUsername(String userName) { this.user.setUsername(userName); }
	@Override public String toString() { return new Json<Object>().serialize(this); }
}

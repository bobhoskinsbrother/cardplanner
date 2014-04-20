package uk.co.itstherules.cardplanner.model;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity  
public final class PersonModel extends IdentifiableDeleteableModel<PersonModel> {

	public enum Gender {Female, Male}
	
	@QueryKey("visible") private boolean visible = true;
	@QueryKey("firstName") @NotNull @NotBlank private String firstName;
	@QueryKey("lastName") @NotNull @NotBlank private String lastName;
	@QueryKey("email") @NotNull @Email private String email;
	@QueryKey("initials") private String initials;
	@Enumerated(EnumType.STRING) @QueryKey("gender") private Gender gender;
	@ManyToOne @QueryKey(value="picture", cache=CacheInstruction.FromCache) SimpleAttachmentModel picture;
	@ManyToMany @QueryKey("organisations") private Set<OrganisationModel> organisations = new LinkedHashSet<OrganisationModel>();
	@ManyToMany(mappedBy="people") private Set<CardModel> cards;

	public PersonModel() { 
		super(); 
		this.initials = "";
	}
	
	public PersonModel defaultSetup(ObjectCache objectCache) {
		this.picture = SpecialInstances.retrieve(objectCache, Identities.DEFAULT_ATTACHMENT);
		this.gender = Gender.Male;
	    return this;
	}

	public boolean getVisible() { return this.visible; }
	public String getEmail() { return this.email; }
	public String getFirstName() { return this.firstName; }
	public Gender getGender() { return this.gender; }
	public String getInitials() { return this.initials; }
	public String getLastName() { return this.lastName; }
	public Set<OrganisationModel> getOrganisations() { return new LinkedHashSet<OrganisationModel>(this.organisations); }
	public SimpleAttachmentModel getPicture() { return this.picture; }
	public Set<CardModel> getCards() { return this.cards; }
	public boolean isAccountNonExpired() { return !getExpired(); }
	public boolean isAccountNonLocked() { return !getLocked(); }
	public boolean isCredentialsNonExpired() { return !getExpired(); }
	public boolean isEnabled() { return getActive(); }
	public void setInitials(String initials) { this.initials = initials; }
	public void setPicture(SimpleAttachmentModel picture) { this.picture = picture;  }
	@Override public String toString() { return new Json<Object>().serialize(this); }
}

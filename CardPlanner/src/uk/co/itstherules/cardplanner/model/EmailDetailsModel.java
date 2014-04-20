package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.NotBlank;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.email.EmailDetails;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity  
public final class EmailDetailsModel extends IdentifiableDeleteableModel<EmailDetailsModel> implements EmailDetails {
	
	@QueryKey("emailHost") @NotBlank private String emailHost;
	@QueryKey("emailUserName") @NotBlank private String emailUserName;
	@QueryKey("emailPassword") @NotBlank private String emailPassword;
	@QueryKey("emailFromEmail") @NotBlank @Email private String emailFromEmail;
	@QueryKey("emailFromName") @NotBlank private String emailFromName;

	public EmailDetailsModel() { activate(); }
	
	public EmailFlavour getFlavour() {
		return EmailFlavour.GMail;
	}

	public String getFromEmail() {
		return emailFromEmail;
	}

	public String getFromName() {
		return emailFromName;
	}

	public String getHost() {
		return emailHost;
	}

	public String getPassword() {
		return emailPassword;
	}

	public String getUserName() {
		return emailUserName;
	}
	
	public void archive() { }
	public void delete() { }
	public void makeInvisible() {	}
	public void expire() { }
	public void lock() { }
	public void pending() { }

    public EmailDetailsModel defaultSetup(ObjectCache objectCache) { return this; }

}

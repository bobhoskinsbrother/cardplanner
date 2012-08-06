package uk.co.itstherules.yawf.model.email;


public interface Mailer {
	void email(String to, String fullName, String subject, String body, EmailDetails emailDetails);
}
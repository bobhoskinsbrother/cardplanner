package uk.co.itstherules.yawf.model.email;


public class Emailer implements Mailer {

	@Override
    public void email(String to, String fullName, String subject, String body,
            EmailDetails emailDetails) {
		if(emailDetails.getFlavour() != EmailDetails.EmailFlavour.GMail) {
			throw new RuntimeException("Only GMail currently implemented");
		}
		new GMailer().email(to, fullName, subject, body, emailDetails);
    }
	
	
	
}

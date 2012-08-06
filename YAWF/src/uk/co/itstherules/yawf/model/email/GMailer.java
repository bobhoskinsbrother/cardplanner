package uk.co.itstherules.yawf.model.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


public class GMailer implements Mailer {
	
    public void email(String to, String fullName, String subject, String body, EmailDetails emailDetails) {
		try {

			HtmlEmail email = new HtmlEmail();
			if(!"".equals(emailDetails.getUserName())) { email.setAuthenticator(new DefaultAuthenticator(emailDetails.getUserName(), emailDetails.getPassword())); }
			email.setHostName(emailDetails.getHost());
			email.addTo(to, fullName);
			email.setFrom(emailDetails.getFromEmail(), emailDetails.getFromName());
			email.getMailSession().getProperties().put("mail.smtp.auth", "true");
			email.getMailSession().getProperties().put("mail.smtp.port", "465");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			
			email.setSubject(subject);
			email.setHtmlMsg(body);

			email.send();
		} catch (EmailException e) {
	        throw new UnableToSendEmailException(e);
        }
	}
	
}

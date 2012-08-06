package uk.co.itstherules.yawf.model.email;

import org.apache.commons.mail.EmailException;

public class UnableToSendEmailException extends RuntimeException {
	public UnableToSendEmailException(EmailException e) {
		super(e);
    }

	private static final long serialVersionUID = -8066415632306874254L;
}

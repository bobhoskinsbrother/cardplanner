//$Id: Contact.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test;

import org.hibernate.validator.NotNull;

/**
 * @author Emmanuel Bernard
 */
public class Contact {
	@NotNull
	private String name;
	@NotNull
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
//$Id: User.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test;

import org.hibernate.validator.Email;
import org.hibernate.validator.NotNull;

/**
 * @author Emmanuel Bernard
 */
public class User {
	@NotNull
	public String name;
	@Email
	public String email;
}

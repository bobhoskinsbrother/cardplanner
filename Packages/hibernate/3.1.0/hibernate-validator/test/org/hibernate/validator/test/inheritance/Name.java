//$Id: Name.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.inheritance;

import org.hibernate.validator.NotNull;

/**
 * @author Emmanuel Bernard
 */
public interface Name {
	@NotNull
	String getName();

	void setName(String name);
}

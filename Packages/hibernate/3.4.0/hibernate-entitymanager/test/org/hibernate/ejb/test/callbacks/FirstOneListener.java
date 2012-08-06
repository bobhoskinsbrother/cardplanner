//$Id: FirstOneListener.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb.test.callbacks;

import javax.persistence.PreUpdate;

/**
 * @author Emmanuel Bernard
 */
public class FirstOneListener {
	@PreUpdate
	public void firstOne(CommunicationSystem object) {
		if ( !object.isFirst ) throw new IllegalStateException();
		object.isFirst = true;
		object.isLast = false;
		object.communication++;
	}
}

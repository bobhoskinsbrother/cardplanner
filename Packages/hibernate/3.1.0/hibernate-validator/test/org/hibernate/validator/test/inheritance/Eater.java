//$Id: Eater.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.inheritance;

import org.hibernate.validator.Min;

/**
 * @author Emmanuel Bernard
 */
public interface Eater {
	@Min(2)
	int getFrequency();

	void setFrequency(int frequency);
}

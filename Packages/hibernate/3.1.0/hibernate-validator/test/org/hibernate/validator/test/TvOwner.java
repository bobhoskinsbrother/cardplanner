//$Id: TvOwner.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

/**
 * @author Emmanuel Bernard
 */
public class TvOwner {
	public Integer id;

	@NotNull
	@Valid
	public Tv tv;
}

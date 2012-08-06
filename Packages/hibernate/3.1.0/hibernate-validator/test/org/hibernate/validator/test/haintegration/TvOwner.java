//$Id: TvOwner.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.haintegration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotNull;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class TvOwner {
	@Id
	@GeneratedValue
	public Integer id;
	@ManyToOne
	@NotNull
	public Tv tv;
}

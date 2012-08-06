//$Id: Tv.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.haintegration;

import java.util.Date;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.Future;
import org.hibernate.validator.Length;
import org.hibernate.validator.Min;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class Tv {
	@Id
	@Length(max = 2)
	public String serial;
	public int size;
	@Length(max = 2)
	public String name;
	@Future
	public Date expDate;
	@Length(min = 0)
	public String description;
	@Min(1000)
	public BigInteger lifetime;
}

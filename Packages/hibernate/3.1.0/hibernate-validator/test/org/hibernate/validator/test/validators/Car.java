//$Id: Car.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.validators;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;

import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Digits;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class Car {
	@Id @GeneratedValue
	public Long id;
	@NotEmpty
	public String name;
	@NotEmpty
	public String[] insurances;
	@Digits(integerDigits = 1, fractionalDigits = 2)
	public BigDecimal length;
	@Digits(integerDigits = 2, fractionalDigits = 1)
	public Double gallons;
	@Digits(integerDigits = 2, fractionalDigits = 1)
	public String mpg;
}

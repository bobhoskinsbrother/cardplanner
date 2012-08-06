//$Id: MarsAddress.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.haintegration;

import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author Emmanuel Bernard
 */
@Embeddable
public class MarsAddress {
	private String continent;
	private String canal;

	@NotNull
	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	@Length(min = 5)
	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}
}

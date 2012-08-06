//$Id: VenusianPk.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test.haintegration;

import java.io.Serializable;

/**
 * @author Emmanuel Bernard
 */
public class VenusianPk implements Serializable {
	private String region;
	private String name;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		if ( this == o ) return true;
		if ( o == null || getClass() != o.getClass() ) return false;

		final VenusianPk that = (VenusianPk) o;

		if ( !name.equals( that.name ) ) return false;
		if ( !region.equals( that.region ) ) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = region.hashCode();
		result = 29 * result + name.hashCode();
		return result;
	}
}

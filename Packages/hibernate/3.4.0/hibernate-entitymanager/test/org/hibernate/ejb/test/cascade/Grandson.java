//$Id: Grandson.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb.test.cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class Grandson {
	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Son parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Son getParent() {
		return parent;
	}

	public void setParent(Son parent) {
		this.parent = parent;
	}
}

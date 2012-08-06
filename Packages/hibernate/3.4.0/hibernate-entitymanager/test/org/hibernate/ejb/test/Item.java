//$Id: Item.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb.test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;

/**
 * @author Gavin King
 */
@Entity(name = "Item")
@SqlResultSetMapping(name = "getItem", entities =
@EntityResult(entityClass = org.hibernate.ejb.test.Item.class, fields = {
@FieldResult(name = "name", column = "itemname"),
@FieldResult(name = "descr", column = "itemdescription")
		})
)
//@Cache(region="Item", usage=NONSTRICT_READ_WRITE)
public class Item implements Serializable {

	private String name;
	private String descr;
	private Set<Distributor> distributors = new HashSet<Distributor>();

	public Item() {
	}

	public Item(String name, String desc) {
		this.name = name;
		this.descr = desc;
	}

	@Column(length = 200)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String desc) {
		this.descr = desc;
	}

	@Id
	@Column(length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany
	public Set<Distributor> getDistributors() {
		return distributors;
	}

	public void setDistributors(Set<Distributor> distributors) {
		this.distributors = distributors;
	}

	public void addDistributor(Distributor d) {
		if ( distributors == null ) distributors = new HashSet();
		distributors.add( d );
	}
}

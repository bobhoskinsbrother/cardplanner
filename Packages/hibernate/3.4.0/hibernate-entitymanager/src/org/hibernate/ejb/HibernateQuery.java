//$Id: HibernateQuery.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb;

import javax.persistence.Query;

public interface HibernateQuery extends Query {
	public org.hibernate.Query getHibernateQuery();
}

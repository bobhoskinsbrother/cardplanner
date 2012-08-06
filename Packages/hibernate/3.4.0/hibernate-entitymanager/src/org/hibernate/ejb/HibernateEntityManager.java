//$Id: HibernateEntityManager.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb;

import javax.persistence.EntityManager;

import org.hibernate.Session;

/**
 * @author Gavin King
 */
public interface HibernateEntityManager extends EntityManager {
	public Session getSession();
}

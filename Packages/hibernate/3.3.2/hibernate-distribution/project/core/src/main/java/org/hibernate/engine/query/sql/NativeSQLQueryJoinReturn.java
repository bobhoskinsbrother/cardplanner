/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package org.hibernate.engine.query.sql;

import java.util.Map;

import org.hibernate.LockMode;

/**
 * Represents a return defined as part of a native sql query which
 * names a fetched role.
 *
 * @author Steve Ebersole
 */
public class NativeSQLQueryJoinReturn extends NativeSQLQueryNonScalarReturn {
	private String ownerAlias;
	private String ownerProperty;

	/**
	 * Construct a return descriptor representing some form of fetch.
	 *
	 * @param alias The result alias
	 * @param ownerAlias The owner's result alias
	 * @param ownerProperty The owner's property representing the thing to be fetched
	 * @param propertyResults Any user-supplied column->property mappings
	 * @param lockMode The lock mode to apply
	 */
	public NativeSQLQueryJoinReturn(
			String alias,
			String ownerAlias,
			String ownerProperty,
			Map propertyResults,
			LockMode lockMode) {
		super( alias, propertyResults, lockMode );
		this.ownerAlias = ownerAlias;
		this.ownerProperty = ownerProperty;
	}

	/**
	 * Retrieve the alias of the owner of this fetched association.
	 *
	 * @return The owner's alias.
	 */
	public String getOwnerAlias() {
		return ownerAlias;
	}

	/**
	 * Retrieve the property name (relative to the owner) which maps to
	 * the association to be fetched.
	 *
	 * @return The property name.
	 */
	public String getOwnerProperty() {
		return ownerProperty;
	}
}

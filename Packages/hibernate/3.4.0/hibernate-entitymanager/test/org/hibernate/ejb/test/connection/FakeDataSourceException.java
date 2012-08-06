//$Id: FakeDataSourceException.java 11282 2007-03-14 22:05:59Z epbernard $
package org.hibernate.ejb.test.connection;

/**
 * @author Emmanuel Bernard
 */
public class FakeDataSourceException extends RuntimeException {
	public FakeDataSourceException(String message) {
		super( message );
	}
}

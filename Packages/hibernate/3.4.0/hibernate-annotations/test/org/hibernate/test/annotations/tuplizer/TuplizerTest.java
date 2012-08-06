//$Id: TuplizerTest.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.tuplizer;

import org.hibernate.test.annotations.TestCase;
import org.hibernate.Session;

/**
 * @author Emmanuel Bernard
 */
public class TuplizerTest extends TestCase {
	public void testEntityTuplizer() throws Exception {
		Cuisine cuisine = ProxyHelper.newCuisineProxy( null );
		cuisine.setName( "Fran�aise" );
		Country country = ProxyHelper.newCountryProxy( null );
		country.setName( "France" );
		cuisine.setCountry( country );
		Session s = openSession( new EntityNameInterceptor() );
		s.getTransaction().begin();
		s.persist( cuisine );
		s.flush();
		s.clear();
		cuisine = (Cuisine) s.get(Cuisine.class, cuisine.getId() );
		assertNotNull( cuisine );
		assertEquals( "Fran�aise", cuisine.getName() );
		assertEquals( "France", country.getName() );
		s.getTransaction().rollback();
		s.close();
	}
	protected Class[] getMappings() {
		return new Class[] {
				Cuisine.class
		};
	}
}

//$Id: CascadeTest.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.cascade;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.test.annotations.TestCase;

/**
 * Check some of the individual cascade styles
 *
 * @author Emmanuel Bernard
 */
//FIXME do somthing for refresh
public class CascadeTest extends TestCase {
	public void testPersist() {
		Session s;
		Transaction tx;
		s = openSession();
		Tooth tooth = new Tooth();
		Tooth leftTooth = new Tooth();
		tooth.leftNeighbour = leftTooth;
		s.persist( tooth );
		tx = s.beginTransaction();
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		leftTooth = (Tooth) s.get( Tooth.class, leftTooth.id );
		assertNotNull( leftTooth );
		tx.commit();
		s.close();
	}

	public void testMerge() {
		Session s;
		Transaction tx;
		s = openSession();
		Tooth tooth = new Tooth();
		Tooth rightTooth = new Tooth();
		tooth.type = "canine";
		tooth.rightNeighbour = rightTooth;
		rightTooth.type = "incisive";
		s.persist( rightTooth );
		s.persist( tooth );
		tx = s.beginTransaction();
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		tooth = (Tooth) s.get( Tooth.class, tooth.id );
		assertEquals( "incisive", tooth.rightNeighbour.type );
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		tooth.rightNeighbour.type = "premolars";
		s.merge( tooth );
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		rightTooth = (Tooth) s.get( Tooth.class, rightTooth.id );
		assertEquals( "premolars", rightTooth.type );
		tx.commit();
		s.close();
	}

	public void testRemove() {
		Session s;
		Transaction tx;
		s = openSession();
		tx = s.beginTransaction();
		Tooth tooth = new Tooth();
		Mouth mouth = new Mouth();
		s.persist( mouth );
		s.persist( tooth );
		tooth.mouth = mouth;
		mouth.teeth = new ArrayList<Tooth>();
		mouth.teeth.add( tooth );
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		tooth = (Tooth) s.get( Tooth.class, tooth.id );
		assertNotNull( tooth );
		s.delete( tooth.mouth );
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		tooth = (Tooth) s.get( Tooth.class, tooth.id );
		assertNull( tooth );
		tx.commit();
		s.close();
	}

	public CascadeTest(String x) {
		super( x );
	}

	protected Class[] getMappings() {
		return new Class[]{
				Mouth.class,
				Tooth.class
		};
	}
}

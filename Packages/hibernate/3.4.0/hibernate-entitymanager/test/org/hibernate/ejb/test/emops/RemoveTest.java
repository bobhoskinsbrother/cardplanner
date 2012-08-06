//$Id: $
package org.hibernate.ejb.test.emops;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import org.hibernate.ejb.test.TestCase;

/**
 * @author Emmanuel Bernard
 */
public class RemoveTest extends TestCase {
	public void testRemove() {
		Race race = new Race();
		race.competitors.add( new Competitor() );
		race.competitors.add( new Competitor() );
		race.competitors.add( new Competitor() );
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist( race );
		em.flush();
		em.remove( race );
		em.flush();
		em.getTransaction().rollback();
		em.close();
	}

	public void testRemoveAndFind() {
		Race race = new Race();
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist( race );
		em.remove( race );
		assertNull( em.find( Race.class, race.id ) );
		em.getTransaction().rollback();
		em.close();
	}

	public void testUpdatedAndRemove() {
		Music music = new Music();
		music.setName( "Classical" );
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist( music );
		em.getTransaction().commit();

		em.clear();

		em = factory.createEntityManager();
		em.getTransaction().begin();
		EntityManager em2 = factory.createEntityManager();
		em2.getTransaction().begin();

		//read music from 2nd EM
		music = em2.find( Music.class, music.getId() );

		//change music
		em.find( Music.class, music.getId() ).setName( "Rap" );
		em.getTransaction().commit();

		try {
			em2.remove( music ); //remove changed music
			em2.flush();
			fail("should have an optimistic lock exception");
		}
		catch( OptimisticLockException e ) {
			//success
		}
		finally {
			em2.getTransaction().rollback();
		}

		//clean
		em.remove( em.find( Music.class, music.getId() ) );
		
		em.close();
		em2.close();
	}

	public Class[] getAnnotatedClasses() {
		return new Class[] {
				Race.class,
				Competitor.class,
				Music.class
		};
	}


	public Map getConfig() {
		Map cfg =  super.getConfig();
		cfg.put( "hibernate.jdbc.batch_size", "0");
		return cfg;
	}
}

// $Id: AbstractEntityManagerImpl.java 14812 2008-06-25 10:07:04Z hardy.ferentschik $
/*
 * JBoss, the OpenSource EJB server Distributable under LGPL license. See terms of license at
 * gnu.org.
 */
package org.hibernate.ejb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.hibernate.AssertionFailure;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.QueryException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.TransientObjectException;
import org.hibernate.TypeMismatchException;
import org.hibernate.UnresolvableObjectException;
import org.hibernate.cfg.Environment;
import org.hibernate.ejb.transaction.JoinableCMTTransaction;
import org.hibernate.ejb.util.ConfigurationHelper;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.transaction.TransactionFactory;
import org.hibernate.util.CollectionHelper;
import org.hibernate.util.JTAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:gavin@hibernate.org">Gavin King</a>
 * @author Emmanuel Bernard
 */
@SuppressWarnings("unchecked")
public abstract class AbstractEntityManagerImpl implements HibernateEntityManagerImplementor, Serializable {
	private static final Logger log = LoggerFactory.getLogger( AbstractEntityManagerImpl.class );

	protected transient TransactionImpl tx = new TransactionImpl( this );
	protected PersistenceContextType persistenceContextType;
	private FlushModeType flushModeType = FlushModeType.AUTO;
	private PersistenceUnitTransactionType transactionType;
	private Map properties;

	protected AbstractEntityManagerImpl(
			PersistenceContextType type, PersistenceUnitTransactionType transactionType, Map properties) {
		this.persistenceContextType = type;
		this.transactionType = transactionType;
		this.properties = properties != null ? properties : CollectionHelper.EMPTY_MAP;
	}

	protected void postInit() {
		//register in Sync if needed
		if ( PersistenceUnitTransactionType.JTA.equals( transactionType ) ) joinTransaction( true );
		Object flushMode = properties.get( "org.hibernate.flushMode" );
		if (flushMode != null) {
			getSession().setFlushMode( ConfigurationHelper.getFlushMode( flushMode ) );
		}
		this.properties = null;
	}

	public Query createQuery(String ejbqlString) {
		//adjustFlushMode();
		try {
			return new QueryImpl( getSession().createQuery( ejbqlString ), this );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	public Query createNamedQuery(String name) {
		//adjustFlushMode();
		org.hibernate.Query namedQuery;
		try {
			namedQuery = getSession().getNamedQuery( name );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException("Named query not found: " + name);
		}
		try {
			return new QueryImpl( namedQuery, this );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	public Query createNativeQuery(String sqlString) {
		//adjustFlushMode();
		try {
			SQLQuery q = getSession().createSQLQuery( sqlString );
			return new QueryImpl( q, this );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	public Query createNativeQuery(String sqlString, Class resultClass) {
		//adjustFlushMode();
		try {
			SQLQuery q = getSession().createSQLQuery( sqlString );
			q.addEntity( "alias1", resultClass.getName(), LockMode.READ );
			return new QueryImpl( q, this );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		//adjustFlushMode();
		try {
			SQLQuery q = getSession().createSQLQuery( sqlString );
			q.setResultSetMapping( resultSetMapping );
			return new QueryImpl( q, this );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		//adjustFlushMode();
		try {
			return (T) getSession().load( entityClass, (Serializable) primaryKey );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (TypeMismatchException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (ClassCastException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <A> A find(Class<A> entityClass, Object primaryKey) {
		//adjustFlushMode();
		try {
			return (A) getSession().get( entityClass, (Serializable) primaryKey );
		}
		catch (ObjectDeletedException e) {
			//the spec is silent about people doing remove() find() on the same PC
			return null;
		}
		catch (ObjectNotFoundException e) {
			//should not happen on the entity itself with get
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (TypeMismatchException e ) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (ClassCastException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	private void checkTransactionNeeded() {
		if ( persistenceContextType == PersistenceContextType.TRANSACTION && ! isTransactionInProgress() ) {
			//no need to mark as rollback, no tx in progress
			throw new TransactionRequiredException(
					"no transaction is in progress for a TRANSACTION type persistence context"
			);
		}
	}

	public void persist(Object entity) {
		checkTransactionNeeded();
		//adjustFlushMode();
		try {
			getSession().persist( entity );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage() );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
		}
	}

	@SuppressWarnings("unchecked")
	public <A> A merge(A entity) {
		checkTransactionNeeded();
		//adjustFlushMode();
		try {
			return (A) getSession().merge( entity );
		}
		catch (ObjectDeletedException sse) {
			throw new IllegalArgumentException( sse );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return null;
		}
	}

	public void remove(Object entity) {
		checkTransactionNeeded();
		//adjustFlushMode();
		try {
			getSession().delete( entity );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
		}
	}

	public void refresh(Object entity) {
		checkTransactionNeeded();
		//adjustFlushMode();
		try {
			if ( ! getSession().contains( entity ) ) {
				throw new IllegalArgumentException( "Entity not managed" );
			}
			getSession().refresh( entity );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
		}
	}

	public boolean contains(Object entity) {
		try {
			if ( entity != null
					&& ! ( entity instanceof HibernateProxy )
					&& getSession().getSessionFactory().getClassMetadata( entity.getClass() ) == null ) {
				throw new IllegalArgumentException( "Not an entity:" + entity.getClass() );
			}
			return getSession().contains( entity );
		}
		catch (MappingException e) {
			throw new IllegalArgumentException( e.getMessage(), e );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
			return false;
		}
	}

	public void flush() {
		try {
			if ( ! isTransactionInProgress() ) {
				throw new TransactionRequiredException( "no transaction is in progress" );
			}
			//adjustFlushMode();
			getSession().flush();
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
		}
	}

	/**
	 * return a Session
	 * @throws IllegalStateException if the entity manager is closed
	 */
	public abstract Session getSession();

	/**
	 * Return a Session (even if the entity manager is closed
	 */
	protected abstract Session getRawSession();

	public EntityTransaction getTransaction() {
		if ( transactionType == PersistenceUnitTransactionType.JTA ) {
			throw new IllegalStateException( "A JTA EntityManager cannot use getTransaction()" );
		}
		return tx;
	}

	public void setFlushMode(FlushModeType flushModeType) {
		this.flushModeType = flushModeType;
		if ( flushModeType == FlushModeType.AUTO ) {
			getSession().setFlushMode( FlushMode.AUTO );
		}
		else if ( flushModeType == FlushModeType.COMMIT ) {
			getSession().setFlushMode( FlushMode.COMMIT );
		}
		else {
			throw new AssertionFailure( "Unknown FlushModeType: " + flushModeType );
		}
	}

	public void clear() {
		//adjustFlushMode();
		try {
			getSession().clear();
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
		}
	}

	public FlushModeType getFlushMode() {
		FlushMode mode = getSession().getFlushMode();
		if ( mode == FlushMode.AUTO ) {
			this.flushModeType = FlushModeType.AUTO;
		}
		else if ( mode == FlushMode.COMMIT ) {
			this.flushModeType = FlushModeType.COMMIT;
		}
//		else if ( mode == FlushMode.NEVER ) {
//			if ( PersistenceContextType.EXTENDED == persistenceContextType && !isTransactionInProgress() ) {
//				//we are in flushMode none for EXTENDED
//				return flushMode;
//			}
//			else {
//				return null; //TODO exception?
//			}
//		}
		else {
			return null; //TODO exception?
		}
		//otherwise this is an unknown mode for EJB3
		return flushModeType;
	}

	public void lock(Object entity, LockModeType lockMode) {
		try {
			if ( ! isTransactionInProgress() ) {
				throw new TransactionRequiredException( "no transaction is in progress" );
			}
			//adjustFlushMode();
			if ( !contains( entity ) ) throw new IllegalArgumentException( "entity not in the persistence context" );
			getSession().lock( entity, getLockMode( lockMode ) );
		}
		catch (HibernateException he) {
			throwPersistenceException( he );
		}
	}

	private LockMode getLockMode(LockModeType lockMode) {
		switch ( lockMode ) {
			case READ:
				return LockMode.UPGRADE; //assuming we are on read-commited and we need to prevent non repeteable read
			case WRITE:
				return LockMode.FORCE;
			default:
				throw new AssertionFailure( "Unknown LockModeType: " + lockMode );
		}
	}

	public boolean isTransactionInProgress() {
		return ( (SessionImplementor) getRawSession() ).isTransactionInProgress();
	}

	protected void markAsRollback() {
		log.debug( "mark transaction for rollback");
		if ( tx.isActive() ) {
			tx.setRollbackOnly();
		}
		else {
			//no explicit use of the tx. boudaries methods
			if ( PersistenceUnitTransactionType.JTA == transactionType ) {
				TransactionManager transactionManager =
						( (SessionFactoryImplementor) getRawSession().getSessionFactory() ).getTransactionManager();
				if ( transactionManager == null ) {
					throw new PersistenceException(
							"Using a JTA persistence context wo setting hibernate.transaction.manager_lookup_class"
					);
				}
				try {
					transactionManager.setRollbackOnly();
				}
				catch (SystemException e) {
					throw new PersistenceException( "Unable to set the JTA transaction as RollbackOnly", e );
				}
			}
		}
	}

	public void joinTransaction() {
		joinTransaction( false );
	}

	private void joinTransaction(boolean ignoreNotJoining) {
		//set the joined status
		getSession().isOpen(); //for sync
		if ( transactionType == PersistenceUnitTransactionType.JTA ) {
			try {
				log.debug( "Looking for a JTA transaction to join" );
				final Session session = getSession();
				final Transaction transaction = session.getTransaction();
				if ( transaction != null && transaction instanceof JoinableCMTTransaction ) {
					//can't handle it if not a joinnable transaction
					final JoinableCMTTransaction joinableCMTTransaction = (JoinableCMTTransaction) transaction;

					if ( joinableCMTTransaction.getStatus() == JoinableCMTTransaction.JoinStatus.JOINED ) {
						log.debug( "Transaction already joined" );
						return; //no-op
					}
					joinableCMTTransaction.markForJoined();
					session.isOpen(); //register to the Tx
					if ( joinableCMTTransaction.getStatus() == JoinableCMTTransaction.JoinStatus.NOT_JOINED ) {
						if ( ignoreNotJoining ) {
							log.debug( "No JTA transaction found" );
							return;
						}
						else {
							throw new TransactionRequiredException(
									"No active JTA transaction on joinTransaction call"
							);
						}
					}
					else
					if ( joinableCMTTransaction.getStatus() == JoinableCMTTransaction.JoinStatus.MARKED_FOR_JOINED ) {
						throw new AssertionFailure( "Transaction MARKED_FOR_JOINED after isOpen() call" );
					}
					//flush before completion and
					//register clear on rollback
					log.trace( "Adding flush() and close() synchronization" );
					joinableCMTTransaction.registerSynchronization(
							new Synchronization() {
								public void beforeCompletion() {
									boolean flush = false;
									TransactionFactory.Context ctx = null;
									try {
										ctx = (TransactionFactory.Context) session;
										JoinableCMTTransaction joinable = (JoinableCMTTransaction) session.getTransaction();
										javax.transaction.Transaction transaction = joinable.getTransaction();
										if ( transaction == null )
											log.warn( "Transaction not available on beforeCompletionPhase: assuming valid" );
										flush = !ctx.isFlushModeNever() &&
												//ctx.isFlushBeforeCompletionEnabled() &&
												//TODO probably make it ! isFlushBeforecompletion()
												( transaction == null || !JTAHelper.isRollback( transaction.getStatus() ) );
												//transaction == null workaround a JBoss TMBug
									}
									catch (SystemException se) {
										log.error( "could not determine transaction status", se );
										//throwPersistenceException will mark the transaction as rollbacked
										throwPersistenceException(
												new PersistenceException(
														"could not determine transaction status in beforeCompletion()",
														se
												)
										);
									}
									catch (HibernateException he) {
										throwPersistenceException( he );
									}

									try {
										if ( flush ) {
											log.trace( "automatically flushing session" );
											ctx.managedFlush();
										}
										else {
											log.trace( "skipping managed flushing" );
										}
									}
									catch (RuntimeException re) {
										//throwPersistenceException will mark the transaction as rollbacked
										if ( re instanceof HibernateException ) {
											throwPersistenceException( (HibernateException) re );
										}
										else {
											throwPersistenceException( new PersistenceException( re ) );
										}
									}
								}

								public void afterCompletion(int status) {
									try {
										if ( Status.STATUS_ROLLEDBACK == status
												&& transactionType == PersistenceUnitTransactionType.JTA ) {
											if ( session.isOpen() ) {
												session.clear();
											}
										}
										if ( session.isOpen() ) {
											//only reset if the session is opened since you can't get the Transaction otherwise
											JoinableCMTTransaction joinable = (JoinableCMTTransaction) session.getTransaction();
											joinable.resetStatus();
										}
									}
									catch (HibernateException e) {
										throwPersistenceException( e );
									}
								}
							}
					);
				}
				else {
					log.warn( "Cannot join transaction: do not override {}", Environment.TRANSACTION_STRATEGY );
				}
			}
			catch (HibernateException he) {
				throwPersistenceException( he );
			}
		}
		else {
			if ( !ignoreNotJoining ) log.warn( "Calling joinTransaction() on a non JTA EntityManager" );
		}
	}

	/**
	 * returns the underlying session
	 */
	public Object getDelegate() {
		return getSession();
	}

	;

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		tx = new TransactionImpl( this );
	}

	public void throwPersistenceException(PersistenceException e) {
		if ( ! ( e instanceof NoResultException || e instanceof NonUniqueResultException ) ) {
			try {
				markAsRollback();
			}
			catch (Exception ne) {
				//we do not want the subsequent exception to swallow the original one
				log.error( "Unable to mark for rollback on PersistenceException: ", ne);
			}
		}
		throw e;
	}

	public void throwPersistenceException(HibernateException e) {
		if ( e instanceof StaleStateException ) {
			PersistenceException pe = wrapStaleStateException( (StaleStateException) e );
			throwPersistenceException( pe );
		}
		else if ( e instanceof ObjectNotFoundException ) {
			throwPersistenceException( new EntityNotFoundException( e.getMessage() ) );
		}
		else if ( e instanceof org.hibernate.NonUniqueResultException ) {
			throwPersistenceException( new NonUniqueResultException( e.getMessage() ) );
		}
		else if ( e instanceof UnresolvableObjectException ) {
			throwPersistenceException( new EntityNotFoundException( e.getMessage() ) );
		}
		else if ( e instanceof QueryException ) {
			throw new IllegalArgumentException( e );
		}
		else if ( e instanceof TransientObjectException ) {
			try {
				markAsRollback();
			}
			catch (Exception ne) {
				//we do not want the subsequent exception to swallow the original one
				log.error( "Unable to mark for rollback on TransientObjectException: ", ne);
			}
			throw new IllegalStateException( e ); //Spec 3.2.3 Synchronization rules
		}
		else {
			throwPersistenceException( new PersistenceException( e ) );
		}
	}

	public PersistenceException wrapStaleStateException(StaleStateException e) {
		PersistenceException pe;
		if ( e instanceof StaleObjectStateException ) {
			StaleObjectStateException sose = (StaleObjectStateException) e;
			Serializable identifier = sose.getIdentifier();
			if (identifier != null) {
				Object entity = getRawSession().load( sose.getEntityName(), identifier );
				if ( entity instanceof Serializable ) {
					//avoid some user errors regarding boundary crossing
					pe = new OptimisticLockException( null, e, entity );
				}
				else {
					pe = new OptimisticLockException( e );
				}
			}
			else {
				pe = new OptimisticLockException( e );
			}
		}
		else {
			pe = new OptimisticLockException( e );
		}
		return pe;
	}
}

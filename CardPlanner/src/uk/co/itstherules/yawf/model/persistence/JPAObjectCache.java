package uk.co.itstherules.yawf.model.persistence;

import uk.co.itstherules.yawf.model.FileIdentityDeleteable;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.QueryConditions.Operator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.math.BigInteger;
import java.util.*;

public class JPAObjectCache implements ObjectCache  {
	
	private final EntityManager entityManager;
	private Set<EntityType<?>> entities;

	public JPAObjectCache(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
		this.entities = entityManagerFactory.getMetamodel().getEntities();
	}
	public BigInteger count(Class<?> type, Map<String, Object> map, ObjectState... states) throws ObjectCacheActionNotAuthorized {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		javax.persistence.criteria.CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<?> root = query.from(type);
		
		query.select(builder.count(root));
		Predicate[] andWhere = new Predicate[map.size()];
		int counter = 0;
		for (String key : map.keySet()) {
			andWhere[counter] = builder.equal(root.get(key), map.get(key));
		}
		if(states.length > 0) { query.where(buildStatesExpression(builder, root, states), builder.and(andWhere)); }
		else { query.where(builder.and(andWhere)); }
		Long count = this.entityManager.createQuery(query).getSingleResult();
		return new BigInteger(String.valueOf(count));
	}
	
	public BigInteger count(Class<?> type, ObjectState... states) throws ObjectCacheActionNotAuthorized {
		return count(type, new HashMap<String, Object>(), states);
	}
	
	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, ObjectState... states) throws ObjectCacheActionNotAuthorized {
		return all(type, new QueryConditions("AND"), new QueryConditions("OR"), states);
    }
	
    public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, String field, Object value, ObjectState... states) throws ObjectCacheActionNotAuthorized {
    	return all(type, new QueryConditions("AND").put(field, Operator.Equals, value), new QueryConditions("OR"), states);
	}

    public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, QueryConditions andConditions, ObjectState... states) throws ObjectCacheActionNotAuthorized {
    	return all(type, andConditions, new QueryConditions("OR"), states);
	}
	
    public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, QueryConditions andConditions, QueryConditions orConditions, ObjectState... states) throws ObjectCacheActionNotAuthorized {
    	QueryConditions statesConditions = new QueryConditions("OR");
    	if(states.length > 0) {
    		for (ObjectState objectState : states) {
    			statesConditions.put("objectState", Operator.Equals, objectState);
            }
    	} else {
			statesConditions.put("objectState", Operator.Equals, ObjectState.Active);
			statesConditions.put("objectState", Operator.Equals, ObjectState.Invisible);
    	}
    	return new JPQLQueryBuilder().select(this.entityManager, type, andConditions, orConditions, statesConditions);
	}
	
    public <T extends IdentityDeleteable<?>> Set<T> contains(Class<T> type, String field, Object value, ObjectState... states) throws ObjectCacheActionNotAuthorized {
    	return all(type, new QueryConditions("AND").put(field, Operator.MemberOf, value), states);
	}

    public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type, String field, Object value, ObjectState... states) throws ObjectCacheActionNotAuthorized {
    	return retrieve(type, new QueryConditions("AND").put(field, Operator.Equals, value), states);
	}

    public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type, QueryConditions andFieldValues, ObjectState... states) throws ObjectCacheActionNotAuthorized {
    	try {
    		return all(type, andFieldValues, states).iterator().next();
    	} catch (Exception e) {
    		return null;
    	}
	}
	
    public <T extends IdentityDeleteable<?>> T retrieveByIdentity(Class<T> type, String identity, ObjectState... states) throws ObjectCacheActionNotAuthorized {
		return retrieve(type, "identity", identity, states);
    }
	
	@SuppressWarnings("unchecked")
    public <T extends IdentityDeleteable<?>> T retrieveByIdentity(IdentityDeleteable<T> object, ObjectState... states) throws ObjectCacheActionNotAuthorized {
		return (T) retrieveByIdentity(object.getClass(), object.getIdentity(), states);
    }

    public <T extends IdentityDeleteable<?>> T first(Class<T> type, ObjectState... states) {
    	try {
    		return all(type, states).iterator().next();
    	} catch (IndexOutOfBoundsException e) {
    		return null;
    	}
	}
	
	public void saveExternal(FileIdentityDeleteable<?> object) throws ObjectCacheActionNotAuthorized {
		save(object);
		object.write();
	}

	public void save(IdentityDeleteable<?> object) throws ObjectCacheActionNotAuthorized {
		object.updateTimestamp();
		if(!this.entityManager.getTransaction().isActive()) {
			this.entityManager.getTransaction().begin();
		}
		this.entityManager.persist(object);
		this.entityManager.flush();
		this.entityManager.refresh(object);
	}

	public void delete(IdentityDeleteable<?> deleteable) throws ObjectCacheActionNotAuthorized {
		deleteable.delete();
		save(deleteable);
    }

	public void destroy(IdentityDeleteable<?> destroyable) throws ObjectCacheActionNotAuthorized {
		this.entityManager.remove(destroyable);
	}
	
	public void saveAll(Collection<IdentityDeleteable<?>> list) throws ObjectCacheActionNotAuthorized {
		for (IdentityDeleteable<?> object : list) {
	        save(object);
        }
    }
	
	private Predicate buildStatesExpression(CriteriaBuilder builder, Root<?> root, ObjectState... states) {
		if(states == null || states.length==0) {
			return builder.and(builder.or(builder.equal(root.get("objectState"), ObjectState.Active), builder.equal(root.get("objectState"), ObjectState.Invisible)));
		}
		Predicate[] ors = new Predicate[states.length];
		int count = 0;
		for (ObjectState objectState : states) {
	        ors[count] = builder.equal(root.get("objectState"), objectState);
            count++;
        }
	    return builder.and(builder.or(ors));
	}
	
	public void close() {
	    EntityTransaction transaction = this.entityManager.getTransaction();
		try {
            commit();
		} finally {
			if(transaction.isActive()) {
				transaction.rollback();
			}
			this.entityManager.close();
	    }
    }


    public void commit() {
        EntityTransaction transaction = this.entityManager.getTransaction();
   		try {
   			if(transaction.isActive()) {
   				transaction.commit();
   			}
   	    } catch (Exception e) {
            throw new RuntimeException(e.toString() + ": " + e.getCause());
        }
    }

	@Override
    public Set<String> metaModelClasses() {
		Set<String> classes = new LinkedHashSet<String>();
		for (EntityType<?> entity : this.entities) {
	        classes.add(entity.getJavaType().getName());
        }
	    return classes;
    }


}
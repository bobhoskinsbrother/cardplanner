package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.FileIdentityDeleteable;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.FieldValue;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheActionNotAuthorized;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;

import java.math.BigInteger;
import java.util.*;

public class FakeObjectCache<T extends IdentityDeleteable<?>> implements ObjectCache {

	private List<Object> objects;
    private Set<T> primed;

    public FakeObjectCache(Set<T> primed) {
        this.primed = primed;
        this.objects = new ArrayList<Object>(); }
	
	public List<Object> getObjects() {
	    return objects;
    }
	
    public <T extends IdentityDeleteable<?>> Set<T> contains(Class<T> type, String field, Object value, ObjectState... states) throws ObjectCacheActionNotAuthorized {
        return null;
    }
	public void addIndex(Class<? extends IdentityDeleteable<?>> toAddTo, boolean unique, String... fields) { }
	public boolean indexExists(Class<? extends IdentityDeleteable<?>> toAddTo, String... fields) { return false; }

	public boolean exists(Class<?> type, String field, String title, ObjectState... states) throws ObjectCacheActionNotAuthorized {
	    return false;
	}
	
	public Map<String, Map<String, Map<String, String>>> metaModelComplete() {
	    throw new RuntimeException("TODO: Not yet implemented");
	}
	
    public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, ObjectState... states) {
        return (Set<T>) primed;
    }
	
    
    public <T extends IdentityDeleteable<?>> List<T> allContains(Class<T> type, String field, Object object, ObjectState... states) {
        throw new RuntimeException("TODO: Not yet implemented");
    }
    
	public void setAll(Class<?> type, List<?> all) {
	    throw new RuntimeException("TODO: Not yet implemented");
	}
	
    public void destroy(Class<?> type, String field, String title) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }
	
    public void destroy(Object object) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

    public <T extends IdentityDeleteable<?>> T first(Class<T> type, ObjectState... states) {
    	return null;
    }
	
    public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type, String field, Object title, ObjectState... states) {
    	return null;
    }
	
    public void save(IdentityDeleteable<?> object) {
		objects.add(object);
    }

	public void externaliseIdentity(IdentityDeleteable<?> object) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public void externaliseIdentityAndSave(IdentityDeleteable<?> object) {
		objects.add(object);
    }
	
	public void saveExternal(FileIdentityDeleteable<?> object){
		objects.add(object);
	}

	public <T extends IdentityDeleteable<?>> T retrieveByIdentity(Class<T> type, IdentityDeleteable<?> object, ObjectState... states) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public void destroy(IdentityDeleteable<?> identity) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public <T extends IdentityDeleteable<?>> T firstExample(Class<T> type, Object object, ObjectState... states) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

    @SuppressWarnings("unchecked")
	public <T extends IdentityDeleteable<?>> T retrieveByIdentity(Class<T> type, String identity, ObjectState... states) {
    	if(type.isAssignableFrom(ConfigurationModel.class)) {
    		return (T) new ConfigurationModel();
    	}
    	return null;
    }

    public <T extends IdentityDeleteable<?>> T retrieveByIdentity(IdentityDeleteable<T> object, ObjectState... states) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public void delete(IdentityDeleteable<?> deleteable) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }
	
	public void saveAll(Collection<IdentityDeleteable<?>> list) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public <T extends IdentityDeleteable<?>> List<T> retrieveContains(Class<T> type, String field, Object title, ObjectState... states) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public BigInteger count(Class<?> type, ObjectState... states) {
		return new BigInteger("0");
    }

	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, String field, Object value, ObjectState... states) {
	    throw new RuntimeException("TODO: Not yet implemented");
	}
	
	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, QueryConditions fieldValues, ObjectState... states) {
        return (Set<T>) primed;
    }

	public Set<String> metaModelClasses() {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public BigInteger count(Class<?> type, Map<String, Object> map, ObjectState... states) {
		return new BigInteger("0");
    }


	public List<Map<String, String>> metaModelAll(String fullClassName) {
	    return null;
    }

	public Map<String, Map<String, String>> metaModelFor(String fullClassName) {
	    return null;
    }

	public String metaModelSerializeInstancesOf(String fullClassName) {
	    return null;
    }


	public <T extends IdentityDeleteable<?>> List<T> all(Class<T> type, Map<String, Object> andFieldValues,
			List<FieldValue> orFieldValues, ObjectState... states) {
	    return null;
    }

	public void close() {
    }

	public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type,
            Map<String, Object> andFieldValues, ObjectState... states)
            throws ObjectCacheActionNotAuthorized {
	    return null;
    }

	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type,
            QueryConditions andConditions, QueryConditions orConditions,
            ObjectState... states) throws ObjectCacheActionNotAuthorized {
	    return null;
    }

	public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type,
            QueryConditions andFieldValues, ObjectState... states)
            throws ObjectCacheActionNotAuthorized {
	    return null;
    }

}

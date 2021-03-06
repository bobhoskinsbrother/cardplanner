package uk.co.itstherules.yawf.model.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import uk.co.itstherules.yawf.model.FileIdentityDeleteable;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;

public interface ObjectCache {

	//meta queries
	Set<String> metaModelClasses();

	//Object Queries
	BigInteger count(Class<?> type, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	BigInteger count(Class<?> type, Map<String, Object> map, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> T retrieve(Class<T> type, String field, Object title, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> T retrieve(Class<T> type, QueryConditions andFieldValues, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> T retrieveByIdentity(Class<T> type, String identity, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> T retrieveByIdentity(IdentityDeleteable<T> object, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> T first(Class<T> type, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, String field, Object value, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, QueryConditions andConditions, ObjectState... states) throws ObjectCacheActionNotAuthorized;
	<T extends IdentityDeleteable<?>> Set<T> all(Class<T> type, QueryConditions andConditions, QueryConditions orConditions, ObjectState... states) throws ObjectCacheActionNotAuthorized;
    <T extends IdentityDeleteable<?>> Set<T> contains(Class<T> type, String field, Object value, ObjectState... states) throws ObjectCacheActionNotAuthorized;

    //Object State Change
    void save(IdentityDeleteable<?> object) throws ObjectCacheActionNotAuthorized;
	void saveExternal(FileIdentityDeleteable<?> object) throws ObjectCacheActionNotAuthorized;
	void delete(IdentityDeleteable<?> deleteable) throws ObjectCacheActionNotAuthorized;
	void destroy(IdentityDeleteable<?> destroyable) throws ObjectCacheActionNotAuthorized;
	void saveAll(Collection<IdentityDeleteable<?>> list) throws ObjectCacheActionNotAuthorized;
	
	//ObjectCache Actions
	void close() throws ObjectCacheActionNotAuthorized;
}
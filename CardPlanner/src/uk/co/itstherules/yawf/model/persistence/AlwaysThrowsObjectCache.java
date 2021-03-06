package uk.co.itstherules.yawf.model.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import uk.co.itstherules.yawf.model.FileIdentityDeleteable;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;

public class AlwaysThrowsObjectCache implements ObjectCache {

	private static final String NEVER_IMPLEMENTED = "Programmatic error; ObjectCache has been set to not required, and is being called";
	
	@Override
	public Set<String> metaModelClasses() {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public BigInteger count(Class<?> type, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public BigInteger count(Class<?> type, Map<String, Object> map,
			ObjectState... states) throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type,
			String field, Object title, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> T retrieve(Class<T> type,
			QueryConditions andFieldValues, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> T retrieveByIdentity(
			Class<T> type, String identity, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> T retrieveByIdentity(
			IdentityDeleteable<T> object, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> T first(Class<T> type,
			ObjectState... states) throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type,
			ObjectState... states) throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type,
			String field, Object value, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type,
			QueryConditions andConditions, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> Set<T> all(Class<T> type,
			QueryConditions andConditions, QueryConditions orConditions,
			ObjectState... states) throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public <T extends IdentityDeleteable<?>> Set<T> contains(Class<T> type,
			String field, Object value, ObjectState... states)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public void save(IdentityDeleteable<?> object)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public void saveExternal(FileIdentityDeleteable<?> object)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public void delete(IdentityDeleteable<?> deleteable)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public void destroy(IdentityDeleteable<?> destroyable)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public void saveAll(Collection<IdentityDeleteable<?>> list)
			throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

	@Override
	public void close() throws ObjectCacheActionNotAuthorized {
		throw new IllegalStateException(NEVER_IMPLEMENTED);
	}

}

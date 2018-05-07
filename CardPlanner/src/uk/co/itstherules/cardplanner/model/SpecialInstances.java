package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.Identity;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.instantiator.Instantiator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class SpecialInstances {

	private final ObjectCache objectCache;

	public SpecialInstances(final ObjectCache objectCache) { this.objectCache = objectCache; }
	
	public void persistIfNotExists(Identity identity) {
		if(SpecialInstances.retrieve(this.objectCache, identity)==null) { 
			IdentityDeleteable<?> built = SpecialInstances.buildFrom(this.objectCache, identity);
			this.objectCache.save(built); 
		}
	}
	
	private void resetOrPersist(Identity identity, ValuesProvider provider) {
		IdentityDeleteable<?> instance = SpecialInstances.retrieve(this.objectCache, identity);
		if(instance==null) { 
			instance = SpecialInstances.buildFrom(this.objectCache, identity);
		} else {
			instance = bind(objectCache, identity.getIdentity(), instance, provider);
		}
		this.objectCache.save(instance); 
	}

	@SuppressWarnings("unchecked")
    public static <T extends IdentityDeleteable<?>> T retrieve(ObjectCache objectCache, Identity identity) {
		return (T) objectCache.retrieveByIdentity(identity.toInstantiate(), identity.getIdentity()); 
	}

	private static IdentityDeleteable<?> buildFrom(ObjectCache objectCache, String identity, Class<? extends IdentityDeleteable<?>> theClass, ValuesProvider values) { 
		IdentityDeleteable<?> newInstance = new Instantiator().instantiate(theClass);
		return bind(objectCache, identity, newInstance, values);
	}

	private static IdentityDeleteable<?> bind(ObjectCache objectCache, String identity, IdentityDeleteable<?> instance, ValuesProvider provider) { 
		instance.setIdentity(identity);
		instance.defaultSetup(objectCache);
		QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, instance, objectCache);
		if(violations.isRegistered()) {
			StringBuilder builder = new StringBuilder(instance.getClass().getCanonicalName()).append(" has not been properly bound. The following errors occurred: ").append(violations.toString());
			throw new IllegalStateException(builder.toString());
		}
		instance.setIdentity(identity);
		return instance; 
	}
	
	private static IdentityDeleteable<?> buildFrom(ObjectCache objectCache, Identity identity) {
		ValuesProvider provider = identity.provider();
		return buildFrom(objectCache, identity.getIdentity(), identity.toInstantiate(), provider);
	}

}
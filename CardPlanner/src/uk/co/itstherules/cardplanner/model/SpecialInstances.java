package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.collections.PropertiesMap;
import uk.co.itstherules.yawf.PropertiesHandler;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.Identity;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.instantiator.Instantiator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.Properties;

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

	public void resetOrPersist(Identity identity) {
        resetOrPersist(identity, getProviderFor(identity));
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
		ValuesProvider provider = getProviderFor(identity);
		return buildFrom(objectCache, identity.getIdentity(), identity.toInstantiate(), provider);
	}

	private static ValuesProvider getProviderFor(Identity identity) {
		Properties properties = PropertiesHandler.provide("uk/co/itstherules/cardplanner/model/defaults", identity.getIdentity());
		PropertiesMap map = new PropertiesMap(properties);
		return new MapValuesProvider(map);
	}

}
package uk.co.itstherules.yawf.inbound.binders.objectproviders;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class PassthroughImplementationProvider<T> implements ImplementationProvider<T> {

	private final Class<T> theClass;

	public PassthroughImplementationProvider(Class<T> theClass) {
		this.theClass = theClass;
    }
	
	@Override public T provide(ObjectCache objectCache, ValuesProvider provider) { 
		try {
			T instance = theClass.newInstance();
	        if(theClass.isAssignableFrom(IdentityDeleteable.class)) {
	        	IdentityDeleteable.class.cast(instance).defaultSetup(objectCache);
	        }
			return instance;
        } catch (InstantiationException | IllegalAccessException e) {
	        throw new RuntimeException(e);
        }
    }

	@Override
    public boolean canHandle(Class<?> theClass, ObjectCache objectCache, ValuesProvider provider) {
	    return true;
    }
	
}

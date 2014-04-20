package uk.co.itstherules.yawf.inbound.binders.objectproviders;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class AliasImplementationProvider<T> implements ImplementationProvider<T> {

	private final Class<T> theOriginalClass;
	private final Class<? extends T> theSubstituteClass;
	private final InstanceDecisionMaker instanceDecisionMaker;

	public AliasImplementationProvider(Class<T> theInterface, Class<? extends T> theClass) {
		this(theInterface, theClass, new AlwaysTrueDecisionMaker());
	}
	
	public AliasImplementationProvider(Class<T> theOriginalClass, Class<? extends T> theSubstituteClass, InstanceDecisionMaker instanceDecisionMaker) {
		this.theOriginalClass = theOriginalClass;
		this.theSubstituteClass = theSubstituteClass;
		this.instanceDecisionMaker = instanceDecisionMaker;
    }
	
	@Override public T provide(ObjectCache objectCache, ValuesProvider provider) { 
		try {
			T instance = theSubstituteClass.newInstance();
	        if(theSubstituteClass.isAssignableFrom(IdentityDeleteable.class)) {
	        	IdentityDeleteable.class.cast(instance).defaultSetup(objectCache);
	        }
			return instance;
        } catch (InstantiationException e) {
	        throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
        }
	}

	@Override
    public boolean canHandle(Class<?> theClass, ObjectCache objectCache, ValuesProvider provider) {
	    return theOriginalClass.equals(theClass) && instanceDecisionMaker.shouldInstantiate(objectCache, provider);
    }
	
}

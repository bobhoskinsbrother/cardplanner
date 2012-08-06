package uk.co.itstherules.yawf.inbound.binders.objectproviders;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public interface ImplementationProvider<T> {
	
	boolean canHandle(Class<?> theClass, ObjectCache objectCache, ValuesProvider provider);
	T provide(ObjectCache objectCache, ValuesProvider provider);

}

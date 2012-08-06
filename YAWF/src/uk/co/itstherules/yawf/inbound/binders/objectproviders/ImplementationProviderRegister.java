package uk.co.itstherules.yawf.inbound.binders.objectproviders;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class ImplementationProviderRegister {
	
	private List<ImplementationProvider<?>> providers;

    @SuppressWarnings("rawtypes")
	public ImplementationProviderRegister() {
		this.providers = new ArrayList<ImplementationProvider<?>>();
		register(new AliasImplementationProvider<List>(List.class, LinkedList.class));
		register(new AliasImplementationProvider<Set>(Set.class, LinkedHashSet.class));
    }
	
	public void register(ImplementationProvider<?> provider) {
		this.providers.add(0, provider);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object provide(Class<?> theClass, ObjectCache objectCache, ValuesProvider valuesProvider) {
		for (ImplementationProvider<?> provider : providers) {
	        if(provider.canHandle(theClass, objectCache, valuesProvider)) {
	        	return provider.provide(objectCache, valuesProvider);
	        }
        }
		return new PassthroughImplementationProvider(theClass).provide(objectCache, valuesProvider);
    }

}

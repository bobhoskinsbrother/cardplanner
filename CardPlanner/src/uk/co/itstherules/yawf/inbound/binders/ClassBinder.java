package uk.co.itstherules.yawf.inbound.binders;


import net.sf.oval.Validator;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ClassBinder extends BaseQueryValueBinder {
	

	public ClassBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		QueryKey annotation = field.getAnnotation(QueryKey.class);
		CacheInstruction cache;
		if(annotation==null) {
			cache = CacheInstruction.NotFromCache;
		} else {
			cache = annotation.cache();
		}
		ValuesProvider currentProvider = provider.limitContext(currentQueryKey);
		
		Object instance = null;
		if(cache != CacheInstruction.FromCache) {
			try {
				instance = field.get(model);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		if(instance == null) {
			Class<?> classToInstantiate = field.getType();
			instance = retrieveFromCacheOrCreateImplementation(objectCache, violations, cache, currentProvider, classToInstantiate);
		}
		

		if(!currentProvider.getKeys().isEmpty()) { 
			violations.mergeWith(binder.bind(currentProvider, instance, objectCache)); 
		}
		setValue(field, model, instance, violations, fullQueryKey);
		violations.add(fullQueryKey, new Validator().validateFieldValue(model, field, instance));
    }
	
	public boolean canHandle(Class<?> classType, Type genericType) {
		// fallthrough
		return true;
    }

}

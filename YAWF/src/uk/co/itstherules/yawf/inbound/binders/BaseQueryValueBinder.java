package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.sf.oval.Validator;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public abstract class BaseQueryValueBinder implements QueryValueBinder {
	
	protected final BaseValuesProviderBinder binder;
	private final ImplementationProviderRegister implementationProviderRegister;
	
	public BaseQueryValueBinder(final BaseValuesProviderBinder binder, final ImplementationProviderRegister implementationProviderRegister) {
		this.binder = binder;
		this.implementationProviderRegister = implementationProviderRegister;
    }

	public void setValue(Field field, Object model, Object value, QueryKeyViolations violations, String fullQueryKey) {
		try {
			field.set(model, value);
			violations.add(fullQueryKey, new Validator().validateFieldValue(model, field, value));
        } catch (IllegalArgumentException e) {
	        throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
        }
	}

	public abstract boolean canHandle(Class<?> fieldClass, Type genericType);
		
    protected Object retrieveFromCacheOrCreateImplementation(ObjectCache objectCache, QueryKeyViolations violations, CacheInstruction cache, ValuesProvider currentProvider, Class<?> classToInstantiate) {
	    Object instance = null;
		if(cache==CacheInstruction.FromCache) {
			String identity = currentProvider.getIdentity();
			if(!"0".equals(identity) && IdentityDeleteable.class.isAssignableFrom(classToInstantiate)) {
				instance = objectCache.retrieveByIdentity(classToInstantiate.asSubclass(IdentityDeleteable.class), identity);
			}
		} 
		if(instance == null) { 
			instance = this.implementationProviderRegister.provide(classToInstantiate, objectCache, currentProvider);
		}
	    return instance;
    }

	
}

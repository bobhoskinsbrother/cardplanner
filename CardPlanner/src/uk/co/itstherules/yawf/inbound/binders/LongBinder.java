package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class LongBinder extends BaseQueryValueBinder {

	public LongBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
    	setValue(field, model, provider.getLong(fullQueryKey, 0), violations, fullQueryKey);
    }

	public boolean canHandle(Class<?> classType, Type genericType) {
		return (classType.equals(Long.class) || classType.equals(Long.TYPE));

    }
}

package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class StringBinder extends BaseQueryValueBinder {

	public StringBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
    	setValue(field, model, provider.getString(fullQueryKey, null), violations, fullQueryKey);
    }

	public boolean canHandle(Class<?> classType, Type genericType) {
		return (classType.equals(String.class));
    }
}

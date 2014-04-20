package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class SetBinder extends CollectionBinder {

	public SetBinder(BinderRegister converterRegister, BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	 	super(converterRegister, binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		Collection<Object> bound = bindInternal(model, field, fullQueryKey, currentQueryKey, provider, objectCache, violations);
		setValue(field, model, new LinkedHashSet<Object>(bound), violations, fullQueryKey);
	}

	@Override
    protected Class<?> getConverterType() {
	    return Set.class;
    }
}
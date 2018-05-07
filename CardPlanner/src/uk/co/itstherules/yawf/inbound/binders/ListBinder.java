package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class ListBinder extends CollectionBinder {

	public ListBinder(BinderRegister converterRegister, BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	 	super(converterRegister, binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		List<?> value = new ArrayList<Object>(bindInternal(model, field, fullQueryKey, currentQueryKey, provider, objectCache, violations));
		setValue(field, model, value, violations, fullQueryKey);
	}

	@Override
    protected Class<?> getConverterType() {
	    return List.class;
    }

}
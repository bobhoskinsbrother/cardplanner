package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class ListOfStringBinder extends BaseQueryValueBinder {

	public ListOfStringBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		setValue(field, model, provider.getList(fullQueryKey), violations, fullQueryKey);
	}

	public boolean canHandle(Class<?> classType, Type genericType) {
		if (genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedGenericType = (ParameterizedType) genericType;
			Type[] parameterGenericTypes = parameterizedGenericType.getActualTypeArguments();
			if (parameterizedGenericType.getRawType().equals(List.class)
			        && parameterGenericTypes.length == 1
			        && parameterGenericTypes[0].equals(String.class)) {
				return true;
			}
		}
		return false;
	}
}
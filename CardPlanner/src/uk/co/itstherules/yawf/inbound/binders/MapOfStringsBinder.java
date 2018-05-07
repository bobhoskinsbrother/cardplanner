package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class MapOfStringsBinder extends BaseQueryValueBinder {
	
	public MapOfStringsBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
    	setValue(field, model, provider.getMap(fullQueryKey, new LinkedHashMap<String, String>()), violations, fullQueryKey);
	}

	public boolean canHandle(Class<?> classType, Type genericType) {
		if (genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			Type[] parameterGenericTypes = parameterizedType.getActualTypeArguments();
			if (parameterizedType.getRawType().equals(Map.class)
			        && parameterGenericTypes.length == 2
			        && parameterGenericTypes[0].equals(String.class)
			        && parameterGenericTypes[1].equals(String.class)) {
				return true;
			}
		}
		return false;
	}
}
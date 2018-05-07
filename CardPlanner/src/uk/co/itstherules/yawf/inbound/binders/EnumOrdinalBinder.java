package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public class EnumOrdinalBinder extends BaseQueryValueBinder {
	
	public EnumOrdinalBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		Class<? extends Enum> fieldType = (Class<? extends Enum>) field.getType();
        Enum[] constants = fieldType.getEnumConstants();
    	if (constants == null) throw new IllegalArgumentException(fieldType.getName() + " is not an enum type");
    	Integer value = provider.getInteger(fullQueryKey, new Integer(-1));
    	
    	try {
    		setValue(field, model, constants[value.intValue()], violations, fullQueryKey);
    	} catch (IndexOutOfBoundsException e) {
    		setValue(field, model, null, violations, fullQueryKey);
		}
    }

	public boolean canHandle(Class<?> classType, Type genericType) {
		return classType.isEnum();
    }
}
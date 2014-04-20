package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public class EnumBinder extends BaseQueryValueBinder {
	
	
	public EnumBinder(BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		Class<? extends Enum> fieldType = (Class<? extends Enum>) field.getType();
        Enum[] constants = fieldType.getEnumConstants();
    	if (constants == null) throw new IllegalArgumentException(fieldType.getName() + " is not an enum type");
    	boolean set = false;
        for (Enum constant : constants) {
	        String value = provider.getString(fullQueryKey, "");
	        if(value.equals(((Enum)constant).name())){
	        	setValue(field, model, constant, violations, fullQueryKey);
	        	set = true;
	        	break;
	        }
        }
        if(!set) {
    		setValue(field, model, null, violations, fullQueryKey);
        }
    }

	public boolean canHandle(Class<?> classType, Type genericType) {
		return classType.isEnum();
    }
}
package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.lang.reflect.Field;
import java.util.Map;

import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;

public class BasicValuesProviderBinder extends BaseValuesProviderBinder {
	
	public BasicValuesProviderBinder() {
	    super(new ImplementationProviderRegister());
    }

	public BasicValuesProviderBinder(ImplementationProviderRegister register) {
	    super(register);
    }
	
	@Override protected Map<String, Field> getFields(Object model)  {
	    return ClassFieldDescriptions.getFieldsFor(model.getClass());
    }
    
}

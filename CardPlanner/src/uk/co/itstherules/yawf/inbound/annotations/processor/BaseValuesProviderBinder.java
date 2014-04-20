package uk.co.itstherules.yawf.inbound.annotations.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.UseBinder;
import uk.co.itstherules.yawf.inbound.binders.BinderRegister;
import uk.co.itstherules.yawf.inbound.binders.NoBinderFoundException;
import uk.co.itstherules.yawf.inbound.binders.QueryValueBinder;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.comparator.StringLengthComparator;
import uk.co.itstherules.yawf.model.instantiator.Instantiator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public abstract class BaseValuesProviderBinder {

	private final BinderRegister binderRegister;
	protected final ImplementationProviderRegister implementationProviderRegister;

	public BaseValuesProviderBinder(ImplementationProviderRegister implementationProviderRegister) {
		this.implementationProviderRegister = implementationProviderRegister;
		this.binderRegister = new BinderRegister(this, implementationProviderRegister);
    }
	
	public void registerBinder(QueryValueBinder converter) {
		this.binderRegister.register(converter);
    }
	
	public QueryKeyViolations bind(ValuesProvider provider, Object model, ObjectCache objectCache) {
		QueryKeyViolations violations = new QueryKeyViolations();
		populate(violations, provider, model, objectCache);
		return violations;
	}
	
    public void populate(QueryKeyViolations violations, ValuesProvider provider, Object model, ObjectCache objectCache) {
    	List<String> providerKeys = new ArrayList<String>(provider.getKeys());
    	Collections.sort(providerKeys, new StringLengthComparator());
    	List<ObjectAndField> objectAndFields = new ArrayList<ObjectAndField>();
    	for (String fullDotSeparatedProviderKey : providerKeys) {
    		List<String> splitKey = Arrays.asList(fullDotSeparatedProviderKey.split("\\."));
	        for(int i=0; i < splitKey.size(); i++) {
	        	String currentQueryKey = splitKey.get(i);
				Map<String, Field> fields = getFields(model);
				Field field = fields.get(currentQueryKey);
				if(field == null) continue; // there is a key from the values provider, but there is not anything to populate it on in the model
	           	field.setAccessible(true);
	           	ObjectAndField objectAndField = new ObjectAndField(model, field, fullDotSeparatedProviderKey);
	           	if(!objectAndFields.contains(objectAndField)) {
	           		bindToField(provider, model, field, fullDotSeparatedProviderKey, flattenToCurrent(splitKey, i), objectCache, violations);
	           		objectAndFields.add(objectAndField);
	           		break;
	            }
	        }
        }
	}

	protected abstract Map<String, Field> getFields(Object model);

    private String flattenToCurrent(List<String> splitKey, int i) {
    	StringBuffer buffer = new StringBuffer();
    	for (int j = 0; j <= i; j++) {
	        buffer.append(".");
	        buffer.append(splitKey.get(j));
        }
    	if(buffer.length()>0) {
    		return buffer.substring(1);
    	}
    	return "";
    }

    private void bindToField(ValuesProvider provider, Object model, Field field, String fullQueryKey, String currentQueryKey, ObjectCache objectCache, QueryKeyViolations violations) {
		try {
			Class<?> fieldType = field.getType();
			Type genericType = field.getGenericType();
			QueryValueBinder binder = null;
			if(field.isAnnotationPresent(UseBinder.class)) {
				 binder = new Instantiator().instantiate(field.getAnnotation(UseBinder.class).value());
			} else {
				binder = binderRegister.retrieve(fieldType, genericType);
			}
			binder.bind(model, field, fullQueryKey, currentQueryKey, provider, objectCache, violations);
        } catch (NoBinderFoundException e) {
	        throw new RuntimeException(e);
        }
    }
}

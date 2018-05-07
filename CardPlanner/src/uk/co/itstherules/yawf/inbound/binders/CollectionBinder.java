package uk.co.itstherules.yawf.inbound.binders;

import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.comparator.StringLengthComparator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class CollectionBinder extends BaseQueryValueBinder {

	protected final BinderRegister converterRegister;

	protected abstract Class<?> getConverterType();
	
	public CollectionBinder(BinderRegister converterRegister, BaseValuesProviderBinder binder, ImplementationProviderRegister register) {
	    super(binder, register);
		this.converterRegister = converterRegister;
    }

	public abstract void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations);
	
	
	private Object findInCurrentCollection(String identity, Collection<IdentityDeleteable<?>> collectionAlreadySetOnModel) {
		for (IdentityDeleteable<?> identityDeleteable : collectionAlreadySetOnModel) {
	        if(identity.equals(identityDeleteable.getIdentity())) {
	        	return identityDeleteable;
	        }
        }
	    return null;
    }

	@SuppressWarnings("unchecked")
	public Collection<Object> bindInternal(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		QueryKey queryKeyAnnotation = field.getAnnotation(QueryKey.class);
		CacheInstruction cache;
		if(queryKeyAnnotation==null) {
			cache = CacheInstruction.FromCache;
		} else {
			cache = queryKeyAnnotation.cache();
		}
		Type genericType = field.getGenericType();
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type[] parameterGenericTypes = parameterizedType.getActualTypeArguments();
		Class<?> classToInstantiate = (Class<?>) parameterGenericTypes[0];
		Collection<IdentityDeleteable<?>> collectionAlreadySetOnModel;
		try {
	        collectionAlreadySetOnModel = (Collection<IdentityDeleteable<?>>) field.get(model);
        } catch (IllegalArgumentException e1) {
	        throw new RuntimeException(e1);
        } catch (IllegalAccessException e1) {
	        throw new RuntimeException(e1);
        }
		List<Object> reply = new ArrayList<Object>();
		Map<String, Map<String, Object>> relevant = filterProviderForCurrentContext(currentQueryKey, provider);
		Set<String> keySet = relevant.keySet();

		List<String> toSort = new ArrayList<String>(keySet);
    	Collections.sort(toSort, new StringLengthComparator());
    	keySet = new LinkedHashSet<String>(toSort);
		
		for (String key : keySet) {
			MapValuesProvider currentProvider = new MapValuesProvider(relevant.get(key));
			String identity = currentProvider.getIdentity();
			Object instance = null;
			if(collectionAlreadySetOnModel != null && identity != null) { 
				instance = findInCurrentCollection(identity, collectionAlreadySetOnModel); 
			}
			if(!currentProvider.getKeys().isEmpty()) {
				if(instance == null) {
					instance = retrieveFromCacheOrCreateImplementation(objectCache, violations, cache, currentProvider, classToInstantiate);
				}
				violations.mergeWith(binder.bind(currentProvider, instance, objectCache)); 
				reply.add(instance);
			}
		}
		return reply;
	}
	
	
	public boolean canHandle(Class<?> classType, Type genericType) {
		if (genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			Type[] parameterGenericTypes = parameterizedType.getActualTypeArguments();
			if (getConverterType().isInterface() && getConverterType().isAssignableFrom(classType) && parameterGenericTypes.length == 1) {
				try {
					converterRegister.retrieve((Class<?>) parameterGenericTypes[0], null);
					return true;
				} catch (NoBinderFoundException e) {
					return false;
				}
			}
		}
		return false;
	}
	

	public Map<String, Map<String, Object>> filterProviderForCurrentContext(String currentQueryKey, ValuesProvider provider) {
    	List<String> keys = new ArrayList<String>(provider.getKeys());
    	Collections.sort(keys);
		Map<String, Map<String, Object>> reply = new LinkedHashMap<String, Map<String,Object>>();
		for (String key : keys) {
	        if(key.startsWith(currentQueryKey) && key.length() > currentQueryKey.length()) {
	        	String relevantKey = key.substring(currentQueryKey.length()+1);
	        	String number = null;
        		if(relevantKey.indexOf(".") > -1) {
        			number = relevantKey.substring(0, relevantKey.indexOf("."));
	        	} else {
        			number = relevantKey;
	        	}
	        	Map<String, Object> subset = reply.get(number);
				if(subset==null) {
	        		subset = new LinkedHashMap<String, Object>();
	        	}
				subset.put(relevantKey.substring(number.length()+1), provider.getString(key));
				reply.put(number, subset);
	        }
		}
		return reply;
    }
}
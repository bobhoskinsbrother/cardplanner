package uk.co.itstherules.yawf.inbound.annotations.processor;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.QueryKeyRoot;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ClassFieldDescriptions {

	private static final ConcurrentMap<Class<?>, FutureTask<Map<String, Field>>> CACHE;

	static {
		CACHE = new ConcurrentHashMap<Class<?>, FutureTask<Map<String, Field>>>();
	}
	
	public static Map<String, Field> getFieldsFor(final Class<?> classToFindFieldsFor) {
		FutureTask<Map<String, Field>> futureTask = CACHE.get(classToFindFieldsFor);
		if (futureTask == null) {
			Callable<Map<String, Field>> callable = new Callable<Map<String, Field>>() {
				@Override public Map<String, Field> call() throws Exception {
					return collectFieldsFor(classToFindFieldsFor);
				}
			};
			FutureTask<Map<String, Field>> newFutureTask = new FutureTask<Map<String, Field>>(callable);
			futureTask = CACHE.putIfAbsent(classToFindFieldsFor, newFutureTask);
			if (futureTask == null) {
				futureTask = newFutureTask;
				newFutureTask.run();
			}
		}
		try {
			return futureTask.get();
		} catch (CancellationException e) {
			CACHE.remove(classToFindFieldsFor, futureTask);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		throw new RuntimeException();
	}
	
	private static Map<String, Field> collectFieldsFor(Class<?> modelClass){
		Map<String, Field> map = new HashMap<String, Field>();
		collectFieldsFor(modelClass, map);
		return map;
	}
	
	private static void collectFieldsFor(Class<?> modelClass, Map<String, Field> map) {
		Field[] declaredFields = modelClass.getDeclaredFields();
		for (Field field : declaredFields) { 
			if(field.isAnnotationPresent(QueryKey.class)) { 
				map.put(field.getAnnotation(QueryKey.class).value(), field); 
			} 
		}
		if(modelClass.isAnnotationPresent(QueryKeyRoot.class)) { 
			return;
		} else {
			Class<?> superclass = modelClass.getSuperclass();
			if(superclass==Object.class) return;
			collectFieldsFor(superclass, map);
		}
	}
	
}

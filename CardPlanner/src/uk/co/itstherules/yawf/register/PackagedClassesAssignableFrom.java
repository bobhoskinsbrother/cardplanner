package uk.co.itstherules.yawf.register;

import uk.co.itstherules.reflection.ClassFinder;

import java.util.*;

public final class PackagedClassesAssignableFrom<T extends Keyed> {
	
	private InternalRegister<T> register;

	public PackagedClassesAssignableFrom(T defaultInstance) {
		this.register = new InternalRegister<T>(defaultInstance);
    }
	
	public Register<T> collect(Class<T> interfaceType, List<String> packages) {
		try {
			for (String viewPackage : packages) {
				List<Class<?>> classes = new ClassFinder().collectClassesFromAllPackagesNamed(viewPackage);
				for (Class<?> classToInstantiate : classes) {
					if(interfaceType.isAssignableFrom(classToInstantiate)) {
						T instance = interfaceType.cast(classToInstantiate.newInstance());
						register.register(instance);
					}
				}
            }
		} catch (Exception e) {
			throw new IllegalArgumentException("Every reflected object needs to implement / extend the provided class and have an empty constructor");
		}
		register.seal();
		return register;
	}
	
	
	static final class InternalRegister<T extends Keyed> implements Register<T> {
		
		private Map<String, T> registered = new HashMap<String, T>();
		private final T defaultInstance;
		
		public InternalRegister(T defaultInstance) { this.defaultInstance = defaultInstance; }

		void register(T keyable) {
			registered.put(keyable.getKey(), keyable);
		}
		
		public boolean exists(String key) {
			return registered.containsKey(key);
	    }
		
		private void seal() { registered = Collections.unmodifiableMap(registered); }
		
		public T get(String key) throws NotRegistered { 
			T reply = registered.get(key);
			if(reply == null) {
				if(defaultInstance != null) { return defaultInstance; }
				else { throw new NotRegistered(); }
			}
			return reply; 
		}
		
		public Set<String> available() { return Collections.unmodifiableSet(this.registered.keySet()); }
		
	}

}
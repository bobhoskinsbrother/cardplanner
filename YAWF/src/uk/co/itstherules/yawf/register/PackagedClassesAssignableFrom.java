package uk.co.itstherules.yawf.register;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.itstherules.reflection.ClassFinder;

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
			this.registered.put(keyable.getKey(), keyable);
		}
		
		public boolean exists(String key) {
			return registered.containsKey(key);
	    }
		
		private void seal() { this.registered = Collections.unmodifiableMap(this.registered); }
		
		public T get(String key) throws NotRegistered { 
			T reply = this.registered.get(key);
			if(reply == null) {
				if(this.defaultInstance != null) { return this.defaultInstance; }
				else { throw new NotRegistered(); }
			}
			return reply; 
		}
		
		public Set<String> available() { return Collections.unmodifiableSet(this.registered.keySet()); }
		
	}

}
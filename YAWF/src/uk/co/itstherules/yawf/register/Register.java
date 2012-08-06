package uk.co.itstherules.yawf.register;

import java.util.Set;

public interface Register<T extends Keyed> {

	public boolean exists(String key);
	public T get(String key) throws NotRegistered;
	public Set<String> available();
	
}

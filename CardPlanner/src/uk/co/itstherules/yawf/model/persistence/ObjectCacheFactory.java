package uk.co.itstherules.yawf.model.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import uk.co.itstherules.yawf.model.EntityManagerListener;


public final class ObjectCacheFactory {

	private ObjectCacheFactory(){}
	
	public static ObjectCache get() {
		EntityManagerFactory factory = EntityManagerListener.getEntityManagerFactory();
		return new JPAObjectCache(factory);
	}

	public static ObjectCache get(String applicationName) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(applicationName);
		return new JPAObjectCache(factory);
	}

	public static ObjectCache create(String applicationName, String url, String userName, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("javax.persistence.jdbc.url", url);
		map.put("javax.persistence.jdbc.user", userName);
		map.put("javax.persistence.jdbc.password", password);
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(applicationName, map);
		return new JPAObjectCache(factory); 
	}
	
}
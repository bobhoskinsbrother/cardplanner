package uk.co.itstherules.yawf.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uk.co.itstherules.yawf.inbound.ContextValuesProvider;
import uk.co.itstherules.yawf.inbound.DefaultValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;

public final class EntityManagerListener implements ServletContextListener {
	
	private static EntityManagerFactory ENTITY_MANAGER_FACTORY;
	private static String APPLICATION_NAME;

	@Override public void contextInitialized(ServletContextEvent contextEvent) {
		ValuesProvider provider = new ContextValuesProvider(contextEvent.getServletContext(), new DefaultValuesProvider());
		APPLICATION_NAME = provider.getString("applicationName");
    }

	@Override public void contextDestroyed(ServletContextEvent arg0) { 
		if(ENTITY_MANAGER_FACTORY!=null) {
			ENTITY_MANAGER_FACTORY.close();
			ENTITY_MANAGER_FACTORY = null;
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		if(ENTITY_MANAGER_FACTORY==null) {
			try {
				ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(APPLICATION_NAME);
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		return ENTITY_MANAGER_FACTORY;
	}	
}
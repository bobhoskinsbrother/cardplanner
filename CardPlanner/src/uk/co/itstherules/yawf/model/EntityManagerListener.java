package uk.co.itstherules.yawf.model;

import uk.co.itstherules.yawf.inbound.ContextValuesProvider;
import uk.co.itstherules.yawf.inbound.DefaultValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

public final class EntityManagerListener implements ServletContextListener {
	
	private static EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private final Map<String, String> map;

    public EntityManagerListener(Map<String, String> map) {
        this.map = map;
    }

    @Override public void contextInitialized(ServletContextEvent contextEvent) {
		ValuesProvider provider = new ContextValuesProvider(contextEvent.getServletContext(), new DefaultValuesProvider());
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(provider.getString("applicationName"), map);
    }

	@Override public void contextDestroyed(ServletContextEvent arg0) {
		if(ENTITY_MANAGER_FACTORY!=null) {
			ENTITY_MANAGER_FACTORY.close();
			ENTITY_MANAGER_FACTORY = null;
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return ENTITY_MANAGER_FACTORY;
	}	
}
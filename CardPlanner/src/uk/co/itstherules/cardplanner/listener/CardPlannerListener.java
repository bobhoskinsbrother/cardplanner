package uk.co.itstherules.cardplanner.listener;

import net.sf.oval.Validator;
import net.sf.oval.localization.message.ResourceBundleMessageResolver;
import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.model.CachedInstance;
import uk.co.itstherules.yawf.ApplicationInfo;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.ResourceBundle;

public class CardPlannerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        setupDefaultModels();
        setupDefaultFolders(context);
        setupDefaultBundles();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void setupDefaultBundles() {
        final ResourceBundle bundle = ResourceBundle.getBundle("uk/co/itstherules/cardplanner/listener/errormessages");
        ResourceBundleMessageResolver messageResolver = ResourceBundleMessageResolver.class.cast(Validator.getMessageResolver());
        messageResolver.addMessageBundle(bundle);
    }

    private void setupDefaultFolders(ServletContext servletContext) {
        File uploadDirectory = new File(ApplicationInfo.getUploadPath(servletContext.getInitParameter(ProviderKey.applicationName.name())));
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    }

    private void setupDefaultModels() {
        ObjectCache cache = ObjectCacheFactory.get();
        try {
            new CachedInstance().initialise(cache);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            cache.close();
        }
    }
}

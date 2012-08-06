package uk.co.itstherules.cardplanner.initialization;

import java.io.File;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import net.sf.oval.Validator;
import net.sf.oval.localization.message.ResourceBundleMessageResolver;
import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.model.CachedInstance;
import uk.co.itstherules.yawf.ApplicationInfo;
import uk.co.itstherules.yawf.initialization.Initializer;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheFactory;

public class CardPlannerInitializer implements Initializer {
	
	public void initialize(ServletConfig config) {
        ServletContext context = config.getServletContext();
		setupDefaultModels();
		setupDefaultFolders(context);
		setupDefaultBundles();
	}

	private void setupDefaultBundles() {
		ResourceBundleMessageResolver.class.cast(Validator.getMessageResolver()).
		addMessageBundle(ResourceBundle.getBundle("/uk/co/itstherules/yawf/internationalised/errormessages"));
    }

	private void setupDefaultFolders(ServletContext servletContext) {
		File uploadDirectory = new File(ApplicationInfo.getUploadPath(servletContext.getInitParameter(ProviderKey.applicationName.name())));
		if(!uploadDirectory.exists()) { uploadDirectory.mkdirs(); }
    }

	private void setupDefaultModels() {
		ObjectCache cache = ObjectCacheFactory.get();
		try {
			new CachedInstance().initialise(cache);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			cache.close();
			cache = null;
		}
    }	
}

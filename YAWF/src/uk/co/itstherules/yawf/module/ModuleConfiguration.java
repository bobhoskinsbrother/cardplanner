package uk.co.itstherules.yawf.module;

import uk.co.itstherules.yawf.initialization.Initializer;
import uk.co.itstherules.yawf.modelview.ModelView;

public interface ModuleConfiguration {

	ModelView getModuleNavigationView();
	Initializer getInitializer();

}
package uk.co.itstherules.cardplanner.initialization;

import uk.co.itstherules.yawf.initialization.Initializer;
import uk.co.itstherules.yawf.modelview.EmptyModelView;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.module.ModuleConfiguration;

public class CardPlannerModuleConfiguration implements ModuleConfiguration {
	
	
	@Override public ModelView getModuleNavigationView() {
		return new EmptyModelView();
	}
	
	@Override public Initializer getInitializer() {
		return new CardPlannerInitializer();
	}
	
}

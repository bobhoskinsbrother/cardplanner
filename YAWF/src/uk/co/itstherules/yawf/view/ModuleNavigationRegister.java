package uk.co.itstherules.yawf.view;

import uk.co.itstherules.yawf.modelview.EmptyModelView;
import uk.co.itstherules.yawf.modelview.ModelView;

public final class ModuleNavigationRegister {

	private static ModelView view;
	
	public static synchronized void create(ModelView view) {
		if(ModuleNavigationRegister.view == null) {
			ModuleNavigationRegister.view = view;
		}
    }
	
	public static ModelView getView() { 
		if(ModuleNavigationRegister.view != null) { return ModuleNavigationRegister.view; }
		return new EmptyModelView();
	}
}

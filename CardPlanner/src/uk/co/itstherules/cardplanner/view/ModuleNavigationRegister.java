package uk.co.itstherules.cardplanner.view;

import uk.co.itstherules.cardplanner.view.active.Empty;
import uk.co.itstherules.yawf.modelview.ModelView;

public class ModuleNavigationRegister {

	private static ModelView view;
	
	public static synchronized void setModuleSideNavigation(ModelView view) {
		ModuleNavigationRegister.view = view;
    }
	
	public static ModelView getView() { 
		if(view != null) { return view; }
		return new Empty();
	}
}

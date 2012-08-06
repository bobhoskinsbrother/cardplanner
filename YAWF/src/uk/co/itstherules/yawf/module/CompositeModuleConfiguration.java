package uk.co.itstherules.yawf.module;

import java.util.ArrayList;
import java.util.List;

import uk.co.itstherules.yawf.initialization.CompositeInitializer;
import uk.co.itstherules.yawf.initialization.Initializer;
import uk.co.itstherules.yawf.modelview.CompositeView;
import uk.co.itstherules.yawf.modelview.ModelView;

public final class CompositeModuleConfiguration implements ModuleConfiguration {

	private List<ModuleConfiguration> configurations;
	
	public CompositeModuleConfiguration(String classes) {
		this.configurations = new ArrayList<ModuleConfiguration>();
		String[] splitClasses = classes.split(",");
		for (String classToInstantiate : splitClasses) {
			if(!"".equals(classToInstantiate)){
				try {
		            ModuleConfiguration configuration = (ModuleConfiguration) Class.forName(classToInstantiate.trim()).newInstance();
		            this.configurations.add(configuration);
	            } catch (InstantiationException e) {
		            throw new RuntimeException(e);
	            } catch (IllegalAccessException e) {
		            throw new RuntimeException(e);
	            } catch (ClassNotFoundException e) {
		            throw new RuntimeException(e);
	            }
			}
        }
    }
	
	public ModelView getModuleNavigationView() {
		CompositeView view = new CompositeView();
		for (ModuleConfiguration configuration : this.configurations) {
	        ModelView sideNavigationView = configuration.getModuleNavigationView();
	        if(sideNavigationView!=null) {
	        	view.add(sideNavigationView);
	        }
        }
	    return view;
    }

	public Initializer getInitializer() {
		CompositeInitializer initializer = new CompositeInitializer();
		for (ModuleConfiguration configuration : this.configurations) {
			Initializer currentInitializer = configuration.getInitializer();
			if(currentInitializer!=null) {
				initializer.add(currentInitializer);
			}
		}
	    return initializer;
    }
	
}

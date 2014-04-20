package uk.co.itstherules.yawf.modelview;

import java.util.List;
import java.util.Set;

import uk.co.itstherules.yawf.register.NotRegistered;
import uk.co.itstherules.yawf.register.PackagedClassesAssignableFrom;
import uk.co.itstherules.yawf.register.Register;


public final class ModelViewRegister {
	
	private final Register<ModelView> register;
	
	public ModelViewRegister(final List<String> viewPackages) {
		this.register = new PackagedClassesAssignableFrom<ModelView>(null).collect(ModelView.class, viewPackages);
	}
	
	public ModelView get(String view) { 
		try {
			return this.register.get(view); 
		} catch (NotRegistered e) {
			return new ViewNotRegisteredModelView();
		}
	}
	
	public Set<String> available() {
		return this.register.available();
    }
}

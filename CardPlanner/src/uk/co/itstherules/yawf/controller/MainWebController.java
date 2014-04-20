package uk.co.itstherules.yawf.controller;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.register.PackagedClassesAssignableFrom;
import uk.co.itstherules.yawf.register.Register;
import uk.co.itstherules.yawf.view.context.DefaultContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.xhtml.ErrorView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public final class MainWebController implements Controller {
	
	private final Register<Controller> register;

	public MainWebController(final List<String> packages) {
		Controller defaultInstance = new RouteProcessStepController();
		register = new PackagedClassesAssignableFrom<>(defaultInstance).collect(Controller.class, packages);
    }
	
	@Override public void control(final ValuesProvider provider, final HttpServletResponse response, final ModelViewRegister viewFactory) throws Exception {
		try {
			Controller controller = register.get(provider.getController());
			controller.control(provider, response, viewFactory);
		} catch (Exception e) {
			ViewContext context = new DefaultContext("Error", "Error");
			context.put("error", Object.class.cast(e));
			new ErrorView().renderTo(context, response, provider.getString("root",""));
		}
    }

	@Override
    public String getTitle() {
	    return "MainWebController";
    }

	@Override
    public String getKey() {
	    return getTitle();
    }
}

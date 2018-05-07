package uk.co.itstherules.yawf.controller;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.TextStringView;
import uk.co.itstherules.yawf.view.context.DefaultContext;

public class NotRecognisedController implements Controller {
	
	public void control(ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
		new TextStringView("You have input a URL which has not been recognised").renderTo(new DefaultContext(getTitle(), "Error"), response, provider.getApplicationRoot());
	}

	public String getTitle() { return "NotRecognised"; }

	@Override
    public String getKey() {
	    return getTitle();
    }

}

package uk.co.itstherules.yawf.controller;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

import javax.servlet.http.HttpServletResponse;

public final class NullController implements Controller {

    @Override public void control(ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
    }

    @Override public String getKey() {
        return "Null";
    }

    @Override public String getTitle() {
        return "Null";
    }
}

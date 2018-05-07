package uk.co.itstherules.yawf.controller;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.helper.UrlBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public final class NullController implements Controller {

    @Override public void control(ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
        String url = new UrlBuilder(provider.getApplicationRoot()).url(provider.getController(), provider.getAction(), provider.getIdentity(), provider.getTitle());
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        PrintWriter writer = response.getWriter();
        writer.write("The route: ");
        writer.write(url);
        writer.write(" cannot be found");
        writer.close();
    }

    @Override public String getKey() {
        return "Null";
    }

    @Override public String getTitle() {
        return "Null";
    }
}

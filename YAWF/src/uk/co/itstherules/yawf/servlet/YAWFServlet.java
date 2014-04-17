package uk.co.itstherules.yawf.servlet;

import uk.co.itstherules.yawf.controller.MainWebController;
import uk.co.itstherules.yawf.inbound.ContextValuesProvider;
import uk.co.itstherules.yawf.inbound.DefaultValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.WebValuesProvider;
import uk.co.itstherules.yawf.modelview.EmptyModelView;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.ModuleNavigationRegister;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class YAWFServlet extends HttpServlet {

    private static final long serialVersionUID = 6206169463288665364L;
    private MainWebController webController;
    private ModelViewRegister viewRegister;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ContextValuesProvider provider = new ContextValuesProvider(config.getServletContext(), new DefaultValuesProvider());
        ModuleNavigationRegister.create(new EmptyModelView());
        viewRegister = new ModelViewRegister(provider.getList("viewRoot"));
        webController = new MainWebController(provider.getList("controllerRoot"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ValuesProvider provider = new WebValuesProvider(request.getServletContext(), request);
        try {
            webController.control(provider, response, viewRegister);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

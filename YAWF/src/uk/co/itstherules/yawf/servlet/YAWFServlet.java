package uk.co.itstherules.yawf.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.MainWebController;
import uk.co.itstherules.yawf.controller.processor.ProcessStepRegister;
import uk.co.itstherules.yawf.inbound.ContextValuesProvider;
import uk.co.itstherules.yawf.inbound.DefaultValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.WebValuesProvider;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.module.CompositeModuleConfiguration;
import uk.co.itstherules.yawf.module.ModuleConfiguration;
import uk.co.itstherules.yawf.view.ModuleNavigationRegister;


public final class YAWFServlet extends HttpServlet {

    private static final long serialVersionUID = 6206169463288665364L;
	private MainWebController webController;
	private ServletContext servletContext;
	private ModelViewRegister viewFactory;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.servletContext = config.getServletContext();
		ContextValuesProvider provider = new ContextValuesProvider(this.servletContext, new DefaultValuesProvider());
		String moduleConfigurationClasses = provider.getString("moduleConfigurationClasses");
		ModuleConfiguration configuration = new CompositeModuleConfiguration(moduleConfigurationClasses);
		configuration.getInitializer().initialize(config);
		ModuleNavigationRegister.create(configuration.getModuleNavigationView());
		this.viewFactory = new ModelViewRegister(provider.getList("viewRoot"));
		ProcessStepRegister.init(provider.getList("processRoot"));
		this.webController = new MainWebController(provider.getList("controllerRoot"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ValuesProvider provider = new WebValuesProvider(this.servletContext, request);
        try {
	        this.webController.control(provider, response, this.viewFactory);
        } catch (Exception e) {
	        throw new ServletException(e);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

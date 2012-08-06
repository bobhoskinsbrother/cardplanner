package uk.co.itstherules.yawf.controller.interfaces;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.TitleProvider;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.register.Keyed;


public interface Controller extends TitleProvider, Keyed {

	void control(ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception;

}
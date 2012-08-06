package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;



public final class Login extends BaseController {

	@Action("Show") public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String username = provider.getString("username");
		String password = provider.getString("password");
		Boolean rememberMe = provider.getBoolean("rememberMe", Boolean.FALSE);
		if("".equals(username) || "".equals(password)) {
			PageModel loginPage = SpecialInstances.retrieve(objectCache, Identities.LOGIN_SHOW_PAGE);
			new PageModelView(loginPage, viewFactory).renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
		} else {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe.booleanValue());
			String root = provider.getApplicationRoot();
			try {
				SecurityUtils.getSubject().login(token);
				token.clear();
				response.sendRedirect(response.encodeRedirectURL(root));
			} catch (UnknownAccountException ex) {
				response.sendRedirect(response.encodeRedirectURL(showUrl("error.xhtml", root)));
			} catch (IncorrectCredentialsException ex) {
				response.sendRedirect(response.encodeRedirectURL(showUrl("error.xhtml", root)));
			} catch (Exception ex) {
				response.sendRedirect(response.encodeRedirectURL(showUrl("error.xhtml", root)));
			}
		}
	}

}
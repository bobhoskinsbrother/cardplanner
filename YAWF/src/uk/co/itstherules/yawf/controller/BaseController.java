package uk.co.itstherules.yawf.controller;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.interfaces.Controller;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.dispatcher.ActionDispatcher;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.helper.XHtmlTagBuilder;
import uk.co.itstherules.yawf.view.json.JsonView;
import freemarker.template.TemplateHashModelEx;

public abstract class BaseController implements Controller {

	public void control(ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
		new ActionDispatcher().dispatch(this, provider, response, viewFactory);
	}
	
    public String getTitle() { return this.getClass().getSimpleName(); }

    @Override public String getKey() { return getTitle(); }

    public String listUrl(String root) { return new XHtmlTagBuilder(root).listUrl(getTitle(), ""); }
    public String listUrl(String title, String root) { return new XHtmlTagBuilder(root).listUrl(getTitle(), title); }
    public String listUrl(String title, String identity, String root) { return new XHtmlTagBuilder(root).listUrl(getTitle(), identity, title); }
    public String listUrl(String title, String identity, String root, TemplateHashModelEx hash) { return new XHtmlTagBuilder(root).listUrl(getTitle(), identity, title, hash); }
    public String feedUrl(String title, String identity, String root) { return new XHtmlTagBuilder(root).feedUrl(getTitle(), identity, title); }
    public String showUrl(String title, String root) { return new XHtmlTagBuilder(root).showUrl(getTitle(), title); }
    public String showUrl(String title, String identity, String root) { return new XHtmlTagBuilder(root).showUrl(getTitle(), identity, title); }
    public String showUrl(String title, String root, TemplateHashModelEx hash) { return new XHtmlTagBuilder(root).showUrl(getTitle(), title, hash); }

    public void rerouteResponseTo(HttpServletResponse response, String root, String action) {
        RerouteResponse.to(response, root, getTitle(), action);
    }

    @Action(value="Feed", requiresObjectCache=false) public void feed(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws Exception {
		new JsonView(Collections.singletonMap("identity", "0")).renderTo(null, response, null);
    }
    
}

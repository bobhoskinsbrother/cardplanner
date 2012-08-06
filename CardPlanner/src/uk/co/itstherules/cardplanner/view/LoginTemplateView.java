package uk.co.itstherules.cardplanner.view;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;


public class LoginTemplateView extends BaseModelView {

	private final String content;
	private final String controller;
	private final String title;

	public LoginTemplateView(String content, String controller, String title) {
		this.content = content;
		this.controller = controller;
		this.title = title;
	}

	
	@Override public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		ViewContext context = new EmptyContext();
		context.put("controller", controller);
		context.put("title", title);
		context.put("content", content);
		return new MergedTextView("login.freemarker", Root.class).asText(context, valuesProvider.getApplicationRoot());
    }


	public String getViewTitle() {
	    return "Login";
    }
}

package uk.co.itstherules.cardplanner.view;

import java.util.ArrayList;
import java.util.List;

import uk.co.itstherules.cardplanner.view.context.JavascriptAndCssContext;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class TemplateCompositeModelView extends BaseModelView {
	
	private final boolean isNaked;
	private final String content;
	private final String controller;
	private final String title;
	private final boolean feedMe;
    private List<String> additionalCssList;
    private List<String> additionalJavascriptList;

    public TemplateCompositeModelView(boolean isNaked, String content, String controller, String title, boolean feedMe, List<String> additionalCssList, List<String> additionalJavascriptList) {
		this.isNaked = isNaked;
		this.content = content;
		this.controller = controller;
		this.title = title;
		this.feedMe = feedMe;
        this.additionalCssList = additionalCssList;
        this.additionalJavascriptList = additionalJavascriptList;
    }

	public TemplateCompositeModelView(boolean isNaked, String content, String controller, String title, boolean feedMe) {
        this(isNaked, content,controller,title,feedMe,new ArrayList<String>(), new ArrayList<String>());
	}

	private String template() {
		if(isNaked) { return "pop.freemarker"; } 
		return "template.freemarker";
	}

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		if(objectCache==null) { throw new IllegalArgumentException("ObjectCache needs to be set.  Have you set requiresObjectCache to true in your controller?"); }
		ViewContext delegate = new JavascriptAndCssContext(valuesProvider.getApplicationRoot(), additionalCssList, additionalJavascriptList);
		delegate.put("controller", controller);
		delegate.put("title", title);
		delegate.put("content", content);
		delegate.put("feedMe", feedMe);
        delegate.put("sideBar", new SideBarModelView().asText(objectCache, valuesProvider, null, violations));

	    return new MergedTextView(template()).asText(delegate, valuesProvider.getApplicationRoot());
    }
	
}
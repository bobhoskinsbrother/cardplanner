package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.TagModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.ModuleNavigationRegister;
import uk.co.itstherules.cardplanner.view.context.JavascriptAndCssContext;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template extends BaseModelView {

    private final List<String> additionalCssList;
    private List<String> additionalJavascriptList;

    public Template() { this(new ArrayList<String>(), new ArrayList<String>()); }

    public Template(List<String> additionalCssList, List<String> additionalJavascriptList) {
        super();
        this.additionalCssList = additionalCssList;
        this.additionalJavascriptList = additionalJavascriptList;
    }

	private String template() {
		return "template.freemarker";
	}

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
		if(objectCache==null) { throw new IllegalArgumentException("Object Cache needs to be set.  Have you set requiresObjectCache to true in your controller?"); }
		ViewContext context = new JavascriptAndCssContext(valuesProvider.getApplicationRoot(), additionalCssList, additionalJavascriptList);
		if(mixInContext!= null) { context.putAll(mixInContext); }
		context.put("controller", valuesProvider.getController());
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("navigable", Boolean.TRUE);
		context.put("tags", objectCache.all(TagModel.class));
		context.put("modulesNav", ModuleNavigationRegister.getView().asText(objectCache, valuesProvider, new EmptyContext(), violations));

	    return new MergedTextView(template()).asText(context, valuesProvider.getApplicationRoot());
    }
	
}
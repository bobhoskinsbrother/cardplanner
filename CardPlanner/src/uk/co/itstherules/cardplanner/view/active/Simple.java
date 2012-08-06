package uk.co.itstherules.cardplanner.view.active;

import java.util.ArrayList;
import java.util.List;

import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SideBarModelView;
import uk.co.itstherules.cardplanner.view.context.JavascriptAndCssContext;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class Simple extends BaseModelView {


    private final List<String> additionalCssList;
    private List<String> additionalJavascriptList;

    public Simple() { this(new ArrayList<String>(), new ArrayList<String>()); }

    public Simple(List<String> additionalCssList, List<String> additionalJavascriptList) {
        super();
        this.additionalCssList = additionalCssList;
        this.additionalJavascriptList = additionalJavascriptList;
    }

    @Override public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		ViewContext context = new JavascriptAndCssContext(provider.getApplicationRoot(), additionalCssList, additionalJavascriptList);
		if(mixInContext!= null) { context.putAll(mixInContext); }
        context.put("sideBar", new SideBarModelView().asText(objectCache, provider, new EmptyContext(), violations));
	    return new MergedTextView(getTemplate()).asText(context, provider.getApplicationRoot());
    }

    protected String getTemplate() { return "simple.freemarker"; }
	
}

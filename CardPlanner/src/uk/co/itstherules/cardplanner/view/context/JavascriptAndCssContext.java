package uk.co.itstherules.cardplanner.view.context;

import uk.co.itstherules.yawf.view.context.BaseContext;
import uk.co.itstherules.yawf.view.context.MapContext;
import uk.co.itstherules.yawf.view.helper.TagBuilder;
import uk.co.itstherules.yawf.view.helper.XHtmlTagBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavascriptAndCssContext extends BaseContext {

    private enum Css {
		GENERAL,
		OPENTIP,
		LIGHTWINDOW
        ;
		
		public String toString() { return name().toLowerCase(); };
	}
	
	private enum JavaScript {
        PROTOTYPE,
        SCRIPTACULOUS,
        UUID,
        DUMB_MVC_1_0,
        JAVASCRIPT_EXTENSIONS,
		EFFECTS,
		BUILDER,
		DATEFORMAT,
		DRAGDROP,
		LIGHTWINDOW,
		SIDEBAR,
		OPENTIP,
		CONTROLLER,
		EXCANVAS
		;
		public String toString() { return name().toLowerCase(); };
	}

    public JavascriptAndCssContext(String root) {
        this(root, new ArrayList<String>(), new ArrayList<String>());
    }

	public JavascriptAndCssContext(String root, List<String> cssList, List<String> javascriptList) {
        super(new MapContext(new HashMap<String, Object>()));
        TagBuilder tagBuilder = new XHtmlTagBuilder(root);
        put("css", makeCssTags(cssList, tagBuilder));
        put("javascript", makeJavaScriptTags(javascriptList, tagBuilder));
    }

    private String makeCssTags(List<String> cssList, TagBuilder tagBuilder) {
        List<String> files = new ArrayList<String>();
        for(Css value : Css.values()) {
            files.add(value.toString());
        }
        StringBuilder b = new StringBuilder(tagBuilder.loadCss(files));
        if(!cssList.isEmpty()) {
            b.append(tagBuilder.loadCss(cssList));
        }
        return b.toString();
    }

    private String makeJavaScriptTags(List<String> javascriptList, TagBuilder tagBuilder) {
        List<String> files = new ArrayList<String>();
        for(JavaScript value : JavaScript.values()) {
            files.add(value.toString());
        }
        StringBuilder b = new StringBuilder(tagBuilder.loadScripts(files));
        if(!javascriptList.isEmpty()) {
            b.append(tagBuilder.loadScripts(javascriptList));
        }
        return b.toString();
    }

}
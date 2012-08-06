package uk.co.itstherules.yawf.modelview;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.view.context.ViewContext;

public abstract class BaseModelView implements ModelView {
	public void renderTo(ObjectCache objectCache, ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations)  throws IllegalArgumentException {
		try {
			if("".equals(response.getContentType())) response.setContentType(ContentType.html.toString());
			response.setHeader("Cache-Control","no-cache"); 
			response.setHeader("Pragma","no-cache"); 
			response.setDateHeader("Expires", -1);

			Writer writer = getWriter(response);
			writer.write(asText(objectCache, valuesProvider, mixInContext, violations));
			writer.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public String getViewTitle(){
		return "";
	}
	
	@Override
    public String getKey() { return this.getClass().getSimpleName(); }

	protected Writer getWriter(HttpServletResponse response) throws IOException {
	    Writer writer = response.getWriter();
	    return writer;
    }

	
	public abstract String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) throws IllegalArgumentException;
}

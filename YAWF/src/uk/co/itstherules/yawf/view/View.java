package uk.co.itstherules.yawf.view;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.view.context.ViewContext;

public interface View {
	
	public void renderTo(ViewContext context, HttpServletResponse response, String root);
	
	public String asText(ViewContext context, String root);
	
	public String getTitle();

}

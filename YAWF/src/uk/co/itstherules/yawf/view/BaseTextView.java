package uk.co.itstherules.yawf.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.string.io.StringLoader;
import uk.co.itstherules.yawf.view.context.ViewContext;

public abstract class BaseTextView implements View {

	private final String string;

	public BaseTextView(String string) { this.string = string; }
	
	public String asText(ViewContext context, String root) { 
	    try {
	        return stringLoader().asString(string);
	    } catch (FileNotFoundException e) {
	    	return "";
	    }
    }
    
    protected abstract StringLoader stringLoader();

    public void renderTo(ViewContext context, HttpServletResponse response, String root) {
        try {
        	Writer writer;
	        writer = response.getWriter();
	        writer.write(asText(context, root));
	        writer.close();
        } catch (IOException e) {
        	throw new RuntimeException(e); 
        }
    }

}

package uk.co.itstherules.yawf.view;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.view.context.ViewContext;

public final class JavaScriptMinifierView implements View {

    private final String string;

    public JavaScriptMinifierView(String string) {
        this.string = string;
    }

    @Override
    public void renderTo(ViewContext context, HttpServletResponse response, String root) {
        new TextStringView(asText(context, root)).renderTo(context, response, root);
    }

    @Override
    public String asText(ViewContext context, String root) {
        Reader reader = new StringReader(string);
        Writer writer = new StringWriter();
        try {
            new JSMin().minify(reader, writer);
        } catch (Exception e) {
        }
        return writer.toString();
    }

    public String getTitle() { return this.getClass().getSimpleName(); }


}

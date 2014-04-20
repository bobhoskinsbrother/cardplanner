package uk.co.itstherules.yawf.view;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.view.context.ViewContext;

import com.yahoo.platform.yui.compressor.CssCompressor;

public final class CssMinifierView implements View {

    private final String string;

    public CssMinifierView(String string) {
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
            CssCompressor compressor = new CssCompressor(reader);
            int lineBreakColumnPosition = -1;
            compressor.compress(writer, lineBreakColumnPosition);
        } catch (Exception e) {
        }
        return writer.toString();
    }

    public String getTitle() { return this.getClass().getSimpleName(); }


}
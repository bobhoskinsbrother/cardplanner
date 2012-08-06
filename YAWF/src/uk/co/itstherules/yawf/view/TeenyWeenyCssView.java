package uk.co.itstherules.yawf.view;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.view.context.ViewContext;

public final class TeenyWeenyCssView implements View {

    private final Class<?> fileRoot;
    private final boolean acceptsGZip;
    private final List<String> files;

    public TeenyWeenyCssView(List<String> files, Class<?> fileRoot, boolean acceptsGZip) {
        this.files = files;
        this.fileRoot = fileRoot;
        this.acceptsGZip = acceptsGZip;
    }

    @Override
    public void renderTo(ViewContext context, HttpServletResponse response, String root) {
        response.setContentType(ContentType.css.toString());
        HeaderCachingInformation.setAsNeverAskAgain(response);
        new GZipTextStringView(asText(context, root), acceptsGZip).renderTo(context, response, root);
    }

    @Override
    public String asText(ViewContext context, String root) {
        StringBuffer reply = new StringBuffer();
        String lineSeparator = System.getProperty("line.separator");
        for(String file : files) {
            String title = MessageFormat.format("css/{0}.css.freemarker", file);
            reply.append(new MergedTextView(title, fileRoot).asText(context, root));
            reply.append(lineSeparator);
        }
        return new CssMinifierView(reply.toString()).asText(context, root);
    }

    public String getTitle() { return this.getClass().getSimpleName(); }


}
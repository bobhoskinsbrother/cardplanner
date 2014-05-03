package uk.co.itstherules.yawf.view;

import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public final class TeenyWeenyJavaScriptView implements View {

    private final boolean acceptsGZip;
    private final List<String> files;
    private String resourceRoot;

    public TeenyWeenyJavaScriptView(List<String> files, String resourceRoot, boolean acceptsGZip, boolean debug) {
        this.files = files;
        this.resourceRoot = resourceRoot;
        this.acceptsGZip = acceptsGZip;
    }

    @Override
    public void renderTo(ViewContext context, HttpServletResponse response, String root) {
        response.setContentType(ContentType.js.toString());
        response.setCharacterEncoding("UTF-8");
        HeaderCachingInformation.setAsNeverAskAgain(response);
        new GZipTextStringView(asText(context, root), acceptsGZip).renderTo(context, response, root);
    }

    @Override
    public String asText(ViewContext context, String root) {
        StringBuffer reply = new StringBuffer();
        String lineSeparator = System.getProperty("line.separator");
        for(String file : files) {
            String filePath = new StringBuffer(resourceRoot).append("js/").append(file).append(".js").toString();
            String uncompressed = new TextResourceView(filePath).asText(null, root);
            reply.append(uncompressed);
            reply.append(lineSeparator);
        }
        return reply.toString();
    }

    public String getTitle() { return this.getClass().getSimpleName(); }


}
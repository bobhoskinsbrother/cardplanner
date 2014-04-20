package uk.co.itstherules.yawf.view;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.view.context.ViewContext;

public final class GZipTextStringView implements View {

    private boolean browserAccepts;
    private View delegate;

    public GZipTextStringView(String string, boolean browserAccepts) {
        this.browserAccepts = browserAccepts;
        this.delegate = new TextStringView(string);
    }

    @Override
    public void renderTo(ViewContext context, HttpServletResponse response, String root) {
        try {
            if(browserAccepts) {
                String reply = asText(context, root);
                byte[] replyBytes = reply.getBytes("utf-8");
                response.setHeader("Content-Encoding", "gzip");
                GZIPOutputStream outputStream = new GZIPOutputStream(response.getOutputStream());
                outputStream.write(replyBytes);
                outputStream.close();
            } else {
                this.delegate.renderTo(context, response, root);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String asText(ViewContext context, String root) {
        return delegate.asText(context, root);
    }

    public String getTitle() { return this.getClass().getSimpleName(); }


}

package uk.co.itstherules.yawf.view;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;

import uk.co.itstherules.io.ContentTypeGuesser;
import uk.co.itstherules.io.StreamForwarder;
import uk.co.itstherules.yawf.view.context.ViewContext;

public abstract class BaseBinaryView implements View {

	protected final String resourcePath;

	public BaseBinaryView(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public void renderTo(ViewContext context, HttpServletResponse response, String root) {
		stream(inputStream(), response);
	}

	protected abstract InputStream inputStream();

	protected void stream(InputStream in, HttpServletResponse response) {
		try {
			response.setContentType(ContentTypeGuesser.guessFrom(resourcePath));
            HeaderCachingInformation.setAsNeverAskAgain(response);
			new StreamForwarder().forward(in, response.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
		}
	}

	public String asText(ViewContext context, String root) {
		throw new UnsupportedOperationException("Cannot view binary resources as text");
	}
	
    public String getTitle() { return this.getClass().getSimpleName(); }

}

package uk.co.itstherules.yawf;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public final class ErrorHandler extends AbstractHandler {

	private final Handler delegate;

	public ErrorHandler(Handler delegate) {
		this.delegate = delegate;
	}

	public void handle(String target, Request r, HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		try {
			delegate.handle(target, r, request, response);
		} catch (Exception e) {
			PrintWriter writer = response.getWriter();
			writer.println("<html><head></head><body><code><pre>");
			e.printStackTrace(writer);
			writer.println("</pre></code></body></html>");
			writer.close();
		}
	}
}


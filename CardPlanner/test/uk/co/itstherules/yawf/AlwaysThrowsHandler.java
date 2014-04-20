package uk.co.itstherules.yawf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;

public class AlwaysThrowsHandler implements Handler {
	public void destroy() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Server getServer() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void handle(String arg0, Request r, HttpServletRequest arg1, HttpServletResponse arg2) throws IOException, ServletException {
		throw new RuntimeException("Intentional Throw");
	}

	public void setServer(Server arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isFailed() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isRunning() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isStarted() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isStarting() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isStopped() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isStopping() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void start() throws Exception {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void stop() throws Exception {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	@Override
    public void addLifeCycleListener(Listener arg0) {
    }

	@Override
    public void removeLifeCycleListener(Listener arg0) {
    }
}

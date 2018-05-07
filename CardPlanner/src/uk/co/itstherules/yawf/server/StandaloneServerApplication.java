package uk.co.itstherules.yawf.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.net.URI;
import java.util.EnumSet;
import java.util.Map;

public final class StandaloneServerApplication implements Runnable, ServerConfigReceiver {
	
	private Server server;
	private Integer port;
    private ServletContextHandler servletContext;

	public StandaloneServerApplication(String root, Integer port) {
        setContexts();
        root(root).port(port);
	}

    private StandaloneServerApplication root(String root) {
        servletContext.setContextPath(root);
        return this;
    }

    private void setContexts() {
        servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
    }

    @Override public ServerConfigReceiver filter(String path, Filter filter) {
        final EnumSet<DispatcherType> types = EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST);
        servletContext.addFilter(new FilterHolder(filter), path, types);
        return this;
    }

    @Override public ServerConfigReceiver servlet(String path, HttpServlet servlet) {
        servletContext.addServlet(new ServletHolder(servlet), path);
        return this;
    }

    @Override public ServerConfigReceiver listener(ServletContextListener listener) {
        servletContext.addEventListener(listener);
        return this;
    }

    @Override public ServerConfigReceiver localeMappings(Map<String, String> locales) {
        for (String key : locales.keySet()) {
            final String value = locales.get(key);
            servletContext.addLocaleEncoding(key, value);
        }
        return this;
    }

    @Override public ServerConfigReceiver contextParams(Map<String, String> params) {
        for (String key : params.keySet()) {
            final String value = params.get(key);
            servletContext.setInitParameter(key, value);
        }
        return this;
    }

    @Override public ServerConfigReceiver welcomeFiles(String... files) {
        servletContext.setWelcomeFiles(files);
        return this;
    }

    public ServerConfigReceiver port(int port) {
        this.port = port;
        return this;
    }

	public boolean isStarted() { return server != null && server.isStarted(); }

	public URI startServer() throws Exception {
		server = new Server(port);
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { servletContext });
        server.setHandler(contexts);
        server.start();
		return server.getURI();
	}

	public void run() {
		try {
			startServer();
		} catch (Exception e) {
	        throw new RuntimeException(e);
        }
	}

	public void destroy() {
		if(this.server !=null) {
			try {
				this.server.stop();
				this.server.destroy();
				this.server = null;
	        } catch (Exception e) {
		        throw new RuntimeException(e);
	        }
		}
    }
}

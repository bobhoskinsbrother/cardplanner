package uk.co.itstherules.yawf.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.WebAppContext;

public final class StandaloneServerApplication implements Runnable {
	
	private Server webServer;
	private final String warFile;
	private final String root;
	private final Integer port;


	public static void main(String... args) {
		if(args.length < 2) {
			throw new RuntimeException("Expected at least a root and war file to run");
		}
		String root = args[0];
		String warFile = args[1];
		Integer port = 9999;
		if(args.length == 3) {
			try {
				port = Integer.valueOf(args[2]);
			} catch (Exception e) {
			}
		}
		Thread thread = new Thread(new StandaloneServerApplication(root, warFile, port));
		thread.start();
	}

	public StandaloneServerApplication(String root, String warFile, Integer port) {
		this.root = root;
		this.warFile = warFile;
		this.port = port;
	}
	
	public boolean isStarted() { return webServer != null && webServer.isStarted(); }

	public boolean startServer() throws Exception {
		Log.setLog(new SystemOutLogger());
		this.webServer = new Server(port.intValue());
		if ("".equals(this.warFile)) {
			throw new IllegalArgumentException("warFile hasn't been specified");
		}
		WebAppContext context = new WebAppContext(this.warFile, this.root);
		this.webServer.setHandler(context);
		this.webServer.start();
		return true;
	}

	public void run() {
		try {
			startServer();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
	        throw new RuntimeException(e);
        }
	}

	public void destroy() {
		if(this.webServer!=null) {
			try {
				this.webServer.stop();
				this.webServer.destroy();
				this.webServer = null;
	        } catch (Exception e) {
		        throw new RuntimeException(e);
	        }
		}
    }
}

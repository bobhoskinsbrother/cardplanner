package uk.co.itstherules.yawf.server;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
import java.util.Map;

public interface ServerConfigReceiver {

    ServerConfigReceiver servlet(String path, HttpServlet servlet);
    ServerConfigReceiver filter(String path, Filter filter);
    ServerConfigReceiver listener(ServletContextListener listener);
    ServerConfigReceiver localeMappings(Map<String, String> locales);
    ServerConfigReceiver contextParams(Map<String, String> params);
    ServerConfigReceiver welcomeFiles(String... files);

}

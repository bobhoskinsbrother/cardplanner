package uk.co.itstherules.string.url;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {
	public static String base(String urlString, String contextPath) {
		if (urlString != null) {
            try {
            	URL url;
	            url = new URL(urlString);
	            StringBuffer buffer = new StringBuffer();
	            buffer.append(url.getProtocol());
	            buffer.append("://");
	            buffer.append(url.getHost());
	            int port = url.getPort();
	            if (port != -1 && port != 80) {
	            	buffer.append(":");
	            	buffer.append(port);
	            }
	            buffer.append(contextPath);
	            return buffer.toString();
            } catch (MalformedURLException e) {
            	return "";
            }
		}
		return "";
	}
}

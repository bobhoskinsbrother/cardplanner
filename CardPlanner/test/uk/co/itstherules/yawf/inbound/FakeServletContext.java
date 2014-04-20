package uk.co.itstherules.yawf.inbound;

import javax.servlet.*;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

public class FakeServletContext implements ServletContext {
	
	private final Map<String, String> map;

	public FakeServletContext(Map<String, String> map) {
		this.map = map;
    }
	
	public Object getAttribute(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Enumeration<String> getAttributeNames() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public ServletContext getContext(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getInitParameter(String arg0) {
		return map.get(arg0);
	}

	@SuppressWarnings("unchecked")
    public Enumeration<String> getInitParameterNames() {
		return new IteratorEnumeration(map.keySet().iterator());
	}

	public int getMajorVersion() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getMimeType(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getMinorVersion() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public RequestDispatcher getNamedDispatcher(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getRealPath(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public URL getResource(String arg0) throws MalformedURLException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public InputStream getResourceAsStream(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Set<String> getResourcePaths(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getServerInfo() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Servlet getServlet(String arg0) throws ServletException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getServletContextName() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Enumeration<String> getServletNames() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Enumeration<Servlet> getServlets() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void log(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void log(Exception arg0, String arg1) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void log(String arg0, Throwable arg1) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void removeAttribute(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void setAttribute(String arg0, Object arg1) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getContextPath() {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	@Override
    public Dynamic addFilter(String arg0, String arg1) {
	    throw new RuntimeException("addFilter Not Implemented");
    }

	@Override
    public Dynamic addFilter(String arg0, Filter arg1) {
	    throw new RuntimeException("addFilter Not Implemented");
    }

	@Override
    public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
	    throw new RuntimeException("addFilter Not Implemented");
    }

	@Override
    public void addListener(String arg0) {
	    throw new RuntimeException("addListener Not Implemented");
    }

	@Override
    public <T extends EventListener> void addListener(T arg0) {
	    throw new RuntimeException("addListener Not Implemented");
    }

	@Override
    public void addListener(Class<? extends EventListener> arg0) {
	    throw new RuntimeException("addListener Not Implemented");
    }

	@Override
    public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
            String arg1) {
	    throw new RuntimeException("addServlet Not Implemented");
    }

	@Override
    public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
            Servlet arg1) {
	    throw new RuntimeException("addServlet Not Implemented");
    }

	@Override
    public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
            Class<? extends Servlet> arg1) {
	    throw new RuntimeException("addServlet Not Implemented");
    }

	@Override
    public <T extends Filter> T createFilter(Class<T> arg0)
            throws ServletException {
	    throw new RuntimeException("createFilter Not Implemented");
    }

	@Override
    public <T extends EventListener> T createListener(Class<T> arg0)
            throws ServletException {
	    throw new RuntimeException("createListener Not Implemented");
    }

	@Override
    public <T extends Servlet> T createServlet(Class<T> arg0)
            throws ServletException {
	    throw new RuntimeException("createServlet Not Implemented");
    }

	@Override
    public void declareRoles(String... arg0) {
	    throw new RuntimeException("declareRoles Not Implemented");
    }

    @Override public String getVirtualServerName() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public ClassLoader getClassLoader() {
	    throw new RuntimeException("getClassLoader Not Implemented");
    }

	@Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
	    throw new RuntimeException("getDefaultSessionTrackingModes Not Implemented");
    }

	@Override
    public int getEffectiveMajorVersion() {
	    throw new RuntimeException("getEffectiveMajorVersion Not Implemented");
    }

	@Override
    public int getEffectiveMinorVersion() {
	    throw new RuntimeException("getEffectiveMinorVersion Not Implemented");
    }

	@Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
	    throw new RuntimeException("getEffectiveSessionTrackingModes Not Implemented");
    }

	@Override
    public FilterRegistration getFilterRegistration(String arg0) {
	    throw new RuntimeException("getFilterRegistration Not Implemented");
    }

	@Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
	    throw new RuntimeException("getFilterRegistrations Not Implemented");
    }

	@Override
    public JspConfigDescriptor getJspConfigDescriptor() {
	    throw new RuntimeException("getJspConfigDescriptor Not Implemented");
    }

	@Override
    public ServletRegistration getServletRegistration(String arg0) {
	    throw new RuntimeException("getServletRegistration Not Implemented");
    }

	@Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
	    throw new RuntimeException("getServletRegistrations Not Implemented");
    }

	@Override
    public SessionCookieConfig getSessionCookieConfig() {
	    throw new RuntimeException("getSessionCookieConfig Not Implemented");
    }

	@Override
    public boolean setInitParameter(String arg0, String arg1) {
	    throw new RuntimeException("setInitParameter Not Implemented");
    }

	@Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
	    throw new RuntimeException("setSessionTrackingModes Not Implemented");
    }
}

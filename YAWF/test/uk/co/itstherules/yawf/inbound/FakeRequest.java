package uk.co.itstherules.yawf.inbound;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

public class FakeRequest implements HttpServletRequest {
	private final Map<String, Object> values;

	public FakeRequest(Map<String, Object> values) {
		this.values = values;
    }

	public String getAuthType() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getContextPath() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Cookie[] getCookies() {
		LinkedList<Cookie> cookies = new LinkedList<Cookie>();
		for (String key : values.keySet()) {
			cookies.add(new Cookie(key, values.get(key).toString()));
		}
		return cookies.toArray(new Cookie[]{});
	}

	public long getDateHeader(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getHeader(String arg0) {
		return (String) values.get(arg0);
	}

	@SuppressWarnings("unchecked")
    public Enumeration getHeaderNames() {
		return new IteratorEnumeration(values.keySet().iterator());
	}

	@SuppressWarnings("unchecked")
    public Enumeration getHeaders(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getIntHeader(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getMethod() {
		return String.class.cast(values.get("method"));
	}

	public String getPathInfo() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getPathTranslated() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getQueryString() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getRemoteUser() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getRequestURI() {
		return String.class.cast(values.get("uri"));
	}

	public StringBuffer getRequestURL() {
		return new StringBuffer(String.class.cast(values.get("url")));
	}

	public String getRequestedSessionId() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getServletPath() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public HttpSession getSession() {
		return new FakeHttpSession((String) values.get("session"));
	}

	public HttpSession getSession(boolean arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Principal getUserPrincipal() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isRequestedSessionIdFromCookie() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isRequestedSessionIdFromURL() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isRequestedSessionIdFromUrl() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isRequestedSessionIdValid() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isUserInRole(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Object getAttribute(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	@SuppressWarnings("unchecked")
    public Enumeration getAttributeNames() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getCharacterEncoding() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getContentLength() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getContentType() {
		return (String) this.values.get("contentType");
	}

	public ServletInputStream getInputStream() throws IOException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getLocalAddr() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getLocalName() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getLocalPort() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Locale getLocale() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

    public Enumeration<Locale> getLocales() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getParameter(String arg0) {
		return (String) getParameterMap().get(arg0);
	}

	@SuppressWarnings("unchecked")
    public Map getParameterMap() {
		return (Map) values.get("parameters");
	}

	@SuppressWarnings("unchecked")
    public Enumeration getParameterNames() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String[] getParameterValues(String arg0) {
		Object reply = getParameterMap().get(arg0);
		if(reply == null) { return null; }
		return new String[]{String.class.cast(reply)};
	}

	public String getProtocol() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new StringReader(""));
	}

	public String getRealPath(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getRemoteAddr() {
		return "Remote";	
	}

	public String getRemoteHost() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getRemotePort() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getScheme() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getServerName() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getServerPort() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isSecure() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void removeAttribute(String arg0) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void setAttribute(String arg0, Object arg1) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void setCharacterEncoding(String arg0)
	        throws UnsupportedEncodingException {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	@Override
    public boolean authenticate(HttpServletResponse arg0) throws IOException,
            ServletException {
	    throw new RuntimeException("authenticate Not Implemented");
    }

	@Override
    public Part getPart(String arg0) throws IOException, ServletException {
	    throw new RuntimeException("getPart Not Implemented");
    }

	@Override
    public Collection<Part> getParts() throws IOException, ServletException {
	    throw new RuntimeException("getParts Not Implemented");
    }

	@Override
    public void login(String arg0, String arg1) throws ServletException {
	    throw new RuntimeException("login Not Implemented");
    }

	@Override
    public void logout() throws ServletException {
	    throw new RuntimeException("logout Not Implemented");
    }

	@Override
    public AsyncContext getAsyncContext() {
	    throw new RuntimeException("getAsyncContext Not Implemented");
    }

	@Override
    public DispatcherType getDispatcherType() {
	    throw new RuntimeException("getDispatcherType Not Implemented");
    }

	@Override
    public ServletContext getServletContext() {
	    throw new RuntimeException("getServletContext Not Implemented");
    }

	@Override
    public boolean isAsyncStarted() {
	    throw new RuntimeException("isAsyncStarted Not Implemented");
    }

	@Override
    public boolean isAsyncSupported() {
	    throw new RuntimeException("isAsyncSupported Not Implemented");
    }

	@Override
    public AsyncContext startAsync() throws IllegalStateException {
	    throw new RuntimeException("startAsync Not Implemented");
    }

	@Override
    public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1)
            throws IllegalStateException {
	    throw new RuntimeException("startAsync Not Implemented");
    }
}

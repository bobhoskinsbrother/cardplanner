package uk.co.itstherules.yawf.inbound;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class FakeHttpSession implements HttpSession {
	private final String sessionIdentifier;

	public FakeHttpSession(String sessionIdentifier) {
		this.sessionIdentifier = sessionIdentifier;
    }

	public Object getAttribute(String s) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Enumeration<String> getAttributeNames() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public long getCreationTime() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getId() {
		return sessionIdentifier;
	}

	public long getLastAccessedTime() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public int getMaxInactiveInterval() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public ServletContext getServletContext() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public HttpSessionContext getSessionContext() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Object getValue(String s) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String[] getValueNames() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void invalidate() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isNew() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void putValue(String s, Object obj) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void removeAttribute(String s) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void removeValue(String s) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void setAttribute(String s, Object obj) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void setMaxInactiveInterval(int i) {
		throw new RuntimeException("TODO: Not yet implemented");
	}
}

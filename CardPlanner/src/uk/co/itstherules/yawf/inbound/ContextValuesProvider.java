package uk.co.itstherules.yawf.inbound;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;

public final class ContextValuesProvider extends AbstractValuesProvider {

	private final ServletContext context;

	public ContextValuesProvider(ServletContext context, ValuesProvider delegate) {
		super(delegate);
		this.context = context;
    }

	@Override public Object getAttribute(String key) {
		return this.context.getAttribute(key);
	}

	@Override public Set<String> getKeys() {
		Set<String> reply = new LinkedHashSet<String>();
		reply.addAll(this.delegate.getKeys());
		Enumeration<?> names = this.context.getInitParameterNames();
		while (names.hasMoreElements()) {
			reply.add(String.class.cast(names.nextElement()));
		}
	    return reply;
    }

	@Override public String getString(String key, String defaultString) {
	    String reply = this.context.getInitParameter(key);
		if(reply != null && !"".equals(reply)) { return reply; }
		return this.delegate.getString(key, defaultString);
    }
}
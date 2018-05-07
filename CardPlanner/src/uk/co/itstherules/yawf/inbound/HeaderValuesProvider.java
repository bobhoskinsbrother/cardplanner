package uk.co.itstherules.yawf.inbound;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public final class HeaderValuesProvider extends AbstractValuesProvider {

    private final HttpServletRequest request;

	public HeaderValuesProvider(HttpServletRequest request, ValuesProvider delegate) {
		super(delegate);
		this.request = request;
	}

	@Override
    public Set<String> getKeys() {
		Set<String> reply = new LinkedHashSet<String>();
		reply.addAll(this.delegate.getKeys());
		Enumeration<?> headerNames = this.request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			reply.add(String.class.cast(headerNames.nextElement()));
		}
	    return reply;
    }

	@Override
    public String getString(String key, String defaultString) {
	    String reply = this.request.getHeader(key);
		if(reply != null && !"".equals(reply)) { return reply; }
		return this.delegate.getString(key, defaultString);
    }

}

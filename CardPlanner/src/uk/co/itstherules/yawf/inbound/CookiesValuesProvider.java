package uk.co.itstherules.yawf.inbound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookiesValuesProvider extends AbstractValuesProvider {

    private Map<String, String> keyValuePairs;

	public CookiesValuesProvider(HttpServletRequest request, ValuesProvider delegate) {
		super(delegate);
		this.keyValuePairs = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				this.keyValuePairs.put(cookie.getName(), cookie.getValue());
	        }
		}
	}

	@Override
    public Set<String> getKeys() {
		Set<String> reply = new TreeSet<String>();
		reply.addAll(this.delegate.getKeys());
		reply.addAll(this.keyValuePairs.keySet());
	    return reply;
    }

	@Override
    public String getString(String key, String defaultString) {
		String reply = this.keyValuePairs.get(key);
		if(reply != null && !"".equals(reply)) { return reply; }
		return this.delegate.getString(key, defaultString);
    }

}

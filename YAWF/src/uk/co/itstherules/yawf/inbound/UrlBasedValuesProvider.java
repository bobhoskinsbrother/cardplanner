package uk.co.itstherules.yawf.inbound;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import uk.co.itstherules.string.url.Url;

public final class UrlBasedValuesProvider extends AbstractValuesProvider {

	protected Map<String, String> keyValuePairs;

	public UrlBasedValuesProvider(HttpServletRequest request, ValuesProvider delegate) {
		super(delegate);
		this.keyValuePairs = new LinkedHashMap<String, String>();
		this.keyValuePairs = initializeDefaults(getString("defaultHomePage"));
		String root = getString(ROOT);
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		this.keyValuePairs.put(ROOT, root);
		this.keyValuePairs.put(BASE, Url.base(url, root));
		UrlTokenizer tokenizer = new UrlTokenizer(uri.substring(root.length()));
		if (tokenizer.hasNext()) {
			keyValuePairs.put(CONTROLLER, tokenizer.nextToken());
		}
		if (tokenizer.hasNext()) {
			keyValuePairs.put(ACTION, tokenizer.nextToken());
		}
		if (tokenizer.hasNext()) {
			keyValuePairs.put(IDENTITY, tokenizer.nextToken());
		}
		if (tokenizer.hasNext()) {
			keyValuePairs.put(TITLE, exhaust(tokenizer));
		}
	}

	private String exhaust(UrlTokenizer urlTokenizer) {
		StringBuffer reply = new StringBuffer();
		while (urlTokenizer.hasNext()) {
			try {
	            reply.append(URLDecoder.decode(urlTokenizer.nextToken(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException(e);
            }
			reply.append("/");
		}
		return reply.substring(0, reply.length() - 1);
	}

	private Map<String, String> initializeDefaults(String defaultHomePage) {
		Map<String, String> nameValuePairs = new LinkedHashMap<String, String>();
		UrlTokenizer tokenizer = new UrlTokenizer(defaultHomePage);
		nameValuePairs.put(CONTROLLER, tokenizer.nextToken());
		nameValuePairs.put(ACTION, tokenizer.nextToken());
		nameValuePairs.put(IDENTITY, tokenizer.nextToken());
		nameValuePairs.put(TITLE, exhaust(tokenizer));
		return nameValuePairs;
	}

	public String getString(String key, String defaultString) {
		String reply = this.keyValuePairs.get(key);
		if(reply != null && !"".equals(reply)) return reply;
		return this.delegate.getString(key, defaultString);
	}

	@Override
    public Set<String> getKeys() {
		Set<String> reply = new TreeSet<String>();
		reply.addAll(this.delegate.getKeys());
		reply.addAll(this.keyValuePairs.keySet());
	    return reply;
    }

}

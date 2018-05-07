package uk.co.itstherules.yawf.inbound;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

public final class WebValuesProvider implements ValuesProvider {
	
	private final ValuesProvider delegate;

	public WebValuesProvider(ServletContext context, HttpServletRequest request) {
		// order matters for the sake of security - should not be able to override context with request for example
		this.delegate = 
			new ContextValuesProvider(context, 
				new HeaderValuesProvider(request, 
						new CookiesValuesProvider(request, 
								new RequestValuesProvider(request, 
										new UrlBasedValuesProvider(request, 
												new ContextValuesProvider(context, 
														new DefaultValuesProvider()
												)
										)
								)
						)
				)
			);
    }

	public String getAction() {
	    return delegate.getAction();
    }

	public String getApplicationRoot() {
	    return delegate.getApplicationRoot();
    }

	public Object getAttribute(String key) {
	    return delegate.getAttribute(key);
    }

	public String getBase() {
	    return delegate.getBase();
    }

	public Boolean getBoolean(String key, Boolean defaultValue) {
	    return delegate.getBoolean(key, defaultValue);
    }

	public String getController() {
	    return delegate.getController();
    }

	public Date getDate(String key, Date date) {
	    return delegate.getDate(key, date);
    }

	public Date getDate(String key) {
	    return delegate.getDate(key);
    }

	public double getDouble(String key, double defaultDouble) {
	    return delegate.getDouble(key, defaultDouble);
    }

	public FileItem getFileItem(String key) {
	    return delegate.getFileItem(key);
    }

	public String getIdentity() {
	    return delegate.getIdentity();
    }

	public Integer getInteger(String key, Integer defaultInt) {
	    return delegate.getInteger(key, defaultInt);
    }

	public Set<String> getKeys() {
	    return delegate.getKeys();
    }

	public List<String> getList(String key) {
	    return delegate.getList(key);
    }

	public Long getLong(String key, long defaultValue) {
	    return delegate.getLong(key, defaultValue);
    }

	public List<Long> getLongs(String string, List<Long> list) {
	    return delegate.getLongs(string, list);
    }

	public Map<String, String> getMap(String key, Map<String, String> defaultMap) {
	    return delegate.getMap(key, defaultMap);
    }

	public Set<String> getSet(String key) {
	    return delegate.getSet(key);
    }

	public String getString(String key, String defaultString) {
	    return delegate.getString(key, defaultString);
    }

	public String getString(String key) {
	    return delegate.getString(key);
    }

	public String getTitle() {
	    return delegate.getTitle();
    }

	public ValuesProvider limitContext(String context) {
	    return delegate.limitContext(context);
    }

}

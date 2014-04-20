package uk.co.itstherules.yawf.inbound;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

public final class RequestValuesProvider implements ValuesProvider {
	
	private ValuesProvider delegate;

	public RequestValuesProvider(HttpServletRequest request, ValuesProvider delegate) {
		this.delegate = new RequestValuesProviderFactory().create(request, delegate);
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		return this.delegate.getBoolean(key, defaultValue);
	}

	public String getController() {
		return this.delegate.getController();
	}

	public String getAction() {
		return this.delegate.getAction();
	}

	public double getDouble(String key, double defaultDouble) {
		return this.delegate.getDouble(key, defaultDouble);
	}

	public String getIdentity() {
		return this.delegate.getIdentity();
	}

	public Integer getInteger(String key, Integer defaultInt) {
		return this.delegate.getInteger(key, defaultInt);
	}

	public Set<String> getKeys() {
		return this.delegate.getKeys();
	}

	public List<String> getList(String key) {
		return this.delegate.getList(key);
	}

	public Long getLong(String key, long defaultValue) {
		return this.delegate.getLong(key, defaultValue);
	}

	public Map<String, String> getMap(String key, Map<String, String> defaultMap) {
		return this.delegate.getMap(key, defaultMap);
	}

	public String getString(String key, String defaultString) {
		return this.delegate.getString(key, defaultString);
	}

	public String getTitle() {
		return this.delegate.getTitle();
	}

	public String getString(String key) {
		return this.delegate.getString(key);
    }

	public FileItem getFileItem(String key) {
		return delegate.getFileItem(key);
    }

	public List<Long> getLongs(String string, List<Long> list) {
		return delegate.getLongs(string, list);
    }

	public String getApplicationRoot() {
	    return delegate.getApplicationRoot();
    }

	public Date getDate(String key, Date date) {
	    return delegate.getDate(key, date);
    }

	public Date getDate(String key) {
	    return delegate.getDate(key);
    }

	@Override
    public String getBase() {
	    return delegate.getBase();
    }

	@Override
    public ValuesProvider limitContext(String context) {
	    return delegate.limitContext(context);
    }

	@Override
    public Set<String> getSet(String key) {
	    return delegate.getSet(key);
    }

	@Override
    public Object getAttribute(String key) {
		return delegate.getAttribute(key);
    }

}

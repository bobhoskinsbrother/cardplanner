package uk.co.itstherules.yawf.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

import uk.co.itstherules.yawf.inbound.ValuesProvider;

public class FakeValuesProvider implements ValuesProvider {
	
	public FakeValuesProvider() {
    }
	
	public void clearFeedUpdateRequiredRegister() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getController() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getAction() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public double getDouble(String key, double defaultDouble) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public File getFile(String key, File defaultFile) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getIdentity() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Integer getInteger(String key, Integer defaultInt) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Set<String> getKeys() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public List<String> getList(String key) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Long getLong(String key, long defaultValue) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public Map<String, String> getMap(String key, Map<String, String> defaultMap) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getString(String key) {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public String getString(String key, String defaultString) {
		return defaultString;
	}

	public String getTitle() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public boolean isFeedUpdateRequired() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public void registerUpdate() {
		throw new RuntimeException("TODO: Not yet implemented");
	}

	public FileItem getFileItem(String key) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public List<Long> getLongs(String string, List<Long> list) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public String getApplicationRoot() {
	    return null;
    }

	public Date getDate(String key, Date date) {
	    return null;
    }

	public Date getDate(String key) {
	    return null;
    }

	@Override
    public String getBase() {
	    return null;
    }

	@Override
    public ValuesProvider limitContext(String context) {
	    return this;
    }

	@Override
    public Set<String> getSet(String key) {
	    return null;
    }

	@Override
    public Object getAttribute(String key) {
	    return null;
    }
}

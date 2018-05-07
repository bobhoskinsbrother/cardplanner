package uk.co.itstherules.yawf.inbound;

import org.apache.commons.fileupload.FileItem;
import uk.co.itstherules.date.DateConverter;
import uk.co.itstherules.string.manipulation.ListParser;
import uk.co.itstherules.yawf.FileStuff;

import java.util.*;

public abstract class AbstractValuesProvider implements ValuesProvider {

	protected final ValuesProvider delegate;

	protected AbstractValuesProvider(ValuesProvider delegate) {
		this.delegate = delegate;
    }

	public String getController() {
		return getString(CONTROLLER);
	}

	public String getAction() {
		return getString(ACTION);
	}
	
	@Override
	public String getBase() {
		return getString(BASE);
	}

	public String getTitle() {
		return getString(TITLE).trim();
	}
	
	public String getIdentity() { return getString(IDENTITY, "0"); }

	public String getApplicationRoot() { return getString(ROOT); }

	public String getString(String key) {
		return getString(key, "");
	}

	public abstract String getString(String key, String defaultString);
	
	public Set<String> getSet(String key) {
		return new LinkedHashSet<>(getList(key));
	}
	
	public List<String> getList(String key) {
        return ListParser.parse(getString(key));
	}
	
    public Map<String, String> getMap(String key, Map<String, String> defaultMap) {
		String values = getString(key);
		try {
			return MultipleNameValuesParser.parse(values);
		} catch(Exception e) {
			return defaultMap;
		}
    }
    
	public Date getDate(String key, Date defaultDate) {
		try {
			return new DateConverter().fromDate(getString(key));
		} catch(Exception e) {
			return defaultDate;
		}
	}
	
	public Date getDate(String key) { return getDate(key, new Date()); }

	public Integer getInteger(String key, Integer defaultInt) {
		try {
			return Integer.parseInt(getString(key));
		} catch(Exception e) {
			return defaultInt;
		}
    }

	public Long getLong(String key, long defaultLong) {
		try {
			return Long.parseLong(getString(key));
		} catch(Exception e) {
			return defaultLong;
		}
    }

	public List<Long> getLongs(String key, List<Long> defaultLongs) {
		try {
			List<String> list = getList(key);
			List<Long> reply = new LinkedList<Long>();
			for (String string : list) {
	            reply.add(Long.valueOf(string));
            }
			return reply;
		} catch(Exception e) {
			return defaultLongs;
		}
    }
	
	public Boolean getBoolean(String key, Boolean defaultBoolean) {
		String value = getString(key);
		if("true".equalsIgnoreCase(value)) {
			return Boolean.TRUE;
		}
		if("false".equalsIgnoreCase(value)) {
			return Boolean.FALSE;
		}
		return defaultBoolean;
    }

	public double getDouble(String key, double defaultDouble) {
		try {
			return Double.parseDouble(getString(key));
		} catch(Exception e) {
			return defaultDouble;
		}
    }

	public abstract Set<String> getKeys();
		
	@Override public FileItem getFileItem(String key) {
		return this.delegate.getFileItem(key);
    }

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("CrudController: ");
		buffer.append(getController());
		buffer.append(FileStuff.LINE_ENDING);
		buffer.append("Action: ");
		buffer.append(getAction());
		buffer.append(FileStuff.LINE_ENDING);
		buffer.append("Title: ");
		buffer.append(getTitle());
		buffer.append(FileStuff.LINE_ENDING);
		return buffer.toString();
	}
	
	@Override public ValuesProvider limitContext(String context) {
		Set<String> keys = getKeys();
		Map<String, Object> reply = new LinkedHashMap<String,Object>();
		for (String key : keys) {
	        if(key.startsWith(context) && key.length() > context.length() && ".".equals(key.substring(context.length(), context.length()+1))) {
	        	String relevantKey = key.substring(context.length()+1);
				reply.put(relevantKey, getString(key));
	        }
		}
		return new MapValuesProvider(reply);
	}
	
	@Override
	public Object getAttribute(String key) {
		return this.delegate.getAttribute(key);
	}
	
}

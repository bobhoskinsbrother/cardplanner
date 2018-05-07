package uk.co.itstherules.yawf.inbound;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

public interface ValuesProvider {

	static final String ACTION = "action";
	static final String BASE = "base";
	static final String CONTROLLER = "controller";
	static final String CONTENT_BODY = "contentBody";
	static final String IDENTITY = "identity";
	static final String ROOT = "root";
	static final String TITLE = "title";

	String getAction();
	String getBase();
	String getController();
	String getTitle();
	String getIdentity();
	String getApplicationRoot();
	List<String> getList(String key);
	Set<String> getSet(String key);
	String getString(String key);
	String getString(String key, String defaultString);
	Integer getInteger(String key, Integer defaultInt);
	Long getLong(String key, long defaultValue);
	List<Long> getLongs(String string, List<Long> list);
	double getDouble(String key, double defaultDouble);
	Boolean getBoolean(String key, Boolean defaultValue);
	Date getDate(String key, Date date);
	Date getDate(String key);
	Map<String, String> getMap(String key, Map<String, String> defaultMap);
	Set<String> getKeys();
	FileItem getFileItem(String key);
	ValuesProvider limitContext(String context);
	Object getAttribute(String key);

}
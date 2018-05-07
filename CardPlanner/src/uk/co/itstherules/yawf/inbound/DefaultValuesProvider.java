package uk.co.itstherules.yawf.inbound;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.fileupload.FileItem;

public final class DefaultValuesProvider extends AbstractValuesProvider {

	public DefaultValuesProvider() {
		super(null);
	}

	@Override public FileItem getFileItem(String key) {
		throw new RuntimeException();
    }

	@Override public Set<String> getKeys() {
		return new TreeSet<String>();
    }

	@Override public String getString(String key, String defaultString) {
		return defaultString;
    }

}

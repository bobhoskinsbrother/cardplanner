package uk.co.itstherules.yawf.inbound;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import uk.co.itstherules.yawf.model.NullFileItem;

public final class MultipartRequestValuesProvider extends AbstractValuesProvider {

	private final Map<String, String> keyValuePairs;
	private final LinkedHashMap<String, FileItem> fileItems;

	@SuppressWarnings("unchecked")
    public MultipartRequestValuesProvider(HttpServletRequest request, ValuesProvider delegate) {
    	super(delegate);
		this.keyValuePairs = new LinkedHashMap<String, String>();
		this.fileItems = new LinkedHashMap<String, FileItem>();
		Long maxSize = getLong("maxUploadSize", Long.valueOf(0));
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		DiskFileItemFactory factory = new DiskFileItemFactory(maxSize.intValue(), tempDirectory);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxSize);
		try {
	        List<FileItem> items = upload.parseRequest(request);
	        for (FileItem item : items) {
	        	if (item.isFormField()) {
	        		this.keyValuePairs.put(item.getFieldName(), item.getString());
	        	} else {
	        		this.fileItems.put(item.getFieldName(), item);
	        	}
            }
        } catch (FileUploadException e) {
	        throw new RuntimeException(e);
        }
	}

	@Override
    public FileItem getFileItem(String key) {
		FileItem fileItem = this.fileItems.get(key);
		if(fileItem != null) return fileItem;
		return new NullFileItem();
    }

	@Override
    public Set<String> getKeys() {
	    TreeSet<String> set = new TreeSet<String>();
	    set.addAll(this.delegate.getKeys());
	    set.addAll(this.fileItems.keySet());
	    set.addAll(this.keyValuePairs.keySet());
		return set;
    }

	@Override
    public String getString(String key, String defaultString) {
		String reply = String.class.cast(this.keyValuePairs.get(key));
		if(reply != null && !"".equals(reply)) { return reply; }
		return this.delegate.getString(key, defaultString);
    }
}

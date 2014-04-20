package uk.co.itstherules.yawf.inbound;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import uk.co.itstherules.string.manipulation.CollectionPrinter;

public final class SinglePartRequestValuesProvider extends AbstractValuesProvider {

    private final HttpServletRequest request;

	public SinglePartRequestValuesProvider(HttpServletRequest request, ValuesProvider delegate) {
    	super(delegate);
		this.request = request;
	}

	@Override
    public Set<String> getKeys() {
	    TreeSet<String> set = new TreeSet<String>();
	    set.addAll(this.delegate.getKeys());
	    set.addAll(this.request.getParameterMap().keySet());
		return set;
    }

	@Override
    public String getString(String key, String defaultString) {
	    String[] values = this.request.getParameterValues(key);
	    if(values != null && values.length > 0) {
	    	return CollectionPrinter.print(Arrays.asList(values));
	    }
		return this.delegate.getString(key, defaultString);
    }

}

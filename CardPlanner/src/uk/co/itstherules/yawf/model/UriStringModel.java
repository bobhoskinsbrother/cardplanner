package uk.co.itstherules.yawf.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class UriStringModel implements UriExecutor {
	
	private final String string;

	public UriStringModel(UriModel uriModel) {
		Assertion.checkIsSame(UriModel.Scheme.string, uriModel.getScheme(), "UriStringModel can only handle a string uri");
		string = uriModel.getPath(0);
    }

	@Override public Object execute(ObjectCache objectCache) { 
		try {
		    return URLDecoder.decode(string, "UTF8");
	    } catch (UnsupportedEncodingException e) {
		    throw new RuntimeException(e);
	    } 
    }
	
}

package uk.co.itstherules.yawf.inbound;

import javax.servlet.http.HttpServletRequest;

public class RequestValuesProviderFactory {
	
	public ValuesProvider create(HttpServletRequest request, ValuesProvider delegate) {
		if (isMulti(request)) { return createMulti(request, delegate); }
		return createSingle(request, delegate);
	}
	
	private boolean isMulti(HttpServletRequest request) {
		if (!"post".equals(request.getMethod().toLowerCase())) { return false; }
		String contentType = request.getContentType();
		if (contentType == null) { return false; }
		return contentType.toLowerCase().startsWith("multipart/");
	}

	SinglePartRequestValuesProvider createSingle(HttpServletRequest request, ValuesProvider delegate) {
	    return new SinglePartRequestValuesProvider(request, delegate);
    }

	MultipartRequestValuesProvider createMulti(HttpServletRequest request, ValuesProvider delegate) {
	    return new MultipartRequestValuesProvider(request, delegate);
    }

}

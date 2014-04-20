package uk.co.itstherules.yawf;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Locale;


public class FakeResponse implements HttpServletResponse {
	
	private final Writer writer;
	private String contentType;

	public FakeResponse(Writer writer) {
		this.writer = writer;
	}
	
	public void addCookie(Cookie arg0) {
		throw new RuntimeException("TODO");
	}
	
	public void addDateHeader(String arg0, long arg1) {
		throw new RuntimeException("TODO");
	}
	
	public void addHeader(String arg0, String arg1) {
		throw new RuntimeException("TODO");
	}
	
	public void addIntHeader(String arg0, int arg1) {
		throw new RuntimeException("TODO");
	}
	
	public boolean containsHeader(String arg0) {
		throw new RuntimeException("TODO");
	}
	
	public String encodeRedirectURL(String arg0) {
		return arg0;
	}
	
	public String encodeRedirectUrl(String arg0) {
		throw new RuntimeException("TODO");
	}
	
	public String encodeURL(String arg0) {
		throw new RuntimeException("TODO");
	}
	
	public String encodeUrl(String arg0) {
		throw new RuntimeException("TODO");
	}
	
	public void sendError(int arg0) throws IOException {
		throw new RuntimeException("TODO");
	}
	
	public void sendError(int arg0, String arg1) throws IOException {
		throw new RuntimeException("TODO");
	}
	
	public void sendRedirect(String arg0) throws IOException {
		writer.write(arg0);
		writer.close();
	}
	
	public void setDateHeader(String arg0, long arg1) {
		throw new RuntimeException("TODO");
	}
	
	public void setHeader(String arg0, String arg1) {
		throw new RuntimeException("TODO");
	}
	
	public void setIntHeader(String arg0, int arg1) {
		throw new RuntimeException("TODO");
	}
	
	public void setStatus(int arg0) {
		throw new RuntimeException("TODO");
	}
	
	public void setStatus(int arg0, String arg1) {
		throw new RuntimeException("TODO");
	}
	
	public void flushBuffer() throws IOException {
		throw new RuntimeException("TODO");
	}
	
	public int getBufferSize() {
		throw new RuntimeException("TODO");
	}
	
	public String getCharacterEncoding() {
		throw new RuntimeException("TODO");
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public Locale getLocale() {
		throw new RuntimeException("TODO");
	}
	
	public ServletOutputStream getOutputStream() throws IOException {
		throw new RuntimeException("TODO");
	}
	
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(writer);
	}
	
	public boolean isCommitted() {
		throw new RuntimeException("TODO");
	}
	
	public void reset() {
		throw new RuntimeException("TODO");
	}
	
	public void resetBuffer() {
		throw new RuntimeException("TODO");
	}
	
	public void setBufferSize(int arg0) {
		throw new RuntimeException("TODO");
	}
	
	public void setCharacterEncoding(String arg0) {
		throw new RuntimeException("TODO");
	}
	
	public void setContentLength(int arg0) {
		throw new RuntimeException("TODO");
	}

    @Override public void setContentLengthLong(long l) {
        throw new UnsupportedOperationException("TODO");
    }

    public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setLocale(Locale arg0) {
		throw new RuntimeException("TODO");
	}

	@Override
    public String getHeader(String arg0) {
	    throw new RuntimeException("getHeader Not Implemented");
    }

	@Override
    public Collection<String> getHeaderNames() {
	    throw new RuntimeException("getHeaderNames Not Implemented");
    }

	@Override
    public Collection<String> getHeaders(String arg0) {
	    throw new RuntimeException("getHeaders Not Implemented");
    }

	@Override
    public int getStatus() {
	    throw new RuntimeException("getStatus Not Implemented");
    }
}

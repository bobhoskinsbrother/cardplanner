package uk.co.itstherules.yawf;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.junit.Test;

public class ErrorHandlerTest {
	
	@Test public void errorMessage() throws IOException, ServletException {
		ErrorHandler unit = new ErrorHandler(new AlwaysThrowsHandler());
		StringWriter reply = new StringWriter();
		unit.handle(null, null, null, new FakeResponse(reply));
		Assert.assertTrue(reply.toString().indexOf("java.lang.RuntimeException: Intentional Throw")>-1);
	}
	
	@Test public void delegatesHandleWhenNoError() throws IOException, ServletException {
		NeverThrowsHandler delegate = new NeverThrowsHandler();
		ErrorHandler unit = new ErrorHandler(delegate);
		unit.handle(null, null, null, null);
		Assert.assertTrue(delegate.isHandleCalled());
	}

}

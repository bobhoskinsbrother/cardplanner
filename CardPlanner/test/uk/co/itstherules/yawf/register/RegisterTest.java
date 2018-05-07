package uk.co.itstherules.yawf.register;

import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.junit.Test;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.register.PackagedClassesAssignableFrom.InternalRegister;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class RegisterTest {
	
	@Test public void canRegisterView() {
		InternalRegister<ModelView> unit = new InternalRegister<ModelView>(null);
		NullView nullView = new NullView();
		unit.register(nullView);
		Assert.assertEquals(1, unit.available().size());
		Assert.assertSame(nullView, unit.get("NullView"));
	}
	
	private static final class NullView implements ModelView {
		@Override public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) { return null; }
		@Override public String getKey() { return "NullView"; }
		@Override public void renderTo(ObjectCache objectCache, ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations) { }
		@Override public String getViewTitle() { return ""; }
	}
}

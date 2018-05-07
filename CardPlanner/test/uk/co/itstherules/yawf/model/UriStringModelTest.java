package uk.co.itstherules.yawf.model;

import org.junit.Assert;
import org.junit.Test;

public class UriStringModelTest {
	
	@Test public void canHandleStringScheme() {
		UriModel uri = new UriModel("string://localhost/Hi+Mom");
		UriStringModel unit = new UriStringModel(uri);
		Assert.assertEquals("Hi Mom", unit.execute(null));
	}
	
	@Test(expected=IllegalArgumentException.class) public void cannotConstructQuery() {
		UriModel uri = new UriModel("query://localhost/test.models.to.instantiate.TestModel/All");
		new UriStringModel(uri);
    }

	@Test public void canHandleNonDomain() {
		UriModel uri = new UriModel("string:///Hi+MoM");
		UriStringModel unit = new UriStringModel(uri);
		Assert.assertEquals("Hi MoM", unit.execute(null));
    }

	@Test(expected=IllegalArgumentException.class) public void cannotHandleNonClass() {
		UriModel uri = new UriModel("query://localhost/uk.co.itstherules.yawf.model.ImNotAClass/All");
		new UriStringModel(uri);
    }
	
}

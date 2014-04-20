package uk.co.itstherules.yawf.model;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.model.UriModel.Scheme;

public class UriModelTest {
	
	@Test public void canConstruct() {
		UriModel unit = new UriModel("query://localhost/uk.co.itstherules.cardplanner.model.PersonModel/All");
		Assert.assertEquals(Scheme.query, unit.getScheme());
		Assert.assertEquals("localhost", unit.getHost());
		Assert.assertEquals("uk.co.itstherules.cardplanner.model.PersonModel", unit.getPath(0));
		Assert.assertEquals("All", unit.getPath(1));
		Assert.assertEquals("", unit.getFragment());
    }

	@Test public void canConstructFull() {
		UriModel unit = new UriModel("query://localhost/uk.co.itstherules.cardplanner.model.PersonModel/All?identity=10&fish=fred#anchor");
		Assert.assertEquals(Scheme.query, unit.getScheme());
		Assert.assertEquals("localhost", unit.getHost());
		Assert.assertEquals("uk.co.itstherules.cardplanner.model.PersonModel", unit.getPath(0));
		Map<String, Object> reply = unit.getQueryString();
		Assert.assertEquals("10", reply.get("identity"));
		Assert.assertEquals("fred", reply.get("fish"));
		Assert.assertEquals("anchor", unit.getFragment());
    }
	
	@Test public void canConstructUsername() {
		UriModel unit = new UriModel("query://ben@localhost/uk.co.itstherules.cardplanner.model.PersonModel/All?identity=10&fish=fred#anchor");
		Assert.assertEquals(Scheme.query, unit.getScheme());
		Assert.assertEquals("localhost", unit.getHost());
		Assert.assertEquals("uk.co.itstherules.cardplanner.model.PersonModel", unit.getPath(0));
		Map<String, Object> reply = unit.getQueryString();
		Assert.assertEquals("10", reply.get("identity"));
		Assert.assertEquals("fred", reply.get("fish"));
		Assert.assertEquals("anchor", unit.getFragment());
		Assert.assertEquals("ben", unit.getUsername());
		Assert.assertEquals("", unit.getPassword());
    }
	

	@Test public void canConstructFullUernamePassword() {
		UriModel unit = new UriModel("query://ben:fredsdead@localhost/uk.co.itstherules.cardplanner.model.PersonModel/All?identity=10&fish=fred#anchor");
		Assert.assertEquals(Scheme.query, unit.getScheme());
		Assert.assertEquals("localhost", unit.getHost());
		Assert.assertEquals("uk.co.itstherules.cardplanner.model.PersonModel", unit.getPath(0));
		Map<String, Object> reply = unit.getQueryString();
		Assert.assertEquals("10", reply.get("identity"));
		Assert.assertEquals("fred", reply.get("fish"));
		Assert.assertEquals("anchor", unit.getFragment());
		Assert.assertEquals("ben", unit.getUsername());
		Assert.assertEquals("fredsdead", unit.getPassword());
    }
	
	@Test public void unknownSchemeUsesQuery() {
		UriModel unit = new UriModel("fairy://localhost/uk.co.itstherules.cardplanner.model.PersonModel/All");
		Assert.assertEquals(Scheme.query, unit.getScheme());
    }

	@Test public void rootRelativeUsesQuery() {
		UriModel unit = new UriModel("/uk.co.itstherules.cardplanner.model.PersonModel/All");
		Assert.assertEquals(Scheme.query, unit.getScheme());
		Assert.assertEquals("uk.co.itstherules.cardplanner.model.PersonModel", unit.getPath(0));
		Assert.assertEquals("All", unit.getPath(1));
    }
}

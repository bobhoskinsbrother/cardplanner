package uk.co.itstherules.yawf.model;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import test.models.to.instantiate.TestModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;

public class UriQueryModelTest {
	
	@Test(expected=IllegalArgumentException.class) public void cannotHandleStringScheme() {
		UriModel uri = new UriModel("string://localhost/BaseApp/test.models.to.instantiate.TestModel/All");
		new UriQueryModel(uri);
	}
	
	@Test public void canConstruct() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/All");
		new UriQueryModel(uri);
    }

	@Test(expected=IllegalArgumentException.class) public void cannotHandleNonEntityClass() {
		UriModel uri = new UriModel("query://localhost/BaseApp/uk.co.itstherules.yawf.model.UriQueryModelTest.ImNotAnEntity/All");
		new UriQueryModel(uri);
    }

	@Test(expected=IllegalArgumentException.class) public void cannotHandleNonClass() {
		UriModel uri = new UriModel("query://localhost/BaseApp/uk.co.itstherules.yawf.model.ImNotAClass/All");
		new UriQueryModel(uri);
    }
	
	@Test public void canConstructAllQueryNoQueryString() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/All");
		UriExecutor unit = new UriQueryModel(uri);
		ObjectCache objectCache = mock(ObjectCache.class);

		unit.execute(objectCache);
		verify(objectCache).all(test.models.to.instantiate.TestModel.class);
    }
	
	@Test public void canConstructAllQuery() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/All?fred=fish");
		UriExecutor unit = new UriQueryModel(uri);
		ObjectCache objectCache = mock(ObjectCache.class);
		unit.execute(objectCache);
		verify(objectCache).all(eq(TestModel.class), isA(QueryConditions.class));
    }
	
	@Test public void canConstructCountQueryNoQueryString() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/Count");
		UriExecutor unit = new UriQueryModel(uri);
		ObjectCache objectCache = mock(ObjectCache.class);
		unit.execute(objectCache);
		verify(objectCache).count(eq(TestModel.class));
    }
	
	@SuppressWarnings("unchecked")
    @Test public void canConstructCountQuery() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/Count?title=Mr");
		UriExecutor unit = new UriQueryModel(uri);
		ObjectCache objectCache = mock(ObjectCache.class);
		unit.execute(objectCache);
		verify(objectCache).count(eq(TestModel.class), anyMap());
    }
	
	@Test public void canConstructRetrieveByIdentityQuery() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/RetrieveByIdentity/101");
		UriExecutor unit = new UriQueryModel(uri);
		ObjectCache objectCache = mock(ObjectCache.class);
		unit.execute(objectCache);
		verify(objectCache).retrieveByIdentity(eq(TestModel.class), eq("101"));
    }

	@Test public void canConstructRetrieveQuery() {
		UriModel uri = new UriModel("query://localhost/BaseApp/test.models.to.instantiate.TestModel/Retrieve?title=Mr");
		UriExecutor unit = new UriQueryModel(uri);
		ObjectCache objectCache = mock(ObjectCache.class);
		unit.execute(objectCache);
		verify(objectCache).retrieve(eq(TestModel.class), eq("title"), eq("Mr"));

    }
		
	public static final class ImNotAnEntity {}
	
}

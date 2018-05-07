package uk.co.itstherules.cardplanner.controller;

import org.junit.Assert;
import org.junit.Test;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class CardPlannerBaseTest {
	
	private class BaseExtension extends CardPlannerBase<IdentityDeleteableImpl> {
		
	    String calledAction;
	    Object calledWith;
		IdentityDeleteableImpl model;

	    @Override public IdentityDeleteableImpl getDefaultModel(ObjectCache objectCache) { 
	    	if(this.model == null) {
	    		this.model = new IdentityDeleteableImpl();
	    	}
			return this.model; 
	    }
	    
	    @Override
	    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, 
	    		String action, IdentityDeleteableImpl object, QueryKeyViolations violations)
	            throws IOException {
	    	calledAction = action;
	    	calledWith = object;
	    }
	    
	    @Override
	    protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, IdentityDeleteableImpl object) throws IOException {
	    	calledAction = action;
	    	calledWith = object;
	    }
	    
	    @Override
	    protected ModelView getTemplate(String text, String controller, String title) {
	        return new NullTemplate();
	    }
    }

	
	private class BaseExtension2 extends CardPlannerBase<IdentityDeleteableImpl> {
		
		IdentityDeleteableImpl model;
		private QueryKeyViolations queryKeyViolations;
		private boolean called = false;
		private boolean changeViewCalled;

		BaseExtension2(QueryKeyViolations queryKeyViolations) {
			this.queryKeyViolations = queryKeyViolations;
        }
		
	    @Override public IdentityDeleteableImpl getDefaultModel(ObjectCache objectCache) { 
	    	if(this.model == null) {
	    		this.model = new IdentityDeleteableImpl();
	    	}
			return this.model; 
	    }
	    
	    public boolean isSendChangeCalled() { return called; }
	    public boolean isChangeViewCalled() { return changeViewCalled; }
	    	    
	    @Override
	    protected ModelView getTemplate(String text, String controller, String title) {
	        return new NullTemplate();
	    }
	    
	    @Override
	    protected QueryKeyViolations bind(ObjectCache objectCache, ValuesProvider provider, IdentityDeleteableImpl object) {
	    	return queryKeyViolations;
	    }
	    
	    @Override
	    protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
	    	this.called  = true;
	    }

		@Override
        protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, IdentityDeleteableImpl object, QueryKeyViolations violations) throws IOException {
			this.changeViewCalled = true;
        }
    }
	
	private final class CardPlannerBaseExtension extends BaseExtension {
		
	    @Override
	    protected IdentityDeleteableImpl identityModelFromCache(ObjectCache objectCache, ValuesProvider provider) { 
			return getDefaultModel(objectCache); 
	    }
    }
	
	
	@Test public void identityModelFromCache() throws Exception {
		BaseExtension unit = new BaseExtension();
		ValuesProvider provider = mock(ValuesProvider.class);
		ObjectCache objectCache = mock(ObjectCache.class);
		when(provider.getIdentity()).thenReturn("1001");
		IdentityDeleteableImpl cachedInstance = new IdentityDeleteableImpl();
		cachedInstance.setIdentity("100");
		when(objectCache.retrieveByIdentity(unit.getDefaultModel(objectCache))).thenReturn(cachedInstance);
		
		unit.identityModelFromCache(objectCache, provider);
		
		verify(provider).getIdentity();
		verify(objectCache).retrieveByIdentity(unit.getDefaultModel(objectCache));
    }

	@Test public void add() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		unit.add(null, null, null, null);
		Assert.assertEquals("Create", unit.calledAction);
		Assert.assertEquals(IdentityDeleteableImpl.class, unit.calledWith.getClass());
    }

	@Test public void edit() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		unit.edit(null, null, null, null);
		Assert.assertEquals("Update", unit.calledAction);
		Assert.assertEquals(IdentityDeleteableImpl.class, unit.calledWith.getClass());
    }
	
	@Test public void create() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		unit.create(null, null, null, null);
		Assert.assertEquals("Create", unit.calledAction);
		Assert.assertEquals(IdentityDeleteableImpl.class, unit.calledWith.getClass());
    }
	
	@Test public void list() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		unit.list(null, null, null, null);
		Assert.assertEquals("Create", unit.calledAction);
		Assert.assertEquals(IdentityDeleteableImpl.class, unit.calledWith.getClass());
    }
	
	
	@Test public void show() throws Exception {
		ValuesProvider provider = mock(ValuesProvider.class);
		ObjectCache objectCache = mock(ObjectCache.class);
		when(provider.getIdentity()).thenReturn("1001");
		IdentityDeleteableImpl cachedInstance = new IdentityDeleteableImpl();
		cachedInstance.setIdentity("100");
		
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();

		when(objectCache.retrieveByIdentity(unit.getDefaultModel(objectCache))).thenReturn(cachedInstance);
		
		unit.show(objectCache, provider, null, null);
    }

	@Test public void update() throws Exception {
		ObjectCache objectCache = mock(ObjectCache.class);
		when(objectCache.retrieveByIdentity(new IdentityDeleteableImpl())).thenReturn(new IdentityDeleteableImpl());
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		ValuesProvider valuesProvider = new MapValuesProvider(Collections.<String, Object>singletonMap("identity", "1"));
		unit.update(objectCache, valuesProvider, null, null);
		Assert.assertEquals("Update", unit.calledAction);
		Assert.assertEquals(IdentityDeleteableImpl.class, unit.calledWith.getClass());
    }
	
	@Test public void sendChangeActionRedirect() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ValuesProvider.ROOT, "/Oioi");
		ValuesProvider provider = new MapValuesProvider(map);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.encodeRedirectURL("/Oioi/CardPlannerBaseExtension/List/0/")).thenReturn("/Oioi/CardPlannerBaseExtension/List/0/");
		unit.sendChangeActionRedirect(provider, response);
		verify(response).sendRedirect("/Oioi/CardPlannerBaseExtension/List/0/");
    }
	
	@Test public void delete() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ValuesProvider.ROOT, "/Oioi");
		ValuesProvider provider = new MapValuesProvider(map);
		ObjectCache objectCache = mock(ObjectCache.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.encodeRedirectURL("/Oioi/CardPlannerBaseExtension/List/0/")).thenReturn("/Oioi/CardPlannerBaseExtension/List/0/");
		
		unit.delete(objectCache, provider, response, null);
		
		verify(objectCache).delete(unit.identityModelFromCache(null, null));
		verify(response).sendRedirect("/Oioi/CardPlannerBaseExtension/List/0/");
    }

	
	@Test(expected=RuntimeException.class)
	public void deleteThrowsOnError() throws Exception {
		CardPlannerBaseExtension unit = new CardPlannerBaseExtension();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ValuesProvider.ROOT, "/Oioi");
		ValuesProvider provider = new MapValuesProvider(map);
		ObjectCache objectCache = mock(ObjectCache.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.encodeRedirectURL("/Oioi/CardPlannerBaseExtension/List/0/")).thenReturn("/Oioi/CardPlannerBaseExtension/List/0/");
		doThrow(new IOException()).when(response).sendRedirect("/Oioi/CardPlannerBaseExtension/List/0/");
		
		unit.delete(objectCache, provider, response, null);
    }
	
	@Test public void successfulChange() throws Exception {
		ObjectCache objectCache = mock(ObjectCache.class);
		IdentityDeleteableImpl object = new IdentityDeleteableImpl();
		

		BaseExtension2 unit = new BaseExtension2(new QueryKeyViolations());
		unit.changeAction(objectCache, null, null, null, null, object);
		
		verify(objectCache).save(object);
		
		Assert.assertTrue(unit.isSendChangeCalled());
	}
}
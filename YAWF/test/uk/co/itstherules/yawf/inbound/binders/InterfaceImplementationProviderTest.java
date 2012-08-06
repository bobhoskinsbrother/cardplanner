package uk.co.itstherules.yawf.inbound.binders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.AliasImplementationProvider;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.InstanceDecisionMaker;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class InterfaceImplementationProviderTest {
	
    @SuppressWarnings("rawtypes")
	@Test public void canReturnInstanceForInterface() {
		AliasImplementationProvider<List> unit = new AliasImplementationProvider<List>(List.class, ArrayList.class);
		Assert.assertTrue(unit.canHandle(List.class, null, null));
		List reply = unit.provide(null, null);
		Assert.assertTrue(ArrayList.class.isInstance(reply));
	}
	
    @SuppressWarnings("rawtypes")
	@Test public void cannotHandleWithSimpleWrongClass() {
		AliasImplementationProvider<List> unit = new AliasImplementationProvider<List>(List.class, ArrayList.class);
		Assert.assertFalse(unit.canHandle(Set.class, null, null));
	}
	
    @SuppressWarnings("rawtypes")
	@Test public void cannotHandleWithHarshDecision() {
		AliasImplementationProvider<List> unit = new AliasImplementationProvider<List>(List.class, ArrayList.class, new InstanceDecisionMaker() {
			@Override public boolean shouldInstantiate(ObjectCache objectCache, ValuesProvider provider) {
				return false;
			}
		});
		Assert.assertFalse(unit.canHandle(List.class, null, null));
	}
	
    @SuppressWarnings("rawtypes")
	@Test public void cannotHandleWithWrongClass() {
		AliasImplementationProvider<List> unit = new AliasImplementationProvider<List>(List.class, ArrayList.class, new InstanceDecisionMaker() {
			@Override public boolean shouldInstantiate(ObjectCache objectCache, ValuesProvider provider) {
				return false;
			}
		});
		Assert.assertFalse(unit.canHandle(Set.class, null, null));
	}
	
	@SuppressWarnings("rawtypes")
	@Test public void cannotHandleWithWrongClassRightDecision() {
		AliasImplementationProvider<List> unit = new AliasImplementationProvider<List>(List.class, ArrayList.class, new InstanceDecisionMaker() {
			@Override public boolean shouldInstantiate(ObjectCache objectCache, ValuesProvider provider) {
				return true;
			}
		});
		Assert.assertFalse(unit.canHandle(Set.class, null, null));
	}
	
}

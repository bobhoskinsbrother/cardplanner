package uk.co.itstherules.yawf.inbound.annotations.processor;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class BasicValuesProviderBinderForCachingTest {

	@Test public void canFindInCacheForOneFieldOnCachedStringFieldModel() {
		DelegateCachedStringFieldModel replyFromCache = new DelegateCachedStringFieldModel();
		ObjectCache mockObjectCache = mock(ObjectCache.class);
		stub(mockObjectCache.retrieveByIdentity(eq(DelegateCachedStringFieldModel.class), eq("IAmANumberNotAMan"))).toReturn(replyFromCache);
		
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		CachedStringFieldModel model = new CachedStringFieldModel();
		ValuesProvider provider = new MapValuesProvider(Collections.<String, Object>singletonMap("delegate.identity", "IAmANumberNotAMan"));
		QueryKeyViolations violations = unit.bind(provider, model, mockObjectCache);
		Assert.assertFalse(violations.isRegistered());
		
		verify(mockObjectCache).retrieveByIdentity(eq(DelegateCachedStringFieldModel.class), eq("IAmANumberNotAMan"));
		Assert.assertThat(model.getDelegate(), sameInstance(replyFromCache));
    }

}

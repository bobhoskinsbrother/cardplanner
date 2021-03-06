package uk.co.itstherules.yawf.model.persistence;

import static org.hamcrest.CoreMatchers.is;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

public class AlwaysThrowsObjectCacheTest {


	@Test
	public void everyMethodThrowsIllegalState() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AlwaysThrowsObjectCache unit = new AlwaysThrowsObjectCache();
		Method[] declaredMethods = AlwaysThrowsObjectCache.class.getDeclaredMethods();
		for (Method method : declaredMethods) {
			Class<?>[] parameterTypes = method.getParameterTypes();
			Object[] args = new Object[parameterTypes.length];
			try {
				method.invoke(unit, args);
				Assert.fail("Every public method should throw on this class");
			} catch(InvocationTargetException e) {
				Assert.assertThat(e.getTargetException().getMessage(), is("Programmatic error; ObjectCache has been set to not required, and is being called"));
			}
		}
		
	}
}

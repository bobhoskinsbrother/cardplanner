package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Collections;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.constraint.AssertValidCheck;
import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;


public class UniqueStringFieldBinder implements QueryValueBinder {
	
	public UniqueStringFieldBinder() {  }

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		if(!IdentityDeleteable.class.isInstance(model)) {
			throw new IllegalArgumentException("UniqueFieldBinder can only be used on objects that implement IdentityDeleteable");
		}
		QueryKey annotation = field.getAnnotation(QueryKey.class);
		Assertion.checkNotNull(annotation, "UniqueFieldBinder: expected field to have a QueryKey annotation");
		String key = annotation.value();
		String value = provider.getString(key);
		BigInteger count = objectCache.count(model.getClass(), Collections.<String, Object>singletonMap(key, value), ObjectState.Active, ObjectState.Pending);
		if(count.intValue() > 0) {
			violations.add(key, Collections.singletonList(new ConstraintViolation(new AssertValidCheck(), "This " + key + " already exists", model, value, null)));
		}
		Fields.set(model, field, value);
		violations.add(fullQueryKey, new Validator().validateFieldValue(model, field, value));

    }

	public boolean canHandle(Class<?> fieldClass, Type genericType) {
	    return true;
    }

}
package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.sf.oval.Validator;

import org.apache.shiro.crypto.hash.Sha256Hash;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class PasswordBinder implements QueryValueBinder {

	public void bind(Object model, Field field, String fullQueryKey, String currentQueryKey, ValuesProvider provider, ObjectCache objectCache, QueryKeyViolations violations) {
		String plainTextPassword = provider.getString(fullQueryKey);
		String hashedPasswordBase64 = new Sha256Hash(plainTextPassword).toHex();
		Fields.set(model, field, hashedPasswordBase64);
		violations.add(fullQueryKey, new Validator().validateFieldValue(model, field, hashedPasswordBase64));
    }

	public boolean canHandle(Class<?> fieldClass, Type genericType) {
	    return true;
    }
}

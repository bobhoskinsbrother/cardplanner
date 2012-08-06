package uk.co.itstherules.cardplanner.controller.processor.active;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.IdentityDeleteable;
import uk.co.itstherules.yawf.model.UriModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.ObjectCacheActionNotAuthorized;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public final class AllQueryProcessStep extends BaseProcessStep<Set<? extends IdentityDeleteable<?>>> {
	
	public static final String KEY = "AllQuery";

    public Set<? extends IdentityDeleteable<?>> process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object uriObject = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	Assertion.checkIsInstance(String.class, uriObject);
		String uri = String.class.cast(uriObject);
		
		UriModel uriModel = new UriModel(uri);
		String className = uriModel.getPath(0);
		Map<String, Object> queryString = uriModel.getQueryString();
		try {
	        @SuppressWarnings("unchecked")
			Class<? extends IdentityDeleteable<?>> classToFind = (Class<? extends IdentityDeleteable<?>>) Class.forName(className).asSubclass(IdentityDeleteable.class);
    		Set<? extends IdentityDeleteable<?>> all = objectCache.all(classToFind, new QueryConditions("AND").putAll(queryString));
			return all;
        } catch (ObjectCacheActionNotAuthorized e) {
	        throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
	        throw new RuntimeException(e);
        }
    }

	public String getKey() {
		return KEY;
	}

	@Override public ClassDescriptionModel[] getInputs() { return new ClassDescriptionModel[] { DescribeClass.is(String.class) }; }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(List.class, DescribeClass.generics(IdentityDeleteable.class)); }

	@Override
    public ProcessStepType getProcessStepType() {
	    return ProcessStepType.MODULE_CALL;
    }

}
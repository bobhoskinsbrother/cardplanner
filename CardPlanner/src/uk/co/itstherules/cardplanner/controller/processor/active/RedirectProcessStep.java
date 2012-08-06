package uk.co.itstherules.cardplanner.controller.processor.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.assertion.Assertion;
import uk.co.itstherules.yawf.controller.processor.BaseProcessStep;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.DescribeClass;
import uk.co.itstherules.yawf.model.NullEntity;
import uk.co.itstherules.yawf.model.RouteModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.helper.UrlBuilder;


public final class RedirectProcessStep extends BaseProcessStep<NullEntity> {

	public static final String KEY = "Redirect";

	public NullEntity process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) {
    	Object routeObject = processFirstInboundStep(objectCache, provider, response, viewFactory, violations);
    	Assertion.checkIsInstance(RouteModel.class, routeObject);
		RouteModel route = RouteModel.class.cast(routeObject);

		Assertion.checkNotInvisibleObject(route, "When using the Redirect process step, the Route cannot be a nullobject");
		String url = new UrlBuilder(provider.getApplicationRoot()).url(route.getController(), route.getAction(), route.getIdentity(), route.getTitle());
	    try {
	        response.sendRedirect(response.encodeRedirectURL(url));
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
        return new NullEntity();
    }

	public String getKey() { return KEY; }

	@Override public ProcessStepType getProcessStepType() {
	    return ProcessStepType.TERMINAL_END;
    }

	@Override public ClassDescriptionModel[] getInputs() { return DescribeClass.are(RouteModel.class); }
	@Override public ClassDescriptionModel getOutput() { return DescribeClass.is(NullEntity.class); }


}

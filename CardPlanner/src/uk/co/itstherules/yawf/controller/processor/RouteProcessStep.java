package uk.co.itstherules.yawf.controller.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.ClassDescriptionModel;
import uk.co.itstherules.yawf.model.NullEntity;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.RouteModel;
import uk.co.itstherules.yawf.model.comparator.IdentityLengthComparator;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;

public final class RouteProcessStep extends BaseProcessStep<NullEntity> {

	@Override public NullEntity process(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, QueryKeyViolations violations) throws QueryKeyViolationsException {
		QueryConditions andConditions = new QueryConditions("AND").put("routeController", provider.getController()).put("routeAction", provider.getAction());
		QueryConditions orConditions = new QueryConditions("OR").put("routeIdentity", provider.getIdentity()).put("routeIdentity", "0");
		
		List<RouteModel> allRoutes = new ArrayList<RouteModel>(objectCache.<RouteModel>all(RouteModel.class, andConditions, orConditions, ObjectState.Active));
		Collections.sort(allRoutes, new IdentityLengthComparator());
		if(allRoutes.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format("Cannot find a route for controller: {0} and action: {1}", provider.getController(), provider.getAction()));
		}
		RouteModel route = allRoutes.get(0);
		new ProcessStepConnectorPlanExecutor(route.getRouteProcessStep()).process(objectCache, provider, response, viewFactory, violations);
		return new NullEntity();
    }

	@Override
    public String getKey() {
	    return "RouteProcessor";
    }


	@Override public ProcessStepType getProcessStepType() {
	    return ProcessStepType.TERMINAL_END;
    }

	@Override public ClassDescriptionModel[] getInputs() { return new ClassDescriptionModel[]{}; }
	@Override public ClassDescriptionModel getOutput() { return new ClassDescriptionModel(NullEntity.class); }

}

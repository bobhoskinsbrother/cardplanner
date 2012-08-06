package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.yawf.controller.processor.RouteProcessStep;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolationsException;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;

public final class SignUp extends CardPlannerBase<PersonModel> {
	
	@Action("Show") @Override public void show(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		PageModel page = SpecialInstances.retrieve(objectCache, Identities.SIGN_UP_SHOW_PAGE);
		new PageModelView(page, viewFactory).renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Override protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, PersonModel object, QueryKeyViolations violations) throws IOException {
		PageModel page = SpecialInstances.retrieve(objectCache, Identities.SIGN_UP_ADD_PAGE);
		new PageModelView(page, viewFactory).renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }
	
	@Override protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, PersonModel person) throws IOException {
		try {
			new RouteProcessStep().process(objectCache, provider, response, viewFactory, new QueryKeyViolations());
		} catch (QueryKeyViolationsException e) {
			changeView(objectCache, provider, response, viewFactory, action, person, e.getViolations());
		}
    }

	@Override public PersonModel getDefaultModel(ObjectCache objectCache) {
		return new PersonModel().defaultSetup(objectCache);
    }

}
package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.PageModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.modelview.PageModelView;
import uk.co.itstherules.yawf.view.context.EmptyContext;

public final class SignUpConfirm extends BaseController {

	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<PersonModel> people = objectCache.all(PersonModel.class, "identity", provider.getIdentity(), ObjectState.Pending);
		if(people.size()==1) {
			PersonModel person = people.iterator().next();
			person.activate();
			objectCache.save(person);
		}
		PageModel pageModel = SpecialInstances.<PageModel>retrieve(objectCache, Identities.SIGN_UP_CONFIRM_SHOW_PAGE);
		new PageModelView(pageModel, viewFactory).renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

}

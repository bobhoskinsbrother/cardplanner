package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.json.JsonView;


public final class PeopleJson extends BaseController {

	@Action("Update")
    public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		PersonModel person = objectCache.retrieveByIdentity(PersonModel.class, provider.getIdentity(), ObjectState.Pending, ObjectState.Active);
		if(person==null) {
			new JsonView(Collections.singletonMap("error", "Person cannot be found")).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
		}
		if(provider.getBoolean("activate", Boolean.FALSE).equals(Boolean.TRUE)) { 
			person.activate(); 
			objectCache.save(person);
			new JsonView(Collections.singletonMap("identity", person.getIdentity())).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
		} else {
			new JsonView(Collections.singletonMap("error", "Person not activated")).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
		}
    }
}

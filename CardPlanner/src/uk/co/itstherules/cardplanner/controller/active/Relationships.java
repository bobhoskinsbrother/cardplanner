package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.LogModel;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.json.JsonView;


public final class Relationships extends BaseController {

	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
		QueryKeyViolations reply = new BasicValuesProviderBinder().bind(provider, card, objectCache);
		if(reply.isRegistered()) {
			throw new RuntimeException();
		}
		LogModel log = new LogModel(card, card.getStatus(), card.getStatus(), "Update");
		objectCache.save(card);
		objectCache.save(log);
        String serializedCard = SerializeModel.card(card);
		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
		new JsonView(Collections.singletonMap("identity", card.getIdentity())).renderTo(new EmptyContext(), response, "");
	}

	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }

}
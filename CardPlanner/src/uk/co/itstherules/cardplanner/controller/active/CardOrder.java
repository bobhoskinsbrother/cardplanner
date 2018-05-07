package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.json.JsonView;


public final class CardOrder extends BaseController {
	
	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Map<String, String> statuses = provider.getMap(ProviderKey.cards.name(), new HashMap<String,String>());
		for (Entry<String, String> entry : statuses.entrySet()) {
			CardModel card = objectCache.retrieveByIdentity(CardModel.class, entry.getKey());
	        card.setSortOrder(Integer.parseInt(entry.getValue()));
	        objectCache.save(card);
		    SharedObjectSpaceClient client = SharedObjectSpacesListener.reAttachClient(provider.getString("clientIdentity"));
            String serializedCard = new Json<Object>().serialize(card, "model", "*.people", "*.facts", "*.tags");
			client.shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
        }
		StatusModel backlog = SpecialInstances.retrieve(objectCache, Identities.THE_BACKLOG);
		Set<CardModel> cards = objectCache.all(CardModel.class, "status.identity", backlog.getIdentity());
		response.setContentType(ContentType.json.toString());
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("cards", cards);
		new JsonView(objects).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
	}

}
package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.business.Archiver;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.json.JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



public final class Archived extends CardPlannerBase<CardModel> {

	@Override public CardModel getDefaultModel(ObjectCache objectCache) { return new CardModel().defaultSetup(objectCache); }
	
	@Action("Update") @Override public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<CardModel> all = objectCache.all(CardModel.class, "identity", provider.getIdentity());
    	if(provider.getBoolean("archive", Boolean.FALSE)) {
    		for (CardModel model : all) {
    			new Archiver().archive(objectCache, model);
            }
    	} else {
    		for (CardModel model : all) {
    			new Archiver().deArchive(objectCache, model);
            }
    	}
    	try {
    		response.sendRedirect(response.encodeRedirectURL(provider.getString("redirect")));
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
	}
	
	@Override
	@Action("Feed") public void feed(ObjectCache cache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
        Set<CardModel> cards = cache.all(CardModel.class, ObjectState.Archived);
        Map<String, Object> objects = new HashMap<String, Object>();
        objects.put("cards", cards);
        new JsonView(objects, "people", "facts", "tags").renderTo(new EmptyContext(), response, provider.getApplicationRoot());
    }

    @Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		viewFactory.get("ListArchived").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CardModel object, QueryKeyViolations violations) throws IOException { }


}

package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.business.Changer;
import uk.co.itstherules.cardplanner.model.business.Deleter;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.model.persistence.QueryConditions.Operator;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.json.JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public final class CardPeople extends CardPlannerBase<CardModel> {

	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = identityModelFromCache(objectCache, provider);
		PersonModel selectedPerson = objectCache.retrieveByIdentity(PersonModel.class, provider.getString("personIdentity"));
		new Changer().addPersonToCard(objectCache, card, selectedPerson);
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("person", selectedPerson);
        String serializedCard = SerializeModel.card(card);
		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
		new JsonView(objects).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
	}
	
	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = identityModelFromCache(objectCache, provider);
		PersonModel selectedPerson = objectCache.retrieveByIdentity(PersonModel.class, provider.getString("personIdentity"));
		new Deleter().delete(objectCache, card, selectedPerson);
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("person", selectedPerson);
		new JsonView(objects).renderTo(new EmptyContext(), response, provider.getApplicationRoot());
        String serializedCard = SerializeModel.card(card);
		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.DELETE, serializedCard));
	}
	
	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CardModel
    object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("cardpeople/list.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, object);
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
		QueryConditions conditions = new QueryConditions("AND").put("visible", true).put("identity", Operator.NotEquals, Identities.INVISIBLE_PERSON.getIdentity());
		Set<PersonModel> people = objectCache.all(PersonModel.class, conditions);
		String root = provider.getApplicationRoot();
		context.put("card", card);
		context.put("person", object);
		context.put("people", people);
		getPop(view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

	@Override
    public CardModel getDefaultModel(ObjectCache objectCache) {
		return new CardModel().defaultSetup(objectCache);
    }

	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }


}

package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.*;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.persistence.QueryConditions;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.json.JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;


public final class MyCards extends BaseController {

	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<CardTypeModel> types = objectCache.all(CardTypeModel.class);
		String personIdentity = provider.getIdentity();
		PersonModel person = null;
		Set<PersonModel> people = objectCache.all(PersonModel.class, "visible", true);
		PersonModel invisiblePerson = SpecialInstances.retrieve(objectCache, CachedInstance.Identities.INVISIBLE_PERSON);
		people.remove(invisiblePerson);
		if(!personIdentity.equals("0")){
			person = objectCache.retrieveByIdentity(PersonModel.class, personIdentity);
		} else {
			if(people.size()>0)	{ person = people.iterator().next(); }
			else { person = invisiblePerson; }
		}
		
		Set<CardModel> cards = person.getCards();
		ValueEffortCalculator valueEffortCalculator = new ValueEffortCalculator();

		Set<StatusModel> statuses = objectCache.all(StatusModel.class, new QueryConditions("AND").put("identity", QueryConditions.Operator.NotEquals, CachedInstance.Identities.THE_BACKLOG.getIdentity()));
		EffortModel effort = valueEffortCalculator.calculateEffort(cards, SpecialInstances.<EffortTypeModel>retrieve(objectCache, CachedInstance.Identities.IDEAL_DAY_EFFORT_TYPE));
		View view = new MergedTextView("mycards/list.freemarker");
		ViewContext context = new EmptyContext();
		context.put("person",  person);
		context.put("people",  people);
		context.put("cards",  cards);
		context.put("statuses",  statuses);
		context.put("types",  types);
		context.put("effort",  effort);
		String root = provider.getApplicationRoot();
		context.put(ProviderKey.root.name(), root);
		
		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}

	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
        StatusModel fromStatus = card.getStatus();
        StatusModel toStatus = objectCache.retrieveByIdentity(StatusModel.class, provider.getString("status.identity"));
		card.setStatus(toStatus);
		objectCache.save(card);
        String serializedCard = SerializeModel.card(card);
		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
		LogModel log = new LogModel(card, fromStatus, toStatus, "Update");
		objectCache.save(log);
		new JsonView(Collections.singletonMap("identity", card.getIdentity())).renderTo(new EmptyContext(), response, "");
    }
	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }


}

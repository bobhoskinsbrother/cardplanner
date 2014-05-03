package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.*;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.controller.BaseController;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.json.JsonView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


public final class SignUpForWork extends BaseController {

	@Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<CardTypeModel> types = objectCache.all(CardTypeModel.class);
        String identity = provider.getIdentity();
        if("0".equals(identity)) { identity = Identities.INVISIBLE_CARD.toString(); }
        Set<CardModel> cards = new CardService().children(objectCache, identity);
		Set<PersonModel> people = new PeopleService().allVisible(objectCache);
		View view = new MergedTextView("signupforwork/list.freemarker");
		ViewContext context = new EmptyContext();
		context.put("people", people);
		context.put("types", types);
		context.put("cards", cards);

		String root = provider.getApplicationRoot();
		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
    }

	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
		PersonModel person = objectCache.retrieveByIdentity(PersonModel.class, provider.getString("person.identity"));
		Set<PersonModel> people = new LinkedHashSet<PersonModel>();
		people.addAll(card.getPeople());
		people.add(person);
		card.setPeople(people);
		
		objectCache.save(card);
        String serializedCard = SerializeModel.card(card);
		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
		new JsonView(Collections.singletonMap("identity", card.getIdentity())).renderTo(new EmptyContext(), response, "");
    }

	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
		PersonModel person = objectCache.retrieveByIdentity(PersonModel.class, provider.getString("person.identity"));
		Set<PersonModel> people = new LinkedHashSet<PersonModel>();
		people.addAll(card.getPeople());
		people.remove(person);
		card.setPeople(people);
		objectCache.save(card);
        String serializedCard = SerializeModel.card(card);
		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.DELETE, serializedCard));
		new JsonView(Collections.singletonMap("identity", card.getIdentity())).renderTo(new EmptyContext(), response, "");
    }

	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }

}

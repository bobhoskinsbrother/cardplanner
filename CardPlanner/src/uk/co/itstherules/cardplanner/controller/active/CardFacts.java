package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CardFactModel;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.business.Changer;
import uk.co.itstherules.cardplanner.model.business.Deleter;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import freemarker.template.SimpleHash;

public class CardFacts extends CardPlannerBase<CardFactModel> {

	@Override
	public CardFactModel getDefaultModel(ObjectCache objectCache) {
		return new CardFactModel().defaultSetup(objectCache);
	}

    @Override
    @Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	try {
    		CardFactModel fact = identityModelFromCache(objectCache, provider);
            Set<CardModel> cards = objectCache.contains(CardModel.class, "facts", fact);
            Iterator<CardModel> iterator = cards.iterator();
            CardModel card = iterator.next();
    		new Deleter().delete(objectCache, card, fact);
            String serializedCard = new Json<Object>().serialize(card, "model", "*.people", "*.facts", "*.tags");
    		getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
			String root = provider.getApplicationRoot();
			response.sendRedirect(response.encodeRedirectURL(listUrl("index.xhtml", "0", root, new SimpleHash(Collections.singletonMap("card.identity", provider.getString("card.identity"))))));
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }
	
	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CardFactModel object, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("cardfacts/list.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, object);

		String cardIdentityKey = "card.identity";
		String cardIdentity = provider.getString(cardIdentityKey);
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, cardIdentity);
		Set<CardFactModel> facts = card.getFacts();
		if(facts==null) { facts = new LinkedHashSet<CardFactModel>(); }
		String root = provider.getApplicationRoot();
		
		context.put("card", card);
		context.put("fact", object);
		context.put("facts", facts);
		getPop(view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }
	
	@Override
	protected void changeAction(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CardFactModel fact) throws IOException {
		QueryKeyViolations violations = bind(objectCache, provider, fact);
		String root = provider.getApplicationRoot();
		if(!violations.isRegistered()) {
			CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getString("card.identity"));
			new Changer().change(objectCache, card, fact);
			String cardIdentity = provider.getString("card.identity", "0");
            String serializedCard = SerializeModel.card(card);
			getClient(provider.getString("clientIdentity")).shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
			response.sendRedirect(response.encodeRedirectURL(listUrl("index.xhtml", "0", root, new SimpleHash(Collections.singletonMap("card.identity", cardIdentity)))));
		} else {
			changeView(objectCache, provider, response, viewFactory, action, fact, violations);
		}
	}
	
	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }

}

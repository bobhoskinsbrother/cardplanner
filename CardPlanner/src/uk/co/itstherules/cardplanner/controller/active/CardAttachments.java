package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.controller.shared.SharedObject;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpaceClient;
import uk.co.itstherules.cardplanner.controller.shared.SharedObjectSpacesListener;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.business.Changer;
import uk.co.itstherules.cardplanner.model.business.Deleter;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.SerializeModel;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;
import uk.co.itstherules.yawf.view.json.JsonView;


public final class CardAttachments extends CardPlannerBase<SimpleAttachmentModel> {

	@Override
	@Action("Add") public void add(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		SimpleAttachmentModel attachment = new SimpleAttachmentModel();
		String cardIdentity = provider.getString("cardIdentity", "0");
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, cardIdentity);
		QueryKeyViolations violations = new BasicValuesProviderBinder().bind(provider, attachment, objectCache);
		if(!violations.isRegistered()) {
			objectCache.saveExternal(attachment);
			new Changer().addAttachmentToCard(objectCache, card, attachment);
			response.sendRedirect(response.encodeRedirectURL(listUrl(provider.getTitle(), cardIdentity, provider.getApplicationRoot())));
		} else {
			changeView(objectCache, provider, response, viewFactory, "List", attachment, violations);
		}
	}
	
	@Action("Update") public void update(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
		String attachmentIdentity = provider.getString("attachment.identity");
		SimpleAttachmentModel selectedAttachment = objectCache.retrieveByIdentity(SimpleAttachmentModel.class, attachmentIdentity);
		new Changer().addAttachmentToCard(objectCache, card, selectedAttachment);
		new JsonView(Collections.singletonMap("attachment", selectedAttachment)).renderTo(new EmptyContext(),response,  provider.getApplicationRoot());
		SharedObjectSpaceClient client = getClient(provider.getString("clientIdentity"));
        String serializedCard = SerializeModel.card(card);
		client.shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
	}
	
	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String attachmentIdentity = provider.getString("attachment.identity");
		SimpleAttachmentModel selectedAttachment = objectCache.retrieveByIdentity(SimpleAttachmentModel.class, attachmentIdentity);
		Set<CardModel> cards = objectCache.contains(CardModel.class, "attachments", selectedAttachment);
		for (CardModel card : cards) {
			new Deleter().delete(objectCache, card, selectedAttachment);
			SharedObjectSpaceClient client = getClient(provider.getString("clientIdentity"));
			String serializedCard = SerializeModel.card(card);
			client.shareObject(CardModel.class, new SharedObject(SharedObject.Action.UPDATE, serializedCard));
		}
		new JsonView(Collections.singletonMap("attachment", selectedAttachment)).renderTo(new EmptyContext(), response,  provider.getApplicationRoot());
	}
	
	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, SimpleAttachmentModel attachment, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("cardattachments/list.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, attachment);

		CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity());
		Set<SimpleAttachmentModel> attachments = objectCache.all(SimpleAttachmentModel.class);
		attachments.removeAll(card.getAttachments());
		String root = provider.getApplicationRoot();
		
		context.put("card", card);
		context.put("attachment", attachment);
		context.put("attachments", attachments);
		context.put("violations", violations);
		getPop(view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

	@Override
    public SimpleAttachmentModel getDefaultModel(ObjectCache objectCache) {
		return new SimpleAttachmentModel().defaultSetup(objectCache);
    }
	
	private SharedObjectSpaceClient getClient(String clientIdentity) {
	    return SharedObjectSpacesListener.reAttachClient(clientIdentity);
    }


}

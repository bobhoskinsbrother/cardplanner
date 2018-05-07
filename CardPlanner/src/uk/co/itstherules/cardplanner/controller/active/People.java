package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.model.business.Deleter;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.EnumArrayToEntityListConverter;
import uk.co.itstherules.yawf.dispatcher.Action;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


public final class People extends CardPlannerBase<PersonModel> {

	@Override public PersonModel getDefaultModel(ObjectCache objectCache) { return new PersonModel().defaultSetup(objectCache); }

	@Action("Delete") public void delete(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
    	PersonModel person = identityModelFromCache(objectCache, provider);
    	new Deleter().delete(objectCache, person);
    	try {
    		response.sendRedirect(response.encodeRedirectURL(listUrl(provider.getApplicationRoot())));
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }

	
	@Override @Action("List")
	public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		Set<PersonModel> people = objectCache.all(PersonModel.class, "visible", true);
		people.remove(SpecialInstances.retrieve(objectCache, Identities.INVISIBLE_PERSON));
		View view = new MergedTextView("people/list.freemarker");
		ViewContext context = new EmptyContext();
		context.put("people", people);
		String root = provider.getApplicationRoot();
		getTemplate(view.asText(context, root), getTitle(), "List People").renderTo(objectCache, provider, response, new EmptyContext(), new QueryKeyViolations());
	}
	
	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, PersonModel person, QueryKeyViolations violations) throws IOException {
		View view = new MergedTextView("people/edit.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, person);
		SimpleAttachmentModel defaultAttachment = SpecialInstances.retrieve(objectCache, Identities.DEFAULT_ATTACHMENT);
		Set<SimpleAttachmentModel> attachments = objectCache.all(SimpleAttachmentModel.class);
		attachments.remove(defaultAttachment);
		context.put("nullAttachment", defaultAttachment);
		context.put("genders", new EnumArrayToEntityListConverter().convert(PersonModel.Gender.values()));
		context.put("action", action);
		context.put("person", person);
		context.put("attachments", attachments);
		String root = provider.getApplicationRoot();
		getPop(view.asText(context, root), getTitle(), "").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }
	
	@Override
	protected void sendChangeActionRedirect(ValuesProvider provider, HttpServletResponse response) throws IOException {
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		context.put("redirect", listUrl(root));
		new MergedTextView("done.freemarker").renderTo(context, response, root);
	}
	
	protected PersonModel identityModelFromCache(ObjectCache objectCache, ValuesProvider provider) {
    	return objectCache.retrieveByIdentity(PersonModel.class, provider.getIdentity(), ObjectState.Pending, ObjectState.Active);
	}
}

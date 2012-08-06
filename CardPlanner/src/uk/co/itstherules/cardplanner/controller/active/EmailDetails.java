package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.EmailDetailsModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.EnumArrayToEntityListConverter;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.email.EmailDetails.EmailFlavour;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;

public class EmailDetails extends CardPlannerBase<EmailDetailsModel> {
	
	@Override protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, EmailDetailsModel object, QueryKeyViolations violations) throws IOException {
		EmptyContext context = new EmptyContext();
		context.put("flavours", new EnumArrayToEntityListConverter().convert(EmailFlavour.values()));
		context.put("emailDetails", object);
		context.put("violations", new QueryKeyViolations());
		View view = new MergedTextView("emaildetails/edit.freemarker");
		String root = provider.getApplicationRoot();
		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), violations);
	}

	@Override
	public EmailDetailsModel getDefaultModel(ObjectCache objectCache) {
		return SpecialInstances.retrieve(objectCache, Identities.EMAIL_DETAILS);
	}

}
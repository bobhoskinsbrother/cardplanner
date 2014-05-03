package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


public final class CardTypes extends CardPlannerBase<CardTypeModel> {
	
	@Override
	public CardTypeModel getDefaultModel(ObjectCache objectCache) {
		return new CardTypeModel().defaultSetup(objectCache);
	}

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, CardTypeModel object, QueryKeyViolations violations) throws IOException {
		Set<CardTypeModel> cardTypes = objectCache.all(CardTypeModel.class);
		cardTypes.remove(object);
		CardTypeModel defaultCardType = SpecialInstances.retrieve(objectCache, Identities.USER_STORY_ITEM_TYPE);
		View view = new MergedTextView("cardtypes/list.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, object);
		context.put("cardType", object);	
		context.put("cardTypes", cardTypes);
		context.put("nullCardType", defaultCardType);	
		String root = provider.getApplicationRoot();

		new TemplateCompositeModelView(provider.getBoolean("isNaked", Boolean.FALSE).booleanValue(), view.asText(context, root), getTitle(), "Card Types").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }
	
}
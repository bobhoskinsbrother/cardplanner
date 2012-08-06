package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.ProviderKey;
import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.ChangeContext;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;



public final class ValueTypes extends CardPlannerBase<ValueTypeModel> {
	
	@Override
	public ValueTypeModel getDefaultModel(ObjectCache objectCache) {
		return new ValueTypeModel().defaultSetup(objectCache);
	}

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, ValueTypeModel object, QueryKeyViolations violations) throws IOException {
		Set<ValueTypeModel> valueTypes = objectCache.all(ValueTypeModel.class);
		String root = provider.getApplicationRoot();
		valueTypes.remove(object);
		ValueTypeModel currencyValueType = SpecialInstances.retrieve(objectCache, Identities.CURRENCY_VALUE_TYPE);
		View view = new MergedTextView("valuetypes/list.freemarker");
		ViewContext context = new ChangeContext(getTitle(), action, violations, object);
		context.put("gbpValueType", currencyValueType);	
		context.put("valueType", object);	
		context.put("valueTypes", valueTypes);
		context.put(ProviderKey.root.name(), root);

		getTemplate(view.asText(context, root), getTitle(), "Value Types").renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

}

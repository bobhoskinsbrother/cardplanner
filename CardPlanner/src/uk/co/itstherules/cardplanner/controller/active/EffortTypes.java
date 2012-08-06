package uk.co.itstherules.cardplanner.controller.active;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import uk.co.itstherules.cardplanner.controller.CardPlannerBase;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.TypeType;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.EnumArrayToEntityListConverter;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelViewRegister;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;



public final class EffortTypes extends CardPlannerBase<EffortTypeModel> {

	@Override
    protected void changeView(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory, String action, EffortTypeModel object, QueryKeyViolations violations) throws IOException {
		EffortTypeModel effortType = (EffortTypeModel)object;
		Set<EffortTypeModel> effortTypes = objectCache.all(EffortTypeModel.class);
		effortTypes.remove(effortType);
		EffortTypeModel hourEffortType = SpecialInstances.retrieve(objectCache, Identities.IDEAL_DAY_EFFORT_TYPE);
		View view = new MergedTextView("efforttypes/list.freemarker");
		ViewContext context = new EmptyContext();
		
		context.put("action", action);
		context.put("hourEffortType", hourEffortType);
		context.put("effortType", effortType);
		context.put("effortTypes", effortTypes);
		context.put("violations", violations);
		context.put("textBased", TypeType.TextBased);
		context.put("types", new EnumArrayToEntityListConverter().convert(TypeType.values()));
		
		String root = provider.getApplicationRoot();
		new TemplateCompositeModelView(false, view.asText(context, root), getTitle(), "", false).renderTo(objectCache, provider, response, new EmptyContext(), violations);
    }

	@Override
    public EffortTypeModel getDefaultModel(ObjectCache objectCache) {
		return new EffortTypeModel().defaultSetup(objectCache);
    }
	
}

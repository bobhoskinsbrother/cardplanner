package uk.co.itstherules.cardplanner.controller.active;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.CardService;
import uk.co.itstherules.cardplanner.model.CardTypeModel;
import uk.co.itstherules.cardplanner.model.SpecialInstances;
import uk.co.itstherules.cardplanner.view.MergedTextView;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class Cards extends BaseController {

    @Action("List") public void list(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String root = provider.getApplicationRoot();
        View view = new MergedTextView("cards/list.freemarker");
        List<String> cssList = Arrays.asList("storyboard", "humane");
        List<String> javascriptList = Arrays.asList("browserintelligence_1_0", "pxdecorator_1_0", "humane");
        ViewContext context = new EmptyContext();
        context.put("topCard", new CardService().invisibleCard(objectCache));
        context.put("types", objectCache.all(CardTypeModel.class));
        new TemplateCompositeModelView(false, view.asText(context, root), "Cards", "List Cards", cssList, javascriptList).renderTo(objectCache, provider, response, context, new QueryKeyViolations());
    }

    @Action("Print") public void print(ObjectCache objectCache, ValuesProvider provider, HttpServletResponse response, ModelViewRegister viewFactory) throws IOException {
		String root = provider.getApplicationRoot();
		Set<CardModel> cards = objectCache.all(CardModel.class);
        ViewContext context = new EmptyContext();
        cards.remove(SpecialInstances.retrieve(objectCache, Identities.INVISIBLE_CARD));
        context.put("cards", cards);
        new MergedTextView("cards/print.freemarker").renderTo(context, response, root);
    }

}

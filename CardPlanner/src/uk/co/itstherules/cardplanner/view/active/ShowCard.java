package uk.co.itstherules.cardplanner.view.active;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.cardplanner.view.TemplateCompositeModelView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class ShowCard extends BaseModelView {

    @Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
        CardModel card = objectCache.retrieveByIdentity(CardModel.class, provider.getIdentity(), ObjectState.Active, ObjectState.Archived);
        View view = new MergedTextView("storyboard/show_card.freemarker");
        ViewContext context = new EmptyContext();
        context.put("card",  card);
        String root = provider.getApplicationRoot();
        TemplateCompositeModelView pop = new TemplateCompositeModelView(true, view.asText(context, root), "StoryBoard", "");
        return pop.asText(objectCache, provider, new EmptyContext(), new QueryKeyViolations());
    }
}

package uk.co.itstherules.cardplanner.view;

import java.util.Set;

import uk.co.itstherules.cardplanner.model.BurnUpModel;
import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.cardplanner.model.StatusService;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public abstract class BaseBurnUp extends BaseModelView {

    public String asText(ObjectCache cache, BurnUpModel burnUp, String action, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) throws IllegalArgumentException {
        View view = new MergedTextView("burnup/list.freemarker");
        final ViewContext context = new EmptyContext();
        context.putAll(mixInContext);
        final Set<StatusModel> statuses = new StatusService().everythingButTheBacklog(cache);
        context.put("burnUp", burnUp);
        context.put("action", action);
        context.put("statuses", statuses);
        return new TemplateCompositeModelView(false, view.asText(context, provider.getApplicationRoot()), "Cards", "List Cards", false).asText(cache, provider, context, new QueryKeyViolations());
    }
}

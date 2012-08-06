package uk.co.itstherules.cardplanner.view.active;

import java.util.Arrays;

import uk.co.itstherules.cardplanner.model.CardService;
import uk.co.itstherules.cardplanner.model.StatusService;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.SingleValueContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class StoryBoardShow extends BaseModelView {

    @Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
        View view = new MergedTextView("storyboard/show.freemarker");
        String root = provider.getApplicationRoot();
        ViewContext context = new EmptyContext();
        context.put("cards", new CardService().children(objectCache, provider.getIdentity()));
        context.put("backlog", new StatusService().backlog(objectCache));
        context.putAll(mixInContext);
        Simple simpleTemplate = new Simple(Arrays.asList("storyboard", "humane"), Arrays.asList("browserintelligence_1_0", "canvasbuilder_1_0", "canvasdoodle_readonly_1_0", "pxdecorator_1_0", "storyboard_viewer_1_0", "humane"));
        return simpleTemplate.asText(objectCache, provider, new SingleValueContext("content", view.asText(context, root)), violations);
    }
}

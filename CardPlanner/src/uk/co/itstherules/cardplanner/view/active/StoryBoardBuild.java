package uk.co.itstherules.cardplanner.view.active;

import java.util.Arrays;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.CardService;
import uk.co.itstherules.cardplanner.model.StatusModel;
import uk.co.itstherules.cardplanner.model.StatusService;
import uk.co.itstherules.cardplanner.model.StoryBoardModel;
import uk.co.itstherules.cardplanner.model.StoryBoardService;
import uk.co.itstherules.cardplanner.model.StoryBoardTemplate.ModifyTo;
import uk.co.itstherules.cardplanner.view.MergedTextView;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.BaseModelView;
import uk.co.itstherules.yawf.view.View;
import uk.co.itstherules.yawf.view.context.EmptyContext;
import uk.co.itstherules.yawf.view.context.SingleValueContext;
import uk.co.itstherules.yawf.view.context.ViewContext;

public class StoryBoardBuild extends BaseModelView {

	@Override
    public String asText(ObjectCache objectCache, ValuesProvider provider, ViewContext mixInContext, QueryKeyViolations violations) {
		CardModel card = new CardService().getCardOrDefault(objectCache, provider.getIdentity());
		View view = new MergedTextView("storyboard/build.freemarker");
		String root = provider.getApplicationRoot();
		ViewContext context = new EmptyContext();
		Set<StatusModel> statuses = new StatusService().everythingButTheBacklog(objectCache);
        StoryBoardModel board = card.getStoryBoard();
        if(board.isBlank()) { new StoryBoardService().modifyStoryBoard(ModifyTo.KanBanTemplate, objectCache, board); }
		context.put("card", card);
		context.put("statuses", statuses);
        Simple simpleTemplate = new Simple(Arrays.asList("storyboard"), Arrays.asList("resizeable", "jscolor", "browserintelligence_1_0", "canvasbuilder_1_0", "canvasdoodle_readonly_1_0", "canvasdoodle_1_0", "canvasdoodlepanel_1_0", "pxdecorator_1_0", "storyboard_designer_1_0"));
        return simpleTemplate.asText(objectCache, provider, new SingleValueContext("content", view.asText(context, root)), violations);
    }
}

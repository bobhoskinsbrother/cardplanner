package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.Set;

public final class StoryBoardTemplate {

    public enum ModifyTo {

        KanBanTemplate(new KanBan());

        private ModelModifier<StoryBoardModel> modifier;
        private ModifyTo(ModelModifier<StoryBoardModel> modifier) {
            this.modifier = modifier;
        }
    }

    public StoryBoardModel modifyTo(ObjectCache objectCache, StoryBoardModel board, ModifyTo template) {
        return template.modifier.modify(objectCache, board);
    }

    private static final class KanBan implements ModelModifier<StoryBoardModel> {
        @Override public StoryBoardModel modify(ObjectCache objectCache, StoryBoardModel storyBoard) {
            storyBoard.clear(objectCache);
            Set<StatusModel> statuses = new StatusService().everythingButTheBacklog(objectCache);
            StatusModel[] statusesArray = statuses.toArray(new StatusModel[statuses.size()]);
            final Integer storyBoardWidth = storyBoard.getWidth();
            final Integer storyBoardHeight = storyBoard.getHeight();
            storyBoard.setFrameType("large_tv");
            int horizontalLineY = storyBoardHeight / 6;
            int textAreaOffsetY = 50;
            int textAreaWidth = 120;
            int textAreaHeight = 35;
            final int padding = 25;
            int hotspotStartPoint = padding;
            int lineStartPoint = padding;
            final int statusAmount = statuses.size();
            if(statusAmount > 0) {
                final int hotspotWidth = ((storyBoardWidth - (padding*2)) / statusAmount);
                final int hotspotHeight = storyBoardHeight - (padding);
                for (int i = 0; i < statusAmount; i++) {
                    StatusModel status = statusesArray[i];
                    storyBoard.addHotspotArea(new StoryBoardHotspotAreaModel(status, hotspotWidth, hotspotHeight, hotspotStartPoint, padding));
                    int textAreaX = (lineStartPoint + (hotspotWidth / 2)) - (textAreaWidth / 2);
                    storyBoard.addTextArea(new StoryBoardTextAreaModel(status.getTitle(), textAreaWidth, textAreaHeight, textAreaX, textAreaOffsetY));
                    hotspotStartPoint = hotspotStartPoint + hotspotWidth;
                    if(i < (statusAmount - 1)) {
                        lineStartPoint = padding + (hotspotWidth*(i+1));
                        storyBoard.addLine(lineStartPoint, padding, lineStartPoint, hotspotHeight);
                    }
                }
                storyBoard.addTextArea(new StoryBoardTextAreaModel(storyBoard.getCard().getTitle(), textAreaWidth, textAreaHeight, ((storyBoardWidth - textAreaWidth) / 2), 10));
                storyBoard.addLine(padding, horizontalLineY, (storyBoardWidth - (padding)), horizontalLineY);
            }
            return storyBoard;
        }
    }

}

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
            StatusModel[] statusesArray = statuses.toArray(new StatusModel[]{});
            final Integer storyBoardHeight = storyBoard.getHeight();
            final Integer storyBoardWidth = storyBoard.getWidth();
            storyBoard.setFrameType("large_tv");
            int horizontalLineY = storyBoardHeight / 10;
            int textAreaOffsetY = 50;
            int textAreaWidth = 120;
            int textAreaHeight = 50;
            int textAreaY = textAreaOffsetY;
            int cumulativeOffsetX = 0;
            final int columnsAmount = statuses.size();
            if(columnsAmount > 0) {
                final int hotspotAreaWidth = (storyBoardWidth / columnsAmount);
                final int hotspotAreaHeight = storyBoardHeight;
                for (int i = 0; i < columnsAmount; i++) {
                    StatusModel status = statusesArray[i];
                    storyBoard.addHotspotArea(new StoryBoardHotspotAreaModel(status, hotspotAreaWidth, hotspotAreaHeight, cumulativeOffsetX, 0));
                    if(i < (columnsAmount - 1)) {
                        int lineX = cumulativeOffsetX + hotspotAreaWidth;
                        storyBoard.addVerticalLine(lineX, hotspotAreaHeight);
                    }
                    int textAreaX = (cumulativeOffsetX + (hotspotAreaWidth / 2)) - (textAreaWidth / 2);
                    storyBoard.addTextArea(new StoryBoardTextAreaModel(status.getTitle(), textAreaWidth, textAreaHeight, textAreaX, textAreaY));
                    cumulativeOffsetX = cumulativeOffsetX + hotspotAreaWidth;
                }
                storyBoard.addTextArea(new StoryBoardTextAreaModel(storyBoard.getCard().getTitle(), textAreaWidth, textAreaHeight, ((storyBoardWidth - textAreaWidth) / 2), 10));
                storyBoard.addHorizontalLine(horizontalLineY, storyBoardWidth);
            }
            return storyBoard;
        }
    }

}

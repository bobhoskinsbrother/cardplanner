package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import java.util.Set;

public final class StoryBoardService {

    public void deleteHotspotsWith(ObjectCache objectCache, StatusModel status) {
        Set<StoryBoardHotspotAreaModel> hotSpots = getHotspotsFor(objectCache, status);
        for (StoryBoardHotspotAreaModel hotSpot : hotSpots) {
            deleteHotspot(objectCache, hotSpot);
        }

    }
    public void deleteHotspot(ObjectCache objectCache, StoryBoardHotspotAreaModel hotSpot) {
        Set<StoryBoardModel> storyBoards = objectCache.contains(StoryBoardModel.class, "hotSpotAreas", hotSpot);
        for (StoryBoardModel storyBoard : storyBoards) {
            storyBoard.removeHotSpot(objectCache, hotSpot);
        }
        objectCache.delete(hotSpot);
    }

    public Set<StoryBoardHotspotAreaModel> getHotspotsFor(ObjectCache objectCache, StatusModel status) {
        return objectCache.all(StoryBoardHotspotAreaModel.class, "status.identity", status.getIdentity());
    }

    public StoryBoardModel modifyStoryBoard(StoryBoardTemplate.ModifyTo template, ObjectCache objectCache, StoryBoardModel board){
        return new StoryBoardTemplate().modifyTo(objectCache, board, template);
    }


}

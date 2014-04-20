package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity public final class StoryBoardHotspotAreaModel extends IdentifiableDeleteableModel<StoryBoardHotspotAreaModel> {

	@ManyToOne @QueryKey(value = "status", cache = CacheInstruction.FromCache) private StatusModel status;
	@QueryKey("width") private int width;
	@QueryKey("height") private int height;

    public StoryBoardHotspotAreaModel() { }

    public StoryBoardHotspotAreaModel(StatusModel status, int width, int height, int x, int y) {
        this.status = status;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public StoryBoardHotspotAreaModel defaultSetup(ObjectCache objectCache) {
        this.status = SpecialInstances.retrieve(objectCache, Identities.THE_BACKLOG);
        this.width = 250;
        this.height = 250;
        this.x = 10;
        this.y = 10;
        return this;
    }

    public int getWidth() { return width; }
	public int getHeight() { return height; }
	public StatusModel getStatus() { return status; }

    public boolean containsPoint(int x, int y) {
		return (x >= this.x) && (x <= (width+this.x)) && (y >= this.y) && (y <= (height+this.y));
    }

}
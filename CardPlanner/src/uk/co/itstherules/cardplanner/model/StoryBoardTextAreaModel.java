package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity public class StoryBoardTextAreaModel extends IdentifiableDeleteableModel<StoryBoardTextAreaModel> {

	@QueryKey("width") private int width;
	@QueryKey("height") private int height;

    public StoryBoardTextAreaModel() { }

    public StoryBoardTextAreaModel(String title, int width, int height, int x, int y) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public StoryBoardTextAreaModel defaultSetup(ObjectCache objectCache) {
        this.width = 120;
        this.height = 50;
        this.x = 5;
        this.y = 5;
        return this;
    }

    public int getWidth() { return width; }
	public int getHeight() { return height; }

}
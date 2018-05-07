package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity
public final class StoryBoardBrushModel extends IdentifiableDeleteableModel<StoryBoardBrushModel> {
	
	@QueryKey("color") private String color;
	@QueryKey("size") private Integer size;

	public StoryBoardBrushModel() {
    }

	public StoryBoardBrushModel(String color, Integer size) {
		this.color = color;
		this.size = size;
	}
	
	public Integer getSize() {
	    return size;
    }
	
	public String getColor() {
	    return color;
    }


    public StoryBoardBrushModel defaultSetup(ObjectCache objectCache) { return this; }
}

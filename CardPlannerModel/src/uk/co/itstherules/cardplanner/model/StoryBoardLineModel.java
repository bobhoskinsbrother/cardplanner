package uk.co.itstherules.cardplanner.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity public final class StoryBoardLineModel extends IdentifiableDeleteableModel<StoryBoardLineModel> {

	@OneToMany(cascade=CascadeType.ALL) @QueryKey("points") private List<StoryBoardLineEndModel> points;
	@OneToOne(cascade=CascadeType.ALL) @QueryKey("brush") private StoryBoardBrushModel brush;

    public StoryBoardLineModel() { }

    public StoryBoardLineModel(StoryBoardBrushModel brush, List<StoryBoardLineEndModel> points) {
        this.brush = brush;
        this.points = points;
    }

	public List<StoryBoardLineEndModel> getPoints() {
	    return points;
    }
	
	public StoryBoardBrushModel getBrush() { return brush; }
    public StoryBoardLineModel defaultSetup(ObjectCache objectCache) { return this; }

}
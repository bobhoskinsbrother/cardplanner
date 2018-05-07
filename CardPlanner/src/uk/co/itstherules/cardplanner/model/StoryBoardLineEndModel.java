package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity public final class StoryBoardLineEndModel extends IdentifiableDeleteableModel<StoryBoardLineEndModel> {

	public StoryBoardLineEndModel() {
    }
	
	public StoryBoardLineEndModel(int x, int y) {
		this.x = x;
		this.y = y;
	}

    public StoryBoardLineEndModel defaultSetup(ObjectCache objectCache) { return this; }

}
package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity
public final class StatusModel extends IdentifiableDeleteableModel<StatusModel> {

	public StatusModel() { super(); }
    public StatusModel defaultSetup(ObjectCache objectCache) { return this; }

    public StatusModel clone() {
        StatusModel clone = new StatusModel();
        clone.setIdentity(getIdentity());
        clone.title = title;
        clone.objectState = objectState;
        clone.x = x;
        clone.y = y;
        clone.z = z;
        clone.setSortOrder(getSortOrder());
		return clone;
    }



}
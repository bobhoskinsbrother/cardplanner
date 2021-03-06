package test.models.to.instantiate;

import java.util.Date;

import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public class TestModel implements Entity<TestModel> {
	
	private String identity;
	private String title;

	public TestModel() {}
	
	public TestModel(String identity, String title) {
		this.identity = identity;
		this.title = title;
    }
	
	public String getTitle() {
	    return title;
    }

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getIdentity() {
		return identity;
	}

	public void delete() {
	}

	public Boolean getDeleted() {
		return Boolean.FALSE;
	}

	public void updateTimestamp() {
	}

	public void setTitle(String title) {
	    this.title = title;
    }

	public Boolean getInvisible() {
		return Boolean.FALSE;
    }

	public void makeInvisible() {
    }

	public Date getUpdated() {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

	public Boolean getArchived() {
	    return Boolean.FALSE;
    }

	public void archive() {
    }

	public Date getAdded() {
	    return null;
    }

	@SuppressWarnings("unchecked")
    public TestModel defaultSetup(ObjectCache objectCache) {
	    return null;
    }

	@Override
    public void activate() {
    }

	@Override
    public Boolean getActive() {
	    return Boolean.TRUE;
    }

	@Override
    public Boolean getPending() {
	    return Boolean.FALSE;
    }

	@Override
    public void pending() {
    }

	@Override
    public int getSortOrder() {
	    return 0;
    }

	@Override
    public void setSortOrder(Integer sortOrder) {
    }

}

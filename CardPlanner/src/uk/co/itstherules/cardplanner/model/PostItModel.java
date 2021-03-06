package uk.co.itstherules.cardplanner.model;



import javax.persistence.Entity;
import javax.persistence.OneToOne;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class PostItModel extends IdentifiableDeleteableModel<PostItModel> {
	
	@QueryKey("body") @NotEmpty private String body = "";
	@QueryKey("colour") @NotNull @MatchPattern(pattern={"^#{0,1}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}$"}) private String colour;
	@OneToOne @QueryKey(value="postItAttachment", cache=CacheInstruction.FromCache) private SimpleAttachmentModel postItAttachment;

    public PostItModel() {
        super();
        this.body = "";
        this.colour = "#FFFFAA";
    }

    public PostItModel defaultSetup(ObjectCache objectCache) {
        this.postItAttachment = SpecialInstances.retrieve(objectCache, Identities.DEFAULT_ATTACHMENT);
        this.body = "";
        return this;
    }

    public void setColour(String hexColour){ this.colour = hexColour; }
	
	public String getColour() { return colour; }

	public void setPostItAttachment(SimpleAttachmentModel postItAttachment) {
	    this.postItAttachment = postItAttachment;
    }

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public SimpleAttachmentModel getPostItAttachment() {
    	return this.postItAttachment;
    }

}
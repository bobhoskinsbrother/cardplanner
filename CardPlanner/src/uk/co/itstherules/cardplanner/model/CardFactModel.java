package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity  
public final class CardFactModel extends IdentifiableDeleteableModel<CardFactModel> {
	
	@QueryKey("body") @NotNull private String body;
	@QueryKey("colour") @NotNull @MatchPattern(pattern={"^#{0,1}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}$"}) private String hexColour;

	public CardFactModel() { this.hexColour = "#FFFFFF"; }

	public String getColour() { return hexColour; }
	public String getBody() { return body; }
    public CardFactModel defaultSetup(ObjectCache objectCache) { return this; }

}
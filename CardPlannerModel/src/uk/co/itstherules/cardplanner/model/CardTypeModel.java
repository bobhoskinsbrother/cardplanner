package uk.co.itstherules.cardplanner.model;

import javax.persistence.Entity;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity  
public final class CardTypeModel extends IdentifiableDeleteableModel<CardTypeModel> {
	
	@QueryKey("colour") @NotNull @MatchPattern(pattern={"^#{0,1}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}$"}) private String hexColour;
	public CardTypeModel() { this.hexColour = "#FFFFFF"; }
	public void setColour(String hexColour){ this.hexColour = hexColour; }
	public String getColour() { return hexColour; }
    public CardTypeModel defaultSetup(ObjectCache objectCache) { return this; }

}

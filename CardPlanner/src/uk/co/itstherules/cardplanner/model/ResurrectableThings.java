package uk.co.itstherules.cardplanner.model;

import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;


public final class ResurrectableThings {
	private Set<ResurrectableThing> set;

	public ResurrectableThings() {
		set = new LinkedHashSet<ResurrectableThing>();
		set.add(new ResurrectableThing("1", "EffortType", EffortTypeModel.class));
		set.add(new ResurrectableThing("2", "Card", CardModel.class));
		set.add(new ResurrectableThing("3", "Person", PersonModel.class));
		set.add(new ResurrectableThing("4", "Status", StatusModel.class));
		set.add(new ResurrectableThing("5", "Tag", TagModel.class));
		set.add(new ResurrectableThing("6", "ValueType", ValueTypeModel.class));
		set.add(new ResurrectableThing("7", "CardType", CardTypeModel.class));
	}

	public ResurrectableThing get(String identity){
		for (ResurrectableThing resurrectable : set) {
	        if(identity.equals(resurrectable.getIdentity())){
	        	return resurrectable;
	        }
        }
		throw new IllegalArgumentException(MessageFormat.format("The identity {0} has not been recognised", identity));
	}
	
	public Set<ResurrectableThing> all(){
		return set;
	}

	public ResurrectableThing first() {
		return set.iterator().next();
    }
}
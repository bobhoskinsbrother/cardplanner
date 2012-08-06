package uk.co.itstherules.cardplanner.model.atom;

import java.text.MessageFormat;

import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.yawf.model.atom.EntryContributer;

public final class PersonContributerModel implements EntryContributer {

	private final PersonModel person;

	public PersonContributerModel(PersonModel person) {
		this.person = person;
    }

	public String getName() {
	    return MessageFormat.format("{0} {1}", person.getFirstName(), person.getLastName());
    }
}

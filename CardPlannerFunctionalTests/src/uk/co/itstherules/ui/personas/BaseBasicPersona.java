package uk.co.itstherules.ui.personas;

import uk.co.itstherules.ui.pages.Page;

public abstract class BaseBasicPersona<T>  {

	protected PersonaFixture personaData;
	
	protected BaseBasicPersona(String name) {
		this.personaData = PersonaFixtureFactory.create(name);
	}
	
	@SuppressWarnings("unchecked") public T openPage(Page<?> page) {
		page.navigateTo(personaData.getIdentifier(page.identifier()));
	    return (T) page;
    }
	
    public PersonaFixture getMemory() {
		return personaData;
	}
	
}

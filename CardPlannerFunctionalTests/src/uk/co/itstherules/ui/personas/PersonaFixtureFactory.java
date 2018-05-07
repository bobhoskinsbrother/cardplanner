package uk.co.itstherules.ui.personas;

import java.util.Properties;

import uk.co.itstherules.yawf.PropertiesHandler;

public class PersonaFixtureFactory {

	public static PersonaFixture create(String personaDataFile) {
		Properties properties = PropertiesHandler.provide("personas", personaDataFile+".properties");
		return new PersonaFixture(properties);
    }
	
}

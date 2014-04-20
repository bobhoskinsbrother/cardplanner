package uk.co.itstherules.yawf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationInfoTest {
	
	private String userHome;

	@Before
	public void setUp() {
		userHome = System.getProperty("user.home");
		System.setProperty("user.home", "/Where/the/deer/and/antelope/stray");
	}
	
	@After 
	public void tearDown() {
		System.setProperty("user.home", userHome);
	}
	
	@Test public void resourceRootPath() {
		assertEquals("ben/is/fab/", ApplicationInfo.getResourceRootPath("ben.is.fab"));
		assertEquals("ben/is/fab//", ApplicationInfo.getResourceRootPath("ben.is.fab."));
	}
	
	@Test public void uploadPath() {
		assertEquals("/Where/the/deer/and/antelope/stray/Data/BenWasEre/Upload/", ApplicationInfo.getUploadPath("BenWasEre"));
	}
	
	
	@Test public void databasePath() {
		assertEquals("/Where/the/deer/and/antelope/stray/Data/BenWasEre/", ApplicationInfo.getDatabasePath("BenWasEre"));
	}
}

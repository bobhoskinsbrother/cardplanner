package uk.co.itstherules.yawf;

import java.text.MessageFormat;

public final class ApplicationInfo {

    private ApplicationInfo(){}

	public static String getResourceRootPath(String resourceRoot) {
		return resourceRoot.replace('.', '/') + "/";
	}

	public static String getUploadPath(String applicationName){
		return MessageFormat.format("{0}/Data/{1}/Upload/", System.getProperty("user.home"), applicationName) ;
	}
	
	public static String getDatabasePath(String applicationName){
		return MessageFormat.format("{0}/Data/{1}/", System.getProperty("user.home"), applicationName) ;
	}
}
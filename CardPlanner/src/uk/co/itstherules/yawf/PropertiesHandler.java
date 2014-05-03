package uk.co.itstherules.yawf;

import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;

public final class PropertiesHandler {
	
	
	public static Properties provide(String root, String... names) {
		Properties allProperties = new Properties();
		for (String name : names) {
			if(root.length() > 0 && !root.endsWith("/")) { root = root+"/"; }
			InputStream inputStream = PropertiesHandler.class.getClassLoader().getResourceAsStream(root+name);
			if(inputStream==null) {
				throw new RuntimeException("Cannot find properties file \""+root+name+"\"");
			}
			try {
				Properties properties = new Properties();
				properties.load(inputStream);
				allProperties.putAll(properties);
			} catch (IOException e) {
				throw new RuntimeException("Cannot find properties file \""+root+name+"\"");
			}
        }
		return allProperties;
	}
	
	public static void save(String name, Properties properties) {
		String path;
        try {
	        path = URLDecoder.decode(PropertiesHandler.class.getClassLoader().getResource(name).getFile(), "utf8");
	        System.out.println(path);
	        Writer writer = new FileWriter(path);
	        properties.store(writer, "Updated from the web interface");
        } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }

	}
	
	
}
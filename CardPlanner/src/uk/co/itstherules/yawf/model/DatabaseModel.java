package uk.co.itstherules.yawf.model;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class DatabaseModel extends IdentifiableDeleteableModel<DatabaseModel> {

	@QueryKey("applicationName") @NotBlank private String applicationName;
	@QueryKey("databaseName") private String databaseName;
	@QueryKey("databaseHost") private String databaseHost;
	@QueryKey("databaseUsername") @NotBlank private String databaseUsername;
	@QueryKey("databasePassword") @NotBlank private String databasePassword;
	@QueryKey("databasePort") @Min(9000) private Integer databasePort;
	
	public DatabaseModel() {
		this.applicationName="";
		this.databaseName = "cardplanner";
		this.databaseUsername = "username";
		this.databasePassword = "password";
		this.databaseHost = "localhost";
		this.databasePort = 5432;
    }
	
	public String getApplicationName() { return applicationName; }
	public String getDatabaseUsername() { return databaseUsername; }
	public String getDatabasePassword() { return databasePassword; }
	public String getDatabaseName() { return databaseName; }
	public Integer getDatabasePort() { return databasePort; }
	public String getDatabaseHost() { return databaseHost; }

	public DatabaseModel setApplicationName(String applicationName) {
		this.applicationName = applicationName;
    	return this;
    }

	public DatabaseModel setDatabaseName(String databaseName) {
    	this.databaseName = databaseName;
    	return this;
    }

	public DatabaseModel setDatabaseHost(String databaseHost) {
    	this.databaseHost = databaseHost;
    	return this;
    }

	public DatabaseModel setDatabaseUsername(String databaseUsername) {
    	this.databaseUsername = databaseUsername;
    	return this;
    }

	public DatabaseModel setDatabasePassword(String databasePassword) {
    	this.databasePassword = databasePassword;
    	return this;
    }

	public DatabaseModel setDatabasePort(Integer databasePort) {
    	this.databasePort = databasePort;
    	return this;
    }

	public String  getDatabaseUrl() {
		return new StringBuilder().append("jdbc:h2:~/").append(this.getDatabaseHost()).append("/").append(this.getDatabaseName()).toString();
    }

    @Override public DatabaseModel defaultSetup(ObjectCache objectCache) { return this; }

	
}
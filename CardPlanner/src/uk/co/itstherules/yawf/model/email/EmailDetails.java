package uk.co.itstherules.yawf.model.email;

public interface EmailDetails {
	
	public enum EmailFlavour { GMail }
	
	String getFromEmail();
	String getFromName();
	String getHost();
	String getUserName();
	String getPassword();
	EmailFlavour getFlavour();
	
}

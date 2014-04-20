package uk.co.itstherules.cardplanner;

public enum ProviderKey {
	
	root,
	applicationName,
	binaryExtensions,
	childrenAmount, 
	childrenPrefix, 
	cards,
	rate, 
	resourceRoot,
	statuses, 
	syncServers,
	title, 
	type
	;
	
	@Override
	public String toString() { return name(); }
}

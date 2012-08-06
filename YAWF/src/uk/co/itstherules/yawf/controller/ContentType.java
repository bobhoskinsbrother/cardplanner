package uk.co.itstherules.yawf.controller;

public enum ContentType {
	
	css("text/css"),
	csv("text/csv"),
	genericBinary("application/octet-stream"),
	html("text/html"),
	js("text/javascript"),
	json("application/json"),
	pdf("application/pdf"), 
	png("image/png"), 
	svg("svg+xml"), 
	swf("application/x-shockwave-flash"), 
	text("text/plain"), 
	xls("application/ms-excel"), 
	xml("text/xml"), 
	zip("application/zip")
	; 
	
	private final String type;

    private ContentType(String type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}

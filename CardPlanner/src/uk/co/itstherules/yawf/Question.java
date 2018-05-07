package uk.co.itstherules.yawf;

import org.eclipse.jetty.server.Server;


public abstract class Question {
	
	protected final Server server;
	
	public Question(Server server) {
		this.server = server;
	}
	
	public abstract boolean isAnswered();
}

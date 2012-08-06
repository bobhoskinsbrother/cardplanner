package uk.co.itstherules.yawf.initialization;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;

public class CompositeInitializer implements Initializer  { 
	
	private List<Initializer> initializers;

	public CompositeInitializer() {
		this.initializers = new ArrayList<Initializer>();
    }
	
	public void add(Initializer initializer) { initializers.add(initializer); }
	
	public void initialize(ServletConfig config){
		for (Initializer initializer : this.initializers) { 
			initializer.initialize(config); 
		}
	}

}
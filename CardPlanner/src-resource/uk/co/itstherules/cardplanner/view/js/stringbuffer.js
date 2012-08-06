function StringBuffer() { 

	var buffer = [];
	
	this.append = function(string) { 
		buffer.push(string); 
	}
	
	this.toString = function() { 
		return buffer.join(""); 
	} 
	
}
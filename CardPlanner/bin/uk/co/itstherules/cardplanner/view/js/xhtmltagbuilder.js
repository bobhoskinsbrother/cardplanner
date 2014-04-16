function XHtmlTagBuilder(root) {
	
	var root = root;
	
	this.editLink = function(controller, title, identity, name, queryStringValues, attributes) {
		return this.link(this.editUrl(controller, identity, title, queryStringValues), name, attributes);
	}
	
	this.showLink = function(controller, title, identity, name, queryStringValues, attributes) {
		return this.link(this.showUrl(controller, identity, title, queryStringValues), name, attributes);
	}
	
	this.showImageString = function(controller, identity, title) {
		return '<img src="' + this.showUrl(controller, identity, title) + '" />';
	}

	this.showLinkString = function(controller, identity, title) {
		return '<a href="' + this.showUrl(controller, identity, title) + '" target="_blank">'+title+'</a>';
	}


	this.showImage = function(title) {
		return this.showImage(title, {});
	}

	this.showImage = function(title, hash) {
		var options = {"src": this.showUrl("Images", "0", title)};
		options = Object.extend(options, hash || {});
		return new Element("img", options);
	}

	this.showImageUrl = function(title) {
		var url = this.showUrl("Images", "0", title);
		alert(url);
		return url;
	}
	
	this.link = function(url, title, hash) {
		var options = {"href": url};
		options = Object.extend(options, hash || {});
		var a = new Element("a", options).update(title);
		return a;
	}
	
	this.showUrl = function(controller, identity, title, queryStringValues) {
		return this.url(controller, "Show", identity, title, queryStringValues);
	}
	
	this.editUrl = function(controller, identity, title, queryStringValues) {
		return this.url(controller, "Edit", identity, title, queryStringValues);
	}
	
	this.url = function(controller, action, identity, title, hash) {
		var queryString = "";
		if(hash != null && hash.length>-1) { queryString = new QueryStringBuilder().build(hash); } 
		var buffer = [];
		buffer.push(root);
		buffer.push("/");
		buffer.push(controller);
		buffer.push("/");
		buffer.push(action);
		buffer.push("/");
		buffer.push(identity);
		buffer.push("/");
		buffer.push(escape(unescape(title)));
		buffer.push(queryString);
		return buffer.join('');
	}
}





function AttributesBuilder() {
	
	this.build = function(hash) {
		return build(hash, false);
	}
	
	this.build = function(hash, isViolated) {
		var buffer = [];
        var containsClass = false;
        for(var key in hash) {
	        buffer.push(key);
	        buffer.push("=\"");
	        buffer.push(hash[key]);
	        if("class" == key && isViolated) { 
	        	containsClass = true;
	        	buffer.push(" errorbackground errorborder");
	        }
	        buffer.push("\" ");
        }
        if(!containsClass && isViolated) { buffer.push(" class=\"errorbackground errorborder\" "); }
        return buffer.join('');
    }
}





function QueryStringBuilder() {
	this.build = function(hash) {
		var buffer = [];
		if(hash.length > 0) {
			buffer.push("?");
		}
		for(var key in hash) {
		    if (hash.hasOwnProperty(key)) { 
				buffer.push(key);
				buffer.push("=");
				buffer.push(hash[key]);
				buffer.push("&");
			}
		}
		var queryString = buffer.join('');
		if(queryString.length > 1) {
			queryString = queryString.substring(0, queryString.length-1);
		}
	}
}

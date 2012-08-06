	Array.prototype.containsIdentifiable = function(identity) {
	    var i = this.length;
	    while (i--) {
	        if (this[i].identity == identity) { return true; }
	    }
	    return false;
	}

	Array.prototype.contains = function(value) {
	    var i = this.length;
	    while (i--) {
	        if (this[i] == value) { return true; }
	    }
	    return false;
	}

	Array.prototype.getIdentifiable = function(identity) {
	    var i = this.length;
	    while (i--) {
	        if (this[i].identity == identity) { return this[i]; }
	    }
	    return undefined;
	}

	Array.prototype.remove = function(itemToRemove) {
		var j = 0;
		if(itemToRemove.identity) {
			while (j < this.length) {
				if (this[j].identity == itemToRemove.identity) {
					return this.splice(j, 1);
				} else {
					j++;
				}
			}
		} else {
			while (j < this.length) {
				if (this[j] == itemToRemove) {
					return this.splice(j, 1);
				} else {
					j++;
				}
			}
		}
	}

    Array.prototype.indexOf = function(needle) {
        for(var i = 0; i < this.length; i++) {
            if(this[i] === needle) { return i; }
        }
        return -1;
    };

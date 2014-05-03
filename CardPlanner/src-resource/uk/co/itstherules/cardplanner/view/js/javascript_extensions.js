if (typeof Type=="undefined" || !Type) {
	var Type = {
		of: function(value) {
			if (!value && value != false) { return 'null'; }		        	
		    var typeString = typeof value;
		    if ((typeString === 'object') && ((typeof value.length) === 'number') && !(value.propertyIsEnumerable('length')) && ((typeof value.splice) === 'function')) {
		        return 'array';
		    }
		    return typeString;
		},
		isNumeric: function(string) {
			if(!Type.isString(string)) { return false; }
			var numericCharacters = "0123456789";
			for (var index = 0; index < string.length; index++) {
				if (numericCharacters.indexOf(string.charAt(index)) == -1) { return false; }
			}
			return true;
		}
	};

	(function(typeStrings) {
		for (var index = 0; index < typeStrings.length; index++) {
			(function(typeString){
				Type["is" + typeString] = function(unknownType) {
					return (Type.of(unknownType) === typeString.toLowerCase()) ? true : false;
				};
			}(typeStrings[index]));
		}
	}(["String", "Number", "Object", "Array", "Boolean", "Null", "Function", "Undefined"]));

}
;

Array.prototype.containsIdentifiable = function (identity) {
    var i = this.length;
    while (i--) {
        if (this[i].identity == identity) {
            return true;
        }
    }
    return false;
};
Array.prototype.contains = function (value) {
    var i = this.length;
    while (i--) {
        if (this[i] == value) {
            return true;
        }
    }
    return false;
};
Array.prototype.getIdentifiable = function (identity) {
    var i = this.length;
    while (i--) {
        if (this[i].identity == identity) {
            return this[i];
        }
    }
    return undefined;
};
Array.prototype.remove = function (itemToRemove) {
    var j = 0;
    if (itemToRemove.identity) {
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
};
Array.prototype.indexOf = function (needle) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] === needle) {
            return i;
        }
    }
    return -1;
};
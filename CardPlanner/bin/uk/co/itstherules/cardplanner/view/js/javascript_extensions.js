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

};
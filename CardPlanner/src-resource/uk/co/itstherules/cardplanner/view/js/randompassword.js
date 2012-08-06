var RandomPassword = {
		
	getRandomNumber: function(lowerBound, upperBound) {
		return (Math.floor(Math.random() * (upperBound - lowerBound)) + lowerBound);
	},
	
	getCharacterSet: function(options) {
		var numberChars = "0123456789";
		var lowerChars = "abcdefghijklmnopqrstuvwxyz";
		var upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		var otherChars = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/? ";
		
		var characterSet = "";
		
		if (options.lower) { characterSet += lowerChars; }
		if (options.upper) { characterSet += upperChars; }
		if (options.number){ characterSet += numberChars; }
		if (options.other) { characterSet += otherChars; }
		return characterSet;
	},
	
	getRandomCharacter: function(options) {
		var characterSet = this.getCharacterSet(options);
		return characterSet.charAt(this.getRandomNumber(0, characterSet.length));
	},
	
	getPassword: function(length, options) {
		if(options == undefined) {
			options = { "number": true, "lower": true, "upper": true, "other": true };
		}
		var randomCharacters = "";
		for (var index = 0; index < length; index++) {
			randomCharacters += this.getRandomCharacter(options);
		}
		return randomCharacters;
	}
}
	function LetterAbbreviator() {
		this.abbreviate = function(string, letterAmount) {
			string = string.replace("\<.*?\>", "");
			var length = string.length;
			if(length > letterAmount) {
				letterAmount = Math.min(length, letterAmount);
				string = string.substring(0, letterAmount);
				string = string.substring(0, string.lastIndexOf(" "));
				var buffer = "";
		        buffer += string;
				buffer+="...";
				return buffer;
			}
			return string;
		}
	}
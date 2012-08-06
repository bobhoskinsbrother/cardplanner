var BrowserIntelligence = {
	'supportsTouch': ('createTouch' in document),
	'pointerXY': function(event) {
		if(BrowserIntelligence.supportsTouch && event.touches) {
			if(!event.touches && !event.touches[0]) {
				var x = event.pageX, y = event.pageY;
			} else {
		  		var x = event.touches[0].pageX, y = event.touches[0].pageY;
			}
		} else {
			var x = Event.pointerX(event), y = Event.pointerY(event);
		}
		var point = [x,y];
		point.x = x; 
		point.y = y;
		return point;
	}
};
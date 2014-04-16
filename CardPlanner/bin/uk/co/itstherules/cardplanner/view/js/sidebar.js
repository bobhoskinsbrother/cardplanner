var SideBarView = {
		
	isExtended: false,
	
	toggle: function() {
		if(SideBarView.isExtended == false) {
			SideBarView.extend();
		} else {
			SideBarView.retract();
		}
	},
	
	extend: function() {
		new Effect.BlindDown($('sideBarContents'), {scaleX: 'true', scaleY: 'true;', scaleContent: false });
		$('sideBarTabImage').src = $('sideBarTabImage').src.replace(/(\.[^.]+)$/, '_active$1');
		new Effect.Fade('sideBarContents', { duration:1.0, from:0.0, to:1.0 });
		SideBarView.isExtended = true;
	},

	retract: function() {
		new Effect.BlindUp($('sideBarContents'), {scaleX: 'true', scaleY: 'true;', scaleContent: false, afterFinish: function() { Event.observe($('sideBarTab'), 'click', SideBarView.extend, true); }});
		$('sideBarTabImage').src = $('sideBarTabImage').src.replace(/_active(\.[^.]+)$/, '$1');
		new Effect.Fade('sideBarContents', { duration:1.0, from:1.0, to:0.0 });
		SideBarView.isExtended=false;
	},
	
	initialize: function() {
		Event.observe($('sideBarTab'), 'click', SideBarView.toggle, true);
	}
}

Event.observe(window, 'dom:loaded', SideBarView.initialize, true);
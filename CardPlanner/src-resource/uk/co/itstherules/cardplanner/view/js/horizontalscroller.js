var HorizontalScroller = {
	back : function(target) {
		HorizontalScroller.move(target, 180);
		var targetValue = $(target + 'Value');
		var value = targetValue.value;
		targetValue.value = (+value) + 180;
	},

	forward : function(target) {
		HorizontalScroller.move(target, -180);
		var targetValue = $(target + 'Value');
		var value = targetValue.value;
		targetValue.value = (+value) - 180;
	},

	move : function(target, value) {
		new Effect.Move(target, {
			x : value,
			y : 0,
			transition : Effect.Transitions.sinoidal
		});
	}
}
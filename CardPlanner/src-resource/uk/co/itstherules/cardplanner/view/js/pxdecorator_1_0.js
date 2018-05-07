var PxDecorator = Class.create({
	initialize: function(toDecorate) {
		for(var key in toDecorate) {
			if(Type.isNumber(toDecorate[key])) {
				this[key] = toDecorate[key] + 'px';
			} else {
				this[key] = toDecorate[key];
			}
		}
	}
});

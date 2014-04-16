var Logger = {
	
	enabled: true,
	serialize: function(object) {
		if((typeof object) == 'object') {
			var buffer = '\n';
			for(var key in object) {
				buffer += key+' : ';
				var value = object[key];
				if((typeof value) == 'object') {
					buffer += '{\n';
					buffer += Logger.serialize(value);
					buffer += '}\n';
				} else {
					buffer += value;
				}
				var buffer = ',\n';
			}
			return buffer;
		}
		return object+'';
	},
	
	log: function(object) {
		if(Logger.enabled) {
			var panel = $('logPanel');
			if(!panel) {
				var panel = Builder.node('code', { id: 'logPanel' });
				var dragBar = Builder.node('div', {style: 'width: 600px; height: 20px; background: #CCCCCC; clear: both;'});
				var container = $(Builder.node('div', {  id: 'logPanelContainer', className: 'logger', style: 'width: 600px; height: 300px; z-index: 100000; position: absolute; left: 0px; top: 0px;' }, [ dragBar, Builder.node('pre', {style: 'width: 600px; height: 280px; background: #000000; color: #FFFFFF; overflow: scroll; text-align: left;'}, [ panel ])] ));
				$$('body')[0].insert(container);
				new Draggable('logPanelContainer', { handle: dragBar });
			}
			var text = panel.innerHTML + '\n' + Logger.serialize(object);
			panel.update(text);
		}
	},
	
	destroy: function() {
		$('logPanelContainer').remove();
	}
}


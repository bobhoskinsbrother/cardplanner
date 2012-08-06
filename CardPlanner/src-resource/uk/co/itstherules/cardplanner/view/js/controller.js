var TemplateController = {
	confirmDeletion: function(title, url) {
		if(confirm("Do you really want to delete:\n"+unescape(title)+"?")) {
			var postForm = $('postForm');
			postForm.action=url;
			postForm.submit();
		}
	},

	confirmArchive: function(title, url) {
		if(confirm("Do you really want to archive \n'"+unescape(title)+"'?")) {
			var postForm = $('postForm');
			postForm.action=url;
			postForm.submit();
		}
	},
	
	confirmDearchive: function(title, url) {
		if(confirm("Do you really want to make \n'"+unescape(title)+"' \nactive again?")) {
			var postForm = $('postForm');
			postForm.action=url;
			postForm.submit();
		}
	},
	
	attachDraggables: function(selector, options) {
		var d = 'div.draggable';
		if(selector) { d = selector; }
		options = Object.extend({
			revert: true, 
			ghosting: false, 
			scroll: window
		}, options || {});

		var draggables = $$(d);
		for(var i = 0; i < draggables.length; i++) {
			draggables[i].dragObject = new Draggable(draggables[i], options); 
		}
	},

	attachDroppables: function(onDropFunction, selector) {
		var d = 'div.droppable';
		if(selector) { d = selector; }
		var droppables = $$(d);
		for(var i = 0; i < droppables.length; i++) {
			Droppables.add($(droppables[i].id), { onDrop: onDropFunction } ); 
		}
	}

};


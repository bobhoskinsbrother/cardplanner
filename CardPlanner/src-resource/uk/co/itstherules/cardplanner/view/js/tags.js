function TagsHelper(addedTags, tagField, tagsBag) {
	
	var addedTags = addedTags;
	var tagField = $(tagField)
	var tagsBag = $(tagsBag);

	this.render = function() {
		var tagsHtml = '';
		for(var i = 0; i < addedTags.length; i++) {
			var tag = addedTags[i];
			var tagHtml = '';
			if(''!=tag){
				tagHtml = '<span style="margin-left: 5px; margin-right: 5px;">' + tag + '<a href="javascript:remove(\'' + tag + '\')"><img style="margin-left:5px;" src="'+root+'/Images/Show/0/delete.gif"/></a></span>  ';
				tagsHtml += tagHtml;
			}
		}
		tagsBag.innerHTML = tagsHtml;
	}
		
	this.add = function() {
		var tagFieldValue = tagField.value;
		if(''!=tagFieldValue) {
			addedTags[addedTags.length] = tagFieldValue;
			tagField.value = '';
			this.render();
		}
	}
	
	this.remove = function(tag) {
		var newTags = new Array();
		for(var i = 0; i < addedTags.length; i++) {
			if(addedTags[i] != tag) {
				newTags[newTags.length] = addedTags[i];
			}
		}
		addedTags = newTags;
		this.render();
	}

	this.toString=function() {
		return addedTags.join(',');
	}
}
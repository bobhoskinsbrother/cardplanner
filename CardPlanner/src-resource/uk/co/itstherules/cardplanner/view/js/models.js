var TemplateModels = {

	serialize: function(element, regularExpression) {
	    element = $(element);
	    var ids = Sortable.sequence(element, arguments[1]).map( function(card) { 
	    	if(regularExpression) { card = card.replace(regularExpression, ''); }
	    	return encodeURIComponent(card); 
	    } ).join(',');
	    return ids;
	}

};


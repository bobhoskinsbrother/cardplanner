function CardFinder(cards) {
	
	var cards = cards;
	
	this.topLevelArchived = function() {
		var topCards = new Array();
		for(var cardIndex = 0; cardIndex < cards.length; cardIndex++) {
			var card = cards[cardIndex];
			if(card.parent != null && card.parent.archived == false) {
				topCards.push(card);
			}
		}
		return topCards;
	};
	
	var descendants = function(childCards, parentIdentity) {
		var descendantCards = new CardFinder(cards).children(parentIdentity);
		for(var cardIndex = 0; cardIndex < descendantCards.length; cardIndex++) {
        	childCards[childCards.length] = descendantCards[cardIndex];
        	descendants(childCards, descendantCards[cardIndex].identity);
	    }
		return childCards;
	};
	
	this.children = function(parentIdentity) {
		var childCards = new Array();
		for(var cardIndex = 0; cardIndex < cards.length; cardIndex++) {
			var card = cards[cardIndex];
			if(card.parent != null && (parentIdentity==card.parent.identity || parentIdentity==card.parent)) {
	        	childCards[childCards.length] = card;
	        }
	    }
		return childCards;
	};
	
	this.descendants = function(parentIdentity) {
		var childCards = new Array();
		descendants(childCards, parentIdentity);
		return childCards;
	};

	this.withStatus = function(statusIdentity) {
		var statusCards = new Array();
		for(var cardIndex = 0; cardIndex < cards.length; cardIndex++) {
			var card = cards[cardIndex];
			if(card.status != null && statusIdentity==card.status.identity) {
	        	statusCards[statusCards.length] = card;
	        }
	    }
		return statusCards;
	};

	this.card = function(cardIdentity) {
		for(var cardIndex = 0; cardIndex < cards.length; cardIndex++) {
			var card = cards[cardIndex];
			if(card.identity==cardIdentity) { return card; }
	    }
		return undefined;
	};
	
	this.nullCard = function() {
		for(var cardIndex = 0; cardIndex < cards.length; cardIndex++) {
			var card = cards[cardIndex];
			if(card.invisible) { return card; }
	    }
		return undefined;
	};
	
}
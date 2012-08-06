function ValidatorsExecutor(validators) {

	var validators = validators;

	this.validate = function() {
		
		var errors = new Array();
		var thrown = false;
		
		for ( var i = 0; i < validators.length; i++) {
			try {
				validators[i].validate();
			} catch (error) {
				thrown = true;
				errors[errors.length] = error;
			}
		}
		if (thrown) {
			throw new Errors(errors);
		}
	}
}

function ContentRequiredValidator(message, element) {
	var message = message;
	var element = element;
	this.validate = function() {
		if (element.value.length < 1) {
			throw new Error(message, element);
		}
	}
}

function Errors(errors) {
	var errors = errors;
	this.getErrors = function() {
		return errors;
	}
}

function Error(message, element) {

	var message = message;
	var element = element;

	this.getMessage = function() {
		return message;
	}
	this.getElement = function() {
		return element;
	}
}
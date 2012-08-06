var tests = {
	
	before_all: function() {
		IO.include("uk/co/itstherules/cardplanner/view/js/javascript_extensions.js");
	},

	testTypeOf: function(setupValue) {
		Assert.assertEquals('Expected Type to be string', 'string', Type.of('hi mom'));
		Assert.assertEquals('Expected Type to be number', 'number', Type.of(1337));
		Assert.assertEquals('Expected Type to be object', 'object', Type.of({}));
		Assert.assertEquals('Expected Type to be array', 'array', Type.of([]));
		Assert.assertEquals('Expected Type to be boolean', 'boolean', Type.of(true));
		Assert.assertEquals('Expected Type to be null', 'null', Type.of(null));
		Assert.assertEquals('Expected Type to be function', 'function', Type.of(function(){}));
	},
		
	testIsNumeric: function(setupValue) {
		Assert.assertTrue('Expected numeric Type to be numeric', Type.isNumeric('4567'));
		Assert.assertFalse('Expected number Type to not be numeric', Type.isNumeric(4567));
		Assert.assertFalse('Expected alphanumeric Type to not be numeric', Type.isNumeric('45fred67'));
		Assert.assertFalse('Expected empty Type to not be numeric', Type.isNumeric());
		Assert.assertFalse('Expected null Type to not be numeric', Type.isNumeric(null));
	},
	
	testIsString: function() {
		Assert.assertTrue('Expected string Type to be string', Type.isString('hello_fred'));
		Assert.assertFalse('Expected number Type to not be string', Type.isString(101));
		Assert.assertFalse('Expected object Type to not be string', Type.isString({}));
		Assert.assertFalse('Expected array Type to not be string', Type.isString([]));
		Assert.assertFalse('Expected boolean Type to not be string', Type.isString(false));
		Assert.assertFalse('Expected null Type to not be string', Type.isString(null));
		Assert.assertFalse('Expected function Type to not be string', Type.isString(function(){}));
	},
	
	testIsNumber: function() {
		Assert.assertTrue('Expected number Type to be a number', Type.isNumber(101));
		Assert.assertTrue('Expected decimal Type to be a number', Type.isNumber(101.01));
		Assert.assertFalse('Expected string Type to not be a number', Type.isNumber('hello_fred'));
		Assert.assertFalse('Expected object Type to not be a number', Type.isNumber({}));
		Assert.assertFalse('Expected array Type to not be a number', Type.isNumber([]));
		Assert.assertFalse('Expected boolean Type to not be a number', Type.isNumber(false));
		Assert.assertFalse('Expected null Type to not be a number', Type.isNumber(null));
		Assert.assertFalse('Expected function Type to not be a number', Type.isNumber(function(){}));
	},
	
	testIsObject: function() {
		Assert.assertTrue('Expected object Type to be an object', Type.isObject({}));
		Assert.assertFalse('Expected string Type to not be an object', Type.isObject('hello_fred'));
		Assert.assertFalse('Expected number Type to not be an object', Type.isObject(101));
		Assert.assertFalse('Expected array Type to not be an object', Type.isObject([]));
		Assert.assertFalse('Expected boolean Type to not be an object', Type.isObject(false));
		Assert.assertFalse('Expected null Type to not be an object', Type.isObject(null));
		Assert.assertFalse('Expected function Type to not be an object', Type.isObject(function(){}));
	},
	
	testIsArray: function() {
		Assert.assertTrue('Expected array Type to be an array', Type.isArray([]));
		Assert.assertFalse('Expected string Type to not be an array', Type.isArray('hello_fred'));
		Assert.assertFalse('Expected number Type to not be an array', Type.isArray(101));
		Assert.assertFalse('Expected object Type to not be an array', Type.isArray({}));
		Assert.assertFalse('Expected boolean Type to not be an array', Type.isArray(false));
		Assert.assertFalse('Expected null Type to not be an array', Type.isArray(null));
		Assert.assertFalse('Expected function Type to not be an array', Type.isArray(function(){}));
	},
	
	testIsBoolean: function() {
		Assert.assertTrue('Expected "true" Type to be a boolean', Type.isBoolean(true));
		Assert.assertTrue('Expected "false" Type to be a boolean', Type.isBoolean(false));
		Assert.assertFalse('Expected string Type to not be a boolean', Type.isBoolean('hello_fred'));
		Assert.assertFalse('Expected number Type to not be a boolean', Type.isBoolean(101));
		Assert.assertFalse('Expected object Type to not be a boolean', Type.isBoolean({}));
		Assert.assertFalse('Expected array Type to not be a boolean', Type.isBoolean([]));
		Assert.assertFalse('Expected null Type to not be a boolean', Type.isBoolean(null));
		Assert.assertFalse('Expected boolean Type to not be a boolean', Type.isBoolean(function(){}));
	},
	
	testIsNull: function() {
		Assert.assertTrue('Expected null Type to be null', Type.isNull(null));
		Assert.assertFalse('Expected string Type to not be null', Type.isNull('hello_fred'));
		Assert.assertFalse('Expected number Type to not be null', Type.isNull(101));
		Assert.assertFalse('Expected object Type to not be null', Type.isNull({}));
		Assert.assertFalse('Expected array Type to not be null', Type.isNull([]));
		Assert.assertFalse('Expected boolean Type to be null', Type.isNull(true));
		Assert.assertFalse('Expected boolean Type to be null', Type.isNull(false));
		Assert.assertFalse('Expected function Type to not be null', Type.isNull(function(){}));
	},
	
	testIsFunction: function() {
		Assert.assertTrue('Expected function Type to be function', Type.isFunction(function(){}));
		Assert.assertFalse('Expected null Type to not be function', Type.isFunction(null));
		Assert.assertFalse('Expected string Type to not be function', Type.isFunction('hello_fred'));
		Assert.assertFalse('Expected number Type to not be function', Type.isFunction(101));
		Assert.assertFalse('Expected object Type to not be function', Type.isFunction({}));
		Assert.assertFalse('Expected array Type to not be function', Type.isFunction([]));
		Assert.assertFalse('Expected boolean Type to be function', Type.isFunction(true));
		Assert.assertFalse('Expected boolean Type to be function', Type.isFunction(false));
	}
	
}
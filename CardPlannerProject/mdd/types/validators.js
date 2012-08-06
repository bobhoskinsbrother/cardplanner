(function(expose) {
    expose.validators = {
        required: function(property_name, value, errors) {
            if (value === null || value === '') {
                errors[property_name] = property_name+' is required';
            }
        },
        not_empty: function(property_name, value, errors) {
            if (value && value.length === 0) {
                errors[property_name] = 'Expected not to be empty';
            }
        },
        color: function validate(property_name, color, errors) {
            if (!/^#{0,1}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}[0-9A-Fa-f]{2}$/.match(color)) {
                errors[property_name] = 'Expected a color';
            }
        }
    }
})(exports);
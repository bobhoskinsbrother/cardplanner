var Build = {

    'board': function() {
        return Build.object_from_definition(Define.board());
    },

    'object_from_definition': function(definition) {
        var reply = {};
        for each (var key in definition) {
            var defined = definition[key];
            reply[key] = Build._value_for(defined);
        }
        return reply;
    },

    '_value_for': function(definition) {
        var default_value = Build._default_value[definition.is_a];
        if(!default_value) default_value = Define[definition.is_a]();
        if(!default_value) default_value = undefined;
        return default_value;
    },

    '_default_value': {
        'string': '',
        'number': 0,
        'array': [],
        'boolean':  true
    }
};

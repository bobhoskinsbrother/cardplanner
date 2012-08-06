var Define = {

    'board': function() {
        return Define.mix_in(
            Define['3d_object']('board'),
            {
                'hotspot_areas': Define.array('hotspot_area',{'could_be': []}),
                'text_areas': Define.array('text_area',{'could_be': []}),
                'lines': Define.array('line',{'could_be': []}),
                'frame_type': Define.string({'is_not': ''}),
                'parent': Define.object('board', {'could_be': null })
            });
    },

    'line': function() {
        return Define.mix_in(Define.type('line'), { 'start': Define.point(), 'end': Define.point()});
    },
    
    'hotspot_area': function() {
        return Define['3d_object']('hotspot_area');
    },

    'text_area': function() {
        return Define.mix_in(Define['3d_object']('text_area'), { 'title': Define.string({'is_not': ''}) });
    },

    '3d_object': function(type) {
        return
        Define.mix_in({
            'is_a': type,
            'width': Define.number({'is_not':null}),
            'height': Define.number({'is_not':null}),
            'depth': Define.number({'is_not':null})
        }, Define.point());
    },

    'point': function() {
        return {
            'x': Define.number({'is_not':null}),
            'y': Define.number({'is_not':null}),
            'z': Define.number({'is_not':null})
        };

    },

    'boolean': function() {
        return Define.type('boolean');
    },

    'number': function(constraint) {
        return Define.object('number', constraint);
    },

    'string': function(constraint) {
        return Define.object('string', constraint);
    },

    'email': function(constraint) {
        return Define.object('email', constraint);
    },

    'date': function(constraint) {
        return Define.mix_in(Define.object('date', constraint), Define.day(), Define.month(), Define.year());
    },

    'time': function(constraint) {
        return Define.mix_in(Define.object('time',constraint), Define.hour(), Define.minute(), Define.second(),Define.millisecond());
    },

    'datetime': function(constraint) {
        return Define.mix_in(Define.object('datetime',constraint), Define.date(), Define.time());
    },

    'millisecond': function(constraint) {
        return Define.mix_in(Define.object('millisecond',constraint), Define.range(0,1000));
    },

    'second': function(constraint) {
        return Define.mix_in(Define.object('second',constraint), Define.range(0,60));
    },

    'minute': function(constraint) {
        return Define.mix_in(Define.object('minute',constraint), Define.range(0,60));
    },

    'hour': function(constraint) {
        return Define.mix_in(Define.object('hour',constraint), Define.range(0,60));
    },

    'day': function(constraint) {
        return Define.mix_in(Define.object('day',constraint), Define.range(1,31));
    },

    'week': function(constraint) {
        return Define.mix_in(Define.object('week',constraint), Define.range(1,53));
    },

    'month': function(constraint) {
        return Define.mix_in(Define.object('month',constraint), Define.range(0,23));
    },

    'year': function(constraint) {
        return Define.mix_in(Define.object('year',constraint), Define.range(1,9999));
    },

    'range': function(min, max) {
        return Define.number({'constraint': {'is_in_range': {'min': min, 'max': max}}} );
    },

    'array': function(type, constraint) {
        return Define.mix_in(Define.object('array', constraint), { 'of_type': type });
    },

    'object': function(object_type, constraint) {
        var object = Define.type(object_type);
        if(constraint) object.constraint = constraint;
        return object;
    },

    'type': function(object_type) {
        return { 'is_a': object_type };
    },

    'mix_in': function() {
        var reply = {};
        for(var i = 0; i < arguments.length; i++) {
            var argument = arguments[i];
            for(var key in argument) {
                reply[key] = argument[key];
            }
        }
        return reply;
    }
}
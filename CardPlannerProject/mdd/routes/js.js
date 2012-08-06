exports.validators = function(response, identity, values) {
    var fs = require('fs');
    var path = require('path');
    var util = require('util');
    var filename = 'types/validators.js';
    var stream = fs.createReadStream(filename);
    util.pump(stream, response);
}

exports.models = function(response, identity, values) {
    var fs = require('fs');
    var path = require('path');
    var util = require('util');
    for(var i = 0; i < values.length; i++) {
        var filename = 'types/' + values[i]+'.js';
        var stream = fs.createReadStream(filename);
        util.pump(stream, response);
    }
}
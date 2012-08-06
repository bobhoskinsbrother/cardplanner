exports.file = function(response, identity, values) {
    var fs = require('fs');
    var path = require('path');
    var util = require('util');
    var filename = 'client/'+values[0];
    var stream = fs.createReadStream(filename);
    util.pump(stream, response);
}
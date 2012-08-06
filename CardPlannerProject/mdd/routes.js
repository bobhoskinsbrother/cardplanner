exports.home = function(response, action, identity, values) {
    return require('./routes/home.js')[action](response, identity, values);
}

exports.js = function(response, action, identity, values) {
    return require('./routes/js.js')[action](response, identity, values);
}


exports.client = function(response, action, identity, values) {
    return require('./routes/client.js')[action](response, identity, values);
}
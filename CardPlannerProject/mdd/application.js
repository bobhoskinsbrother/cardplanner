var http = require('http');
var path = require('path');
var routes = require('./routes.js');

var mimeTypes = {
    'html': 'text/html',
    'jpeg': 'image/jpeg',
    'jpg': 'image/jpeg',
    'png': 'image/png',
    'js': 'text/javascript',
    'css': 'text/css'
};

var server = http.createServer(
    function(request, response) {

        var uri = ['home','show','0','index.html'];

        var inbound = request.url.split('/').splice(1);
        for(var i = 0; i < inbound.length; i++) {
            if(''!=inbound[i]) uri[i] = inbound[i];
        }
        var route = routes[uri[0]];
        var action = uri[1];
        var identity = uri[2];
        var values = uri.slice(3);
        response.writeHead(200, {'Content-Type': mimeType});
        var mimeType = mimeTypes[path.extname(uri[uri.length-1]).split('.')[1]];
        if(route) {
            route(response, action, identity, values);
        } else {
            response.writeHead(404, {'Content-Type': mimeType});
            response.end('404 Not Found');
        }

    }).listen(8080);
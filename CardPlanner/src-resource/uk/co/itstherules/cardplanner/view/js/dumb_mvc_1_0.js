if (Model == undefined) {
    var Model = {};
};

if (View == undefined) {
    var View = {
        build: function(nodeType, options, content) {
            var node = $(Builder.node(nodeType, options));
            if (content) {
                if (Object.isString(content)) {
                    node.update(content);
                } else if(content) {
                    node.appendChild(content);
                }
            }
            return node;
        }
    };
}

if (Controller == undefined) {
    var Controller = {};
};

if (Document == undefined) {
    var Document = {};
};

Object.extend(Document, {

    Comms: {

        Controller: {
            destroy: function() {
                Document.Comms.View.destroy();
            }
        },

        View: {

            build: function(type, text) {
                var width = 300;
                var height = 200;
                var documentWidth = Document.View.Dimensions.Width.asNumber();
                var documentHeight = Document.View.Dimensions.Height.asNumber();
                var x = parseInt((documentWidth / 2) - (width / 2));
                var y = parseInt((documentHeight / 2) - (height / 2));
                var messageContainer = View.build('div', { 'id': 'message_container', 'class':'rounded shadow'});
                var messageBackground = View.build('div', { 'id': 'message_background'});
                var message = View.build('div', { 'id': 'message' });
                message.appendChild(View.build('div', { 'id': type + '_message_image' }));
                message.appendChild(View.build('div', { 'id': 'message_text' }, text));
                messageBackground.setStyle({ 'width': documentWidth + 'px', 'height': documentHeight + 'px' });
                messageContainer.setStyle({ 'width': width + 'px','height': height + 'px', 'left':x + 'px', 'top':y + 'px' });
                messageContainer.appendChild(message);
                messageBackground.appendChild(messageContainer);
                return messageBackground;
            },

            destroy: function() {
                $('message_background').fade({
                    duration: 1,
                    afterFinish: function() {
                        $('message_background').remove();
                    }
                });
            }

        },

        Notification: {
            Controller:{
                show: function(message) {
                    Document.Comms.Notification.View.show(message);
                }
            },
            View:{
                show: function(message) {
                    humane.info(message);
                }
            }
        },

        PopOver: {
            Controller:{
                show: function(title, controller, action, identity, file, queryString) {
                    var url = root + '/'+controller+'/'+action+'/' + identity + '/'+file;
                    if(queryString) url += '?'+queryString;
                    lightWindow.activateWindow({
                        href: url,
                        title: title,
                        width: 722,
                        height: 560,
                        type: 'external'
                    });

                }
            }
        },

        Wait: {
            Controller:{
                show: function(message) {
                    Document.Comms.Wait.View.show(message);
                }
            },
            View:{
                show: function(message) {
                    var body = Document.View.body();
                    var waitMessage = Document.Comms.View.build('wait', message);
                    body.appendChild(waitMessage);
                }
            }
        },

        Failure: {
            Controller:{
                flash: function(message) {
                    Document.Comms.Failure.View.flash(message);
                }
            },
            View:{
                flash: function(message) {
                    var body = Document.View.body();
                    var messageView = Document.Comms.View.build('failure', message);
                    body.appendChild(messageView);
                    $('message_background').appear({
                        duration: 0.5,
                        afterFinish: function() {
                            setTimeout(Document.Comms.View.destroy, 3000);
                        }
                    });
                }
            }
        }
    },

    View: {

        Dimensions: {

            _dimension: function(dimension) {
                if (Object.isNumber(window['inner' + dimension])) {
                    return window['inner' + dimension];
                } else if (document.documentElement && document.documentElement['client' + dimension]) {
                    return document.documentElement['client' + dimension];
                } else if (document.body && document.body['client' + dimension]) {
                    return document.body['client' + dimension];
                }
                return 0;
            },

            Width: {
                asNumber: function() {
                    return Document.View.Dimensions._dimension('Width');
                }
            },

            Height: {
                asNumber: function() {
                    return Document.View.Dimensions._dimension('Height');
                }
            }
        },

        body: function() {
            return $$('body')[0];
        },

        dimensionsFor: function(element) {
            var elementXY = element.cumulativeOffset();
            var x = elementXY[0] - StoryBoard.View.Dimensions.X.asNumber();
            var y = elementXY[1] - StoryBoard.View.Dimensions.Y.asNumber();
            return {x:x, y:y, width: element.getWidth(), height: element.getHeight() };
        },

        calculateRelativePointFromAbsolutePoint: function(absoluteDimensions, relativeView) {
            var relativeDimensions = Document.View.dimensionsFor(relativeView);
            return { 'x': (absoluteDimensions.x - relativeDimensions.x), 'y': (absoluteDimensions.y - relativeDimensions.y)};
        },

        calculateRelativePointFromAbsoluteView: function(absoluteView, relativeView) {
            var absoluteDimensions = Document.View.dimensionsFor(absoluteView);
            return Document.View.calculateRelativePointFromAbsolutePoint(absoluteDimensions, relativeView);
        }
    },

    Controller: {

        removeNode: function(identity) {
            $('_' + identity).remove();
        }

    }

});


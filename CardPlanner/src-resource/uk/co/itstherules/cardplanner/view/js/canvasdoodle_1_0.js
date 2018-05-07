Object.extend(CanvasDoodle, {

    currentlyPainting: false,

    initialize: function(element, width, height) {
        CanvasDoodle.parent = $(element);
        CanvasDoodle.identity = '_'+UUID.uuid();
        var elementXY = CanvasDoodle.parent.cumulativeOffset();
        CanvasDoodle.x = elementXY[0];
        CanvasDoodle.y = elementXY[1];
        var options = { 'id': CanvasDoodle.identity, 'width': width, 'height': height };
        CanvasDoodle.canvas = CanvasBuilder.build(options);
        CanvasDoodle.parent.appendChild(CanvasDoodle.canvas);
        CanvasDoodle.context = CanvasDoodle.canvas.getContext('2d');
        CanvasDoodle.eventDown = CanvasDoodle.startPaint.bindAsEventListener(CanvasDoodle);
        CanvasDoodle.eventLeave = CanvasDoodle.outPaint.bindAsEventListener(CanvasDoodle);
        CanvasDoodle.eventUp = CanvasDoodle.endPaint.bindAsEventListener(CanvasDoodle);
        CanvasDoodle.eventMove = CanvasDoodle.updatePaint.bindAsEventListener(CanvasDoodle);
        if(BrowserIntelligence.supportsTouch) {
            Event.observe(CanvasDoodle.canvas, 'touchstart', CanvasDoodle.eventDown);
            Event.observe(CanvasDoodle.canvas, 'touchend', CanvasDoodle.eventUp);
            Event.observe(CanvasDoodle.canvas, 'touchmove', CanvasDoodle.eventMove);
        } else {
            Event.observe(CanvasDoodle.canvas, 'mousedown', CanvasDoodle.eventDown);
            Event.observe(CanvasDoodle.canvas, 'mouseup', CanvasDoodle.eventUp);
            Event.observe(CanvasDoodle.canvas, 'mousemove', CanvasDoodle.eventMove);
        }
    },
    
    resize: function(x, y, oldWidth, oldHeight, newWidth, newHeight) {
        CanvasDoodle.x = x;
        CanvasDoodle.y = y;
        var widthHeight = {'width': newWidth, 'height': newHeight };
        CanvasDoodle.parent.setStyle(new PxDecorator(widthHeight));
        CanvasDoodle.canvas.setAttribute('width', newWidth+'px');
        CanvasDoodle.canvas.setAttribute('height', newHeight+'px');
        CanvasDoodle.Line.applyResizeTransformations((newWidth/oldWidth), (newHeight/oldHeight));
    },
    
    calculateXY: function(pointerXY) {
        var x = (pointerXY.x-CanvasDoodle.x);
        var y = (pointerXY.y-CanvasDoodle.y);
        return { 'x': x, 'y': y};
    },
        
    startPaint: function(event) {
        var point = BrowserIntelligence.pointerXY(event);
        point = { 'x': (point.x-CanvasDoodle.x), 'y': (point.y-CanvasDoodle.y)};
        CanvasDoodle.currentlyPainting = true;
        CanvasDoodle.Line.addNew({'x':point.x,'y':point.y}, CanvasDoodle.Brush);
        CanvasDoodle.paint();
    },
    
    updatePaint: function(event) {
        if(CanvasDoodle.currentlyPainting) {
            var point = BrowserIntelligence.pointerXY(event), current = CanvasDoodle.Line.current;
            point = { 'x': (point.x-CanvasDoodle.x), 'y': (point.y-CanvasDoodle.y)};
            if(CanvasDoodle.Line.locked) {
                current.points[current.points.length-1] = point;
            } else {
                current.points.push(point);
            }
            CanvasDoodle.paint();
        }
    },
    
    outPaint: function(event) {
        CanvasDoodle.currentlyPainting = false;
    },
        
    endPaint: function(event) {
        CanvasDoodle.currentlyPainting = false;
        CanvasDoodle.paint();
    },
            
    Brush: { 'color': '#000000', 'size': 5 }

});

Object.extend(CanvasDoodle.Line, {
    locked: false,
    undoBuffer: [],
    current: {},
    applyResizeTransformations: function(xScale, yScale) {
        var lines = CanvasDoodle.Line.all;
        for(var i = 0; i < lines.length; i++) {
            var points = lines[i].points;
            for(var j = 0; j < points.length; j++) {
                var point = points[j];
                point.x = Math.round((point.x * xScale));
                point.y = Math.round((point.y * yScale));
            }
        }
        CanvasDoodle.clear();
        CanvasDoodle.paint();
    },

    addNew: function(point, brush) {
        var newLine = CanvasDoodle.Line.createNew(point, brush);
        if(CanvasDoodle.Line.locked) { newLine.points.push(point); }
        CanvasDoodle.Line.all.push(newLine);
        CanvasDoodle.Line.current = newLine;
    },

    createNew: function(point, brush) {
        var point = Object.clone(point), brush = Object.clone(brush);
        return { 'points':[ point ], 'brush': brush };
    },

    undoAll: function() {
        CanvasDoodle.Line.undoBuffer = CanvasDoodle.Line.all.clone();
        CanvasDoodle.Line.all = [];
        CanvasDoodle.paint();
    },

    undo: function() {
        if(CanvasDoodle.Line.all.length > 0) {
            CanvasDoodle.Line.undoBuffer.push(CanvasDoodle.Line.all.pop());
            CanvasDoodle.paint();
        }
    },


    redo: function() {
        if(CanvasDoodle.Line.undoBuffer.length > 0) {
            CanvasDoodle.Line.all.push(CanvasDoodle.Line.undoBuffer.pop());
            CanvasDoodle.paint();
        }
    }

});
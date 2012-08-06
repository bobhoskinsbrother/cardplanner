
var CanvasDoodle = {
	
	'initialize': function(element, width, height) {
		CanvasDoodle.parent = $(element);
		CanvasDoodle.identity = '_'+UUID.uuid();
		var elementXY = CanvasDoodle.parent.cumulativeOffset();
		CanvasDoodle.x = elementXY[0];
		CanvasDoodle.y = elementXY[1];
		var options = { 'id': CanvasDoodle.identity, 'width': width, 'height': height };

		CanvasDoodle.canvas = CanvasBuilder.build(options);
		CanvasDoodle.parent.appendChild(CanvasDoodle.canvas);
		CanvasDoodle.context = CanvasDoodle.canvas.getContext('2d');
	},

	'clear': function() {
		CanvasDoodle.context.fillStyle = '#FFFFFF';
		CanvasDoodle.context.fillRect(0, 0, CanvasDoodle.canvas.width, CanvasDoodle.canvas.height);
		CanvasDoodle.canvas.width = CanvasDoodle.canvas.width;
	},
	
	'paint': function() {
		CanvasDoodle.clear();
		CanvasDoodle.context.beginPath();
		CanvasDoodle.context.lineJoin = 'round';

		var lines = CanvasDoodle.Line.all;
		for(var i = 0; i < lines.length; i++) {
			var line = lines[i];
			if(line.points.length > 0) {
				CanvasDoodle.context.lineWidth = line.brush.size;
				CanvasDoodle.context.strokeStyle = line.brush.color;
				CanvasDoodle.context.beginPath();
				CanvasDoodle.context.moveTo(line.points[0].x, line.points[0].y);
				
				for(var j = 1; j < line.points.length; j++) {
					CanvasDoodle.context.lineTo(line.points[j].x, line.points[j].y);
				}
				CanvasDoodle.context.stroke();
				CanvasDoodle.context.closePath();
			}
		}
		CanvasDoodle.context.restore();
	},

	'Line': {
		
		'all': [],

		'addAll': function(lines) {
			var all = CanvasDoodle.Line.all;
			for(var i = 0; i < lines.length; i++) {
				all.push(lines[i]);
			}
			CanvasDoodle.paint();
		}
	}
};
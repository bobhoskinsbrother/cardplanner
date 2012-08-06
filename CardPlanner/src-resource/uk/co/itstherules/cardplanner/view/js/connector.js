var Connectors = {

	register: $H(),
	
	add: function(connector) {
		Connectors.register.set(connector.identity, connector); 
	},
	
	connectPointToConnectorEnd: function(connectorEnd, point, event) { 
		connectorEnd.attachTo(point); 
	},

	_registerPointAsConnectable: function(point, className, accepts) {
		if(!point.step) {
			point.step = point;
		}
		$(point).addClassName(className);
		Droppables.add($(point), { onDrop: Connectors.connectPointToConnectorEnd, greedy: true, accept: accepts });
	},
	
	registerPointAsInputConnectable: function(point) {
		Connectors._registerPointAsConnectable(point, 'connectableInputPoint', 'connectorEndInput');
	},
		
	registerPointAsOutputConnectable: function(point) {
		Connectors._registerPointAsConnectable(point, 'connectableOutputPoint', 'connectorEndOutput');
	}
}

var Connector = Class.create({
	
	initialize: function(identity, options) {
		this.options = Object.extend({
			width: '1024px',
			height: '600px'
		}, options || {});
	
		if(!identity) {
			this.identity = 'connector' + UUID.uuid();
		} else {
			this.identity = identity;
		}
		
		this.container = $(Builder.node('div', { id: this.identity+'Container', className: 'connectorContainer' }));
		this.input = $(Builder.node('div', { id: this.identity+'From', className: 'connectorEndInput', style: 'left: 50px; top: 50px;' }));
		this.connector = $(Builder.node('canvas', { id: this.identity+'Line', className:'connectorLine', 'width': this.options.width, 'height': this.options.height, 'style':'z-index: 1; left: 0px; top:0px;' }));
		this.output = $(Builder.node('div', { id: this.identity+'To', className: 'connectorEndOutput', style: 'left: 50px; top: 70px; ' }));
		
		this.attachToInput = this.attachToInput.bind(this);
		this.attachToOutput = this.attachToOutput.bind(this);

		this.input.widget = this;
		this.output.widget = this;
		
		this.input.attachTo = this.attachTo.bind(this.input);
		this.output.attachTo = this.attachTo.bind(this.output);

		this.input.detach = this.detach.bind(this.input);
		this.output.detach = this.detach.bind(this.output);
		
		new Draggable(this.input, { scroll: window, onDrag: this.drawLine.bind(this), onEnd: this.endDrag.bind(this) });
		new Draggable(this.output, { scroll: window, onDrag: this.drawLine.bind(this), onEnd: this.endDrag.bind(this) });

		this.container.appendChild(this.input);
		this.container.appendChild(this.connector);
		this.container.appendChild(this.output);
		Connectors.add(this);
	},
	
	attachToInput: function(handle) {
		// 'this' refers to the connector object
		var inputConnectionPoints = handle.select('.connectableInputPoint');
		for(var i = 0; i < inputConnectionPoints.length; i++) {
			var inputConnectionPoint = inputConnectionPoints[i];
			if(!inputConnectionPoint.populated) {
				this.input.attachTo(inputConnectionPoint);
				break;
			}
		}
	},

	attachToOutput: function(handle) {
		var outputConnectionPoints = handle.select('.connectableOutputPoint');
		for(var i = 0; i < outputConnectionPoints.length; i++) {
			var outputConnectionPoint = outputConnectionPoints[i];
			if(!outputConnectionPoint.populated) {
				this.output.attachTo(outputConnectionPoint);
				break;
			}
		}
	},

	attachTo: function(connectionPoint) {
		// 'this' refers to either the 'input' or 'output' connectors
		this.addClassName('connected');
		connectionPoint.appendChild(this);
		this.style.left='0px';
		this.style.top='0px';
		connectionPoint.populated = true;
		this.connectionPoint = connectionPoint;
		if(!connectionPoint.connected) {
			connectionPoint.connected = this;
			connectionPoint.step.dragObject = new Draggable($(connectionPoint.step), { scroll: window, onDrag: this.widget.drawLines.bind(this.widget), onEnd: this.widget.endDrag.bind(this.widget) });
		}

		this.widget.drawLine();

		// only allow one to drop onto point
		Droppables.remove(connectionPoint);
	},
	
	detach: function(newTarget) {
		// 'this' refers to either the 'input' or 'output' connection point
		if(this.connectionPoint) {
			var xy = this.connectionPoint.cumulativeOffset()
			var xy2 = newTarget.cumulativeOffset()
			this.connectionPoint.step.dragObject.destroy();
			this.style.left=xy[0]-xy2[0]+'px';
			this.style.top=xy[1]-xy2[1]+'px';
			newTarget.appendChild(this);
		}
	},
	
	doOffset: function(endPoint, connector) {
		var endPointXY = endPoint.viewportOffset();
		var connectorXY = connector.viewportOffset();
		var x = (endPointXY[0] - connectorXY[0]) + (endPoint.getWidth() / 2);
		var y = (endPointXY[1] - connectorXY[1]) + (endPoint.getHeight() / 2);
		var reply = {x:x, y:y};
		return reply;
	},
	
	endDrag: function() {
		this.drawLine();
	},
	
	lineFor: function(widget) {
		var inputOffset = widget.doOffset(widget.input, widget.connector);
		var outputOffset = widget.doOffset(widget.output, widget.connector);
		var context = widget.connector.getContext('2d');
		context.clearRect(0, 0, widget.connector.getWidth(), widget.connector.getHeight());
		context.beginPath();
		context.moveTo(inputOffset.x, inputOffset.y);
		
		var outputYBezier = outputOffset.y;
		var inputYBezier = inputOffset.y;
	
		if(inputOffset.x > outputOffset.x) {
			var bezierDiff = (inputOffset.x - outputOffset.x) / 2;
			var outputXBezier = outputOffset.x + bezierDiff;
			var inputXBezier = inputOffset.x - bezierDiff;
		} else {
			var bezierDiff = (outputOffset.x - inputOffset.x) / 2;
			var outputXBezier = outputOffset.x - bezierDiff;
			var inputXBezier = inputOffset.x + bezierDiff;
		}
		context.bezierCurveTo(inputXBezier, inputYBezier, outputXBezier, outputYBezier, outputOffset.x, outputOffset.y)
		context.lineWidth = 3;
		context.lineCap = 'round';
		context.strokeStyle = "rgb(0,0,0)";  
		context.stroke();
	},
		
	drawLines: function(draggable, event) {
		var handle = draggable.element;
		var connectionPoints = handle.select('.connectableInputPoint').concat(handle.select('.connectableOutputPoint'));
		for(var i = 0; i < connectionPoints.length; i++) {
			var connectionPoint = connectionPoints[i];
			if(connectionPoint.populated) {
				var widget = connectionPoint.connected.widget;
				widget.lineFor(widget);
			}
		}
	},
	
	drawLine: function() {
		this.lineFor(this);
	},
	
	addTo: function(area) {
		$(area).insert(this.container);
		this.drawLine();
	}
});
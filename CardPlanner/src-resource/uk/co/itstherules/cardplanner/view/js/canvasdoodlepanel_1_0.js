var CanvasDoodlePanel = {
		
	'initialize': function(element, color) {
		var panel = CanvasDoodlePanel.View.Button.buildAll(color);
		$(element).appendChild(panel);
	},
	
	'View': {

		'Button': {

			'deselectAll': function() {
			    $$('.status_button_selected').each(function(element) {
			        element.removeClassName('status_button_selected');
			        element.addClassName('status_button');
			    });
			},

			'select': function(element) {
			    CanvasDoodlePanel.View.Button.deselectAll();
                element.addClassName('status_button_selected');
			},

			'buildAll': function(color) {
                var drawingPanel = View.build('div', { 'id': '_'+UUID.uuid() });
				drawingPanel.appendChild(CanvasDoodlePanel.View.Button.Line.build());
				drawingPanel.appendChild(CanvasDoodlePanel.View.Button.Freehand.build());
				drawingPanel.appendChild(CanvasDoodlePanel.View.Button.BrushSize.buildAll(color));
				drawingPanel.appendChild(CanvasDoodlePanel.View.Button.Color.build(color));
				drawingPanel.appendChild(CanvasDoodlePanel.View.Button.State.buildAll(color));
				return drawingPanel;
			},
			
			'build': function(child, title, identity, callback) {
				var buttonView = View.build('div', { 'title': title, 'id': identity, 'class': 'status_button image_button pushButton clickable center' }, child);
				if(callback) { Event.observe(buttonView, 'click', callback); }
				return buttonView;
			},
			
			'buildSmaller': function(child, title, identity, callback) {
				var buttonView = View.build('div', { 'title': title, 'id': identity, 'class': 'status_button smaller_image_button pushButton clickable center' }, child);
				if(callback) { Event.observe(buttonView, 'click', callback); }
				return buttonView;
			},
			
			'BrushSize': {
			
				'buildAll': function(color) {
					var brushSizesButtons = View.build('div', { 'id': 'brush_sizes_buttons' });
					var max = 25;
					var increment = 5;
					var currentSize = increment;
                    CanvasDoodlePanel.View.Button.BrushSize.addButton(2, brushSizesButtons, color);
					while(currentSize <= max) {
						CanvasDoodlePanel.View.Button.BrushSize.addButton(currentSize, brushSizesButtons, color);
						currentSize += increment;
					}
                    
					return brushSizesButtons;
				},

                addButton: function(currentSize, brushSizesButtons, color) {
                    var buttonSize = (currentSize)+2;
                    var options = { 'id': 'brush_size_button_image_off_'+currentSize, 'width': buttonSize, 'height': buttonSize, 'class': 'brush_size_off' };
                    var currentOffButton = CanvasBuilder.build(options);
                    CanvasDoodlePanel.View.Button.BrushSize.drawCircle(currentOffButton, currentSize, '#000000', color);
                    currentOffButton.size = currentSize;

                    Event.observe(currentOffButton, 'click', function() {
                        $$('.brush_size_on').each(function(element) {
                            element.hide();
                        });
                        $$('.brush_size_off').each(function(element) {
                            element.show();
                        });
                        $('brush_size_button_image_on_'+this.size).show();
                        this.hide();
                        CanvasDoodle.Brush.size = this.size;
                    });
                    brushSizesButtons.appendChild(currentOffButton);

                    options = { 'id': 'brush_size_button_image_on_'+currentSize, 'width': buttonSize, 'height': buttonSize, 'class':'brush_size_on', 'style':'display:none' };
                    var currentOnButton = CanvasBuilder.build(options);
                    CanvasDoodlePanel.View.Button.BrushSize.drawCircle(currentOnButton, currentSize, '#000000', '#FFFFFF');
                    brushSizesButtons.appendChild(currentOnButton);
                    brushSizesButtons.appendChild(currentOffButton);

                },
				
				'colorAll': function(color) {
					$$('.brush_size_off').each(function(element) {
						CanvasDoodlePanel.View.Button.BrushSize.drawCircle(element, element.size, '#000000', color);
					});
				},
				
				'drawCircle': function(buttonImage, size, lineColor, fillColor) {
					var buttonSize = (size)+2;
					var halfSize = (size/2);
					var context = buttonImage.getContext('2d');
					context.fillStyle = '#FFFFFF';
					context.fillRect(0, 0, buttonImage.width, buttonImage.height);
					context.restore();
					context.strokeStyle = lineColor;
					context.fillStyle = fillColor;
					context.beginPath();
					context.arc((buttonSize/2), (buttonSize/2), halfSize, 0, Math.PI*2, true);
					context.closePath();
					context.stroke();
					context.fill();
					return buttonImage;
				}
			},
			
			'Line': {
				'build': function() {
					var image = View.build('div', { 'class':'line_button canvasdoodle_button_image ', 'id':'line_button_image' });
					var button = View.build('div', { 'title': 'Draw Line', 'id': 'line_button', 'class': 'status_button image_button pushButton clickable center' }, image);
                    Event.observe(button, 'click', function() {
                        CanvasDoodle.Line.locked = true;
                        CanvasDoodlePanel.View.Button.select($('line_button'));
                    });
					return button;
				}
			},
			
			'Freehand': {
				'build': function() {
					var image = View.build('div', { 'class':'freehand_button canvasdoodle_button_image', 'id':'freehand_button_image' });
					var button = View.build('div', { 'title': 'Draw Freehand', 'id': 'freehand_button', 'class': 'status_button_selected image_button pushButton clickable center' }, image);
                    Event.observe(button, 'click', function() {
                        CanvasDoodle.Line.locked = false;
                        CanvasDoodlePanel.View.Button.select($('freehand_button'));
                    });
					return button;
				}
			},
			'State': {
				
				'buildAll': function() {
					var stateButtons = View.build('div', { 'id': 'state_buttons', 'style':'float:left' });
					stateButtons.appendChild(CanvasDoodlePanel.View.Button.State.Undo.build());
					stateButtons.appendChild(CanvasDoodlePanel.View.Button.State.UndoAll.build());
					stateButtons.appendChild(CanvasDoodlePanel.View.Button.State.Redo.build());
					return stateButtons;
				},
				
				'UndoAll': {
					'build': function() {
						var image = View.build('div', {'class':'undo_all_button canvasdoodle_smaller_button_image', 'id':'undo_all_button_image'});
						var button = CanvasDoodlePanel.View.Button.buildSmaller(image, 'Undo All', 'undo_all_button', function() { CanvasDoodle.Line.undoAll(); });
						return button;
					}
				},
				
				'Undo': {
					'build': function() {
						var image = View.build('div', {'class':'undo_button canvasdoodle_smaller_button_image', 'id':'undo_button_image'});
						var button = CanvasDoodlePanel.View.Button.buildSmaller(image, 'Undo', 'undo_button', function() { CanvasDoodle.Line.undo(); });
						return button;
					}
				},
				
				'Redo': {
					'build': function() {
						var image = View.build('div', {'class':'redo_button canvasdoodle_smaller_button_image', 'id':'redo_button_image'});
						var button = CanvasDoodlePanel.View.Button.buildSmaller(image, 'Redo', 'redo_button', function() { CanvasDoodle.Line.redo(); });
						return button;
					}
				}
			},
			
			'Color': {
			
				'build': function(color) {
					var colorField = View.build('input', { 'value': color, 'id': 'color_picker' });
					Event.observe(colorField, 'change', function() { 
						CanvasDoodle.Brush.color = this.value;
						CanvasDoodlePanel.View.Button.BrushSize.colorAll(CanvasDoodle.Brush.color);
					})
					var picker = new jscolor.color(colorField, { 'hash': true, pickerMode: 'HVS' });
					picker.fromString(color);
					return View.build('div', {'title':'Choose Color', 'id':'color_picker_button'}, colorField);
				}
				
			}
		}

	}
};
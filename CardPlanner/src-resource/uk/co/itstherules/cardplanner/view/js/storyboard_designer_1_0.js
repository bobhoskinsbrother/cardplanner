var StoryBoard = {

    View: {

        get: function() {
            return $('board');
        },

        resize: function(x, y, width, height) {
            StoryBoard.View.Dimensions.X.set(x);
            StoryBoard.View.Dimensions.Y.set(y);
            var oldWidth = StoryBoard.View.Dimensions.Width.start, oldHeight = StoryBoard.View.Dimensions.Height.start;
            if ((oldWidth != width || oldHeight != height)) {
                var xScale = width / oldWidth;
                var yScale = height / oldHeight;
                StoryBoard.View.Dimensions.Width.set(width);
                StoryBoard.View.Dimensions.Height.set(height);
                StoryBoard.Frame.View.Dimensions.Width.set(width);
                StoryBoard.Frame.View.Dimensions.Height.set(height);
                CanvasDoodle.resize(x, y, oldWidth, oldHeight, width, height);
                StoryBoard.HotspotArea.View.resize(xScale, yScale);
                StoryBoard.TextArea.View.resize(xScale, yScale);
            }
        },

        initialize: function(board) {
            Opentip.defaultStyle = 'glass';
            StoryBoard.View.Dimensions.X.set(board.x);
            StoryBoard.View.Dimensions.Y.set(board.y);
            StoryBoard.View.Dimensions.Width.set(board.width);
            StoryBoard.View.Dimensions.Height.set(board.height);
            StoryBoard.Frame.View.Dimensions.Width.set(board.width);
            StoryBoard.Frame.View.Dimensions.Height.set(board.height);
            StoryBoard.Frame.View.get().addEventListener('mousedown', function(event) {
                if (event.preventDefault) event.preventDefault();
            }, false);
            StoryBoard.Resizeables.View.add(StoryBoard.View.get(), {
                'minHeight': 640,
                'minWidth': 480,
                'maxHeight': 7680,
                'maxWidth':  4320,
                'resizeEnd': StoryBoard.Controller.resizeEnd,
                'resizeStart': StoryBoard.Controller.resizeStart
            });
            StoryBoard.CanvasDoodle.View.initialize(board.width, board.height);
        },

        dimensionsFor: function(element) {
            var elementXY = element.cumulativeOffset();
            var x = elementXY[0] - StoryBoard.View.Dimensions.X.asNumber();
            var y = elementXY[1] - StoryBoard.View.Dimensions.Y.asNumber();
            return {x:x, y:y, width: element.getWidth(), height: element.getHeight() };
        },

        centerPointFor: function(element) {
            var elementXY = element.cumulativeOffset();
            var x = ((elementXY[0]) + (element.getWidth() / 2)) - StoryBoard.View.Dimensions.X.asNumber();
            var y = ((elementXY[1]) + (element.getHeight() / 2)) - (StoryBoard.View.Dimensions.Y.asNumber() * 2);
            return {x:x, y:y};
        },

        isHotspotsActive: true,

        Dimensions: {
            X: {
                asPixels: function() {
                    return StoryBoard.View.Dimensions.X.asNumber() + 'px';
                },
                asNumber: function() {
                    return StoryBoard.View.get().cumulativeOffset()[0];
                },
                set: function(x) {
                    StoryBoard.View.get().setStyle({'left': x + 'px'});
                    StoryBoard.View.get().setAttribute('left', x + 'px');
                }
            },

            Y: {
                asPixels: function() {
                    return StoryBoard.View.Dimensions.Y.asNumber() + 'px';
                },
                asNumber: function() {
                    return StoryBoard.View.get().cumulativeOffset()[1];
                },
                set: function(y) {
                    StoryBoard.View.get().setStyle({'top': y + 'px'});
                    StoryBoard.View.get().setAttribute('top', y + 'px');
                }
            },
            Width:  {
                asPixels: function() {
                    return StoryBoard.View.Dimensions.Width.asNumber() + 'px';
                },
                asNumber: function() {
                    return StoryBoard.View.get().getWidth();
                },
                set: function(width) {
                    StoryBoard.View.get().setStyle({'width': width + 'px'});
                    StoryBoard.View.get().setAttribute('width', width + 'px');
                }
            },
            Height:  {
                asPixels: function() {
                    return StoryBoard.View.Dimensions.Height.asNumber() + 'px';
                },
                asNumber: function() {
                    return StoryBoard.View.get().getHeight();
                },
                set: function(height) {
                    StoryBoard.View.get().setStyle({'height': height + 'px'});
                    StoryBoard.View.get().setAttribute('height', height + 'px');
                }
            }
        }

    },

    Controller: {

        initialize: function(board) {
            StoryBoard.View.initialize(board);
            StoryBoard.HotspotArea.Controller.addAll(board.hotspotAreas);
            StoryBoard.TextArea.Controller.addAll(board.textAreas);
            StoryBoard.Line.Controller.addAll(board.lines);
            StoryBoard.Frame.Controller.changeTo(board.frameType);
            StoryBoard.Toolbar.Controller.initialize();
            Document.Comms.Controller.destroy();
        },

        useBoard: function() {
            document.location = root + '/StoryBoard/Show/' + Model.card.identity + '/index.xhtml';
        },

        resizeEnd: function(element) {
            var elementXY = element.cumulativeOffset();
            StoryBoard.View.resize(elementXY[0], elementXY[1], element.getWidth(), element.getHeight());
        },

        resizeStart: function(element) {
            StoryBoard.View.Dimensions.Width.start = parseInt(Element.getStyle(element, 'width'));
            StoryBoard.View.Dimensions.Height.start = parseInt(Element.getStyle(element, 'height'));
        },

        add: function(element) {
            StoryBoard.View.get().appendChild(element);
        },

        saved: function() {
            Document.Comms.Controller.destroy();
            StoryBoard.Frame.View.flash('#00FF00');
        },

        saveFailed: function(reason) {
            Document.Comms.Controller.destroy();
            Document.Comms.Failure.Controller.flash(reason);
            StoryBoard.Frame.View.flash('#FF0000');
        },

        save: function() {
            Document.Comms.Wait.Controller.show('Saving board layout.  Just a sec...');
            var parameters = {};
            var boardView = StoryBoard.View;
            if (!boardView.isHotspotsActive) {
                StoryBoard.Toolbar.HotspotsButton.Show.Controller.now();
            }

            parameters['identity'] = Model.board.identity;
            parameters['width'] = boardView.Dimensions.Width.asNumber();
            parameters['height'] = boardView.Dimensions.Height.asNumber();
            parameters['x'] = boardView.Dimensions.X.asNumber();
            parameters['y'] = boardView.Dimensions.Y.asNumber();
            parameters['frameType'] = StoryBoard.Frame.View.frameType;
            var textAreasCount = 0;
            var lines = StoryBoard.Line.View.all();
            for (var i = 0; i < lines.length; i++) {
                var line = lines[i];
                parameters['lines.' + i + '.brush.color'] = line.brush.color;
                parameters['lines.' + i + '.brush.size'] = line.brush.size;
                for (var j = 0; j < line.points.length; j++) {
                    var point = line.points[j];
                    parameters['lines.' + i + '.points.' + j + '.x'] = point.x;
                    parameters['lines.' + i + '.points.' + j + '.y'] = point.y;
                }
            }
            StoryBoard.TextArea.View.all().each(function(textArea) {
                var textAreaDimensions = boardView.dimensionsFor(textArea);
                var textAreaInner = textArea.select('.editable_text_area_inner')[0];
                parameters['textAreas.' + textAreasCount + '.title'] = textAreaInner.textContent;
                parameters['textAreas.' + textAreasCount + '.x'] = textAreaDimensions.x;
                parameters['textAreas.' + textAreasCount + '.y'] = textAreaDimensions.y;
                parameters['textAreas.' + textAreasCount + '.width'] = textAreaDimensions.width;
                parameters['textAreas.' + textAreasCount + '.height'] = textAreaDimensions.height;
                textAreasCount++;
            });
            var hotspotAreasCount = 0;
            StoryBoard.HotspotArea.View.all().each(function(hotspotArea) {
                var hotspotAreaDimensions = boardView.dimensionsFor(hotspotArea);
                parameters['hotspotAreas.' + hotspotAreasCount + '.x'] = hotspotAreaDimensions.x;
                parameters['hotspotAreas.' + hotspotAreasCount + '.y'] = hotspotAreaDimensions.y;
                parameters['hotspotAreas.' + hotspotAreasCount + '.width'] = hotspotAreaDimensions.width;
                parameters['hotspotAreas.' + hotspotAreasCount + '.height'] = hotspotAreaDimensions.height;
                var statusHeader = hotspotArea.select('.editable_hotspot_area_inner')[0];
                parameters['hotspotAreas.' + hotspotAreasCount + '.status.identity'] = statusHeader.readAttribute('id').substring(1);
                hotspotAreasCount++;
            });

            new Ajax.Request(root + '/StoryBoard/Update/' + Model.board.identity + '/' + Model.board.title, {
                method: 'post',
                parameters: parameters,
                onFailure: function() {
                    StoryBoard.Controller.saveFailed('Board failed to save');
                },
                onException: function() {
                    StoryBoard.Controller.saveFailed('Board raised an exception when trying to save');
                },
                onSuccess: function() {
                    StoryBoard.Controller.saved();
                }
            });
        }

    },



    CanvasDoodle: {

        View:{
            target: function() {
                return $('doodle_target');
            },
            initialize: function(width, height) {
                CanvasDoodle.initialize(StoryBoard.CanvasDoodle.View.target(), width, height);
            }
        }

    },

    CloseButton: {

        View: {
            build: function(callback) {
                var closeButtonView = View.build('img', { 'style':'float:right','src': root + '/Images/Show/0/delete.png', 'class': 'close_button clickable' });
                Event.observe(closeButtonView, 'click', callback);
                return closeButtonView;
            },

            all: function() {
                return $$('.close_button');
            }
        }
    },

    Frame: {

        View:{
            Dimensions: {
                X: {
                    asPixels: function() {
                        return StoryBoard.Frame.View.Dimensions.X.asNumber() + 'px';
                    },
                    asNumber: function() {
                        return StoryBoard.Frame.View.get().cumulativeOffset()[0];
                    },
                    set: function(x) {
                        StoryBoard.Frame.View.get().setStyle({'left': x + 'px'});
                        StoryBoard.Frame.View.get().setAttribute('left', x + 'px');
                    }
                },

                Y: {
                    asPixels: function() {
                        return StoryBoard.Frame.View.Dimensions.Y.asNumber() + 'px';
                    },
                    asNumber: function() {
                        return StoryBoard.Frame.View.get().cumulativeOffset()[1];
                    },
                    set: function(y) {
                        StoryBoard.Frame.View.get().setStyle({'top': y + 'px'});
                        StoryBoard.Frame.View.get().setAttribute('top', y + 'px');
                    }
                },
                Width:  {
                    asPixels: function() {
                        return StoryBoard.Frame.View.Dimensions.Width.asNumber() + 'px';
                    },
                    asNumber: function() {
                        return StoryBoard.Frame.View.get().getWidth();
                    },
                    set: function(width) {
                        StoryBoard.Frame.View.get().setStyle({'width': width + 'px'});
                        StoryBoard.Frame.View.get().setAttribute('width', width + 'px');
                    }
                },
                Height:  {
                    asPixels: function() {
                        return StoryBoard.Frame.View.Dimensions.Height.asNumber() + 'px';
                    },
                    asNumber: function() {
                        return StoryBoard.Frame.View.get().getHeight();
                    },
                    set: function(height) {
                        StoryBoard.Frame.View.get().setStyle({'height': height + 'px'});
                        StoryBoard.Frame.View.get().setAttribute('height', height + 'px');
                    }
                }
            },
            frameType: 'large_tv',
            get: function() {
                return $('frame_image');
            },
            change: function(frame) {
                StoryBoard.Frame.View.frameType = frame;
                StoryBoard.Frame.View.get().setAttribute('src', root + '/Images/Show/0/frame_' + frame + '.png');
            },
            flash: function(color) {
                new Effect.Highlight(StoryBoard.Frame.View.get(), { keepBackgroundImage: true, startcolor: color });
            }
        },

        Controller:{
            changeTo: function(newFrame) {
                StoryBoard.Frame.View.change(newFrame);
            }
        }

    },

    HotspotArea: {

        Controller:{

            resizeEnd: function(element) {
                StoryBoard.HotspotArea.View.resizeInner(element);
            },

            addAll: function(hotspotAreas) {
                for (var index = 0; index < hotspotAreas.length; index++) {
                    var hotspotArea = hotspotAreas[index];
                    StoryBoard.HotspotArea.Controller.add(hotspotArea);
                }
            },


            add: function(hotspotArea) {
                var hotspotAreaView = StoryBoard.HotspotArea.View.build(hotspotArea.identity, hotspotArea.status, hotspotArea.x, hotspotArea.y, hotspotArea.width, hotspotArea.height);
                StoryBoard.Controller.add(hotspotAreaView);
            },

            addWithOffset: function(identity) {
                for (var index in Model.statuses) {
                    var status = Model.statuses[index];
                    if (identity == status.identity) {
                        var offsetMultiplier = StoryBoard.HotspotArea.View.all().length;
                        if (offsetMultiplier == 0) {
                            offsetMultiplier = 1;
                        }
                        var nextX = 100 + (5 * offsetMultiplier);
                        var height = StoryBoard.View.Dimensions.Height.asNumber() - 10;
                        var halfOfTextAreaWidth = 60;
                        var hotspotAreaWidth = ((StoryBoard.View.Dimensions.Width.asNumber() / Model.statuses.length) - 10);
                        var halfOfHotspotAreaWidth = (hotspotAreaWidth / 2);
                        var hotspotAreaView = StoryBoard.HotspotArea.View.build(UUID.uuid(), status, nextX, 1, hotspotAreaWidth, height);
                        var textX = (nextX + halfOfHotspotAreaWidth) - halfOfTextAreaWidth;
                        var editableTextView = StoryBoard.TextArea.View.build(UUID.uuid(), status.title, { x: textX, y: 10 });
                        StoryBoard.Controller.add(hotspotAreaView);
                        StoryBoard.Controller.add(editableTextView);
                    }
                }
            }
        },

        View:{

            build: function(identity, status, x, y, width, height) {
                var storyBoardHeight = StoryBoard.View.Dimensions.Height.asNumber();
                if (height > storyBoardHeight) height = storyBoardHeight;
                if (y < 1) y = 1;
                var hotspotAreaView = View.build('div', { 'id': '_' + identity, 'class': 'dashed_border editable_hotspot_area', 'style':'position: absolute; left: ' + x + 'px; top: ' + y + 'px; width: ' + width + 'px; height: ' + height + 'px' });
                var hotspotAreaInnerView = View.build('div', { 'id': '_' + status.identity, 'class': 'editable_hotspot_area_inner', 'style': 'width: ' + (width - 10) + 'px; height: ' + (height - 10) + 'px' }, status.title);
                var closeButtonView = StoryBoard.CloseButton.View.build(function() {
                    Document.Controller.removeNode(identity)
                });
                hotspotAreaInnerView.appendChild(closeButtonView);
                hotspotAreaView.appendChild(hotspotAreaInnerView);
                new Draggable(hotspotAreaView, { handle: hotspotAreaInnerView });
                StoryBoard.Resizeables.View.add(hotspotAreaView, {'resizeEnd': StoryBoard.HotspotArea.Controller.resizeEnd });
                return hotspotAreaView;
            },

            all: function() {
                return $$('.editable_hotspot_area');
            },

            resize: function(xScale, yScale) {
                StoryBoard.HotspotArea.View.all().each(function(element) {
                    var dimensions = StoryBoard.View.dimensionsFor(element);
                    var x = dimensions.x * xScale;
                    var y = dimensions.y * yScale;
                    var width = dimensions.width * xScale;
                    var height = dimensions.height * yScale;

                    var storyBoardHeight = StoryBoard.View.Dimensions.Height.asNumber();
                    if (height > storyBoardHeight) height = storyBoardHeight;
                    if (y < 1) y = 1;

                    element.setStyle(new PxDecorator({'width': width, 'height': height, 'left': x, 'top': y }));
                    StoryBoard.HotspotArea.View.resizeInner(element);
                });
            },

            resizeInner: function(element) {
                var style = new PxDecorator({'width': '95%', 'height': '95%' }), inner = element.select('.editable_hotspot_area_inner')[0];
                inner.setStyle(style);
            }
        }

    },

    Hotspots: {

        Controller:{

            hide: function() {
                StoryBoard.View.isHotspotsActive = false;
                StoryBoard.Resizeables.View.removeAll();
                StoryBoard.View.get().removeClassName('dashed_border');
                StoryBoard.WindowHandle.View.all().each(function(element) {
                    element.setStyle({ 'visibility': 'hidden' });
                });
                StoryBoard.TextArea.View.all().each(function(element) {
                    element.removeClassName('dashed_border');
                });
                StoryBoard.CloseButton.View.all().each(function(element) {
                    element.toggle();
                });
                StoryBoard.HotspotArea.View.all().each(function(element) {
                    element.toggle();
                });
            },

            show: function() {
                StoryBoard.View.isHotspotsActive = true;
                var board = StoryBoard.View.get();
                StoryBoard.Resizeables.View.add(board, {'resizeEnd': StoryBoard.Controller.resizeEnd, 'resizeStart': StoryBoard.Controller.resizeStart });
                board.addClassName('dashed_border');
                StoryBoard.WindowHandle.View.all().each(function(element) {
                    element.setStyle({ 'visibility': 'visible' });
                });
                StoryBoard.TextArea.View.all().each(function(element) {
                    element.addClassName('dashed_border');
                    StoryBoard.Resizeables.View.add(element, {});
                });
                StoryBoard.CloseButton.View.all().each(function(element) {
                    element.toggle();
                });
                StoryBoard.HotspotArea.View.all().each(function(element) {
                    StoryBoard.Resizeables.View.add(element, {'resizeEnd': StoryBoard.HotspotArea.Controller.resizeEnd });
                    element.toggle();
                });
            }

        }

    },

    Line: {

        Controller:{
            addAll: function(lines) {
                CanvasDoodle.Line.addAll(lines);
            }
        },

        View:{
            all: function() {
                return CanvasDoodle.Line.all;
            }
        }


    },

    Resizeables: {

        View:{
            _a: [],

            add: function(element, options) {
                StoryBoard.Resizeables.View._a.push(new Resizeable(element, options));
            },

            removeAll: function() {
                var resizeables = StoryBoard.Resizeables.View._a;
                for (var i = 0; i < resizeables.length; i++) {
                    resizeables[i].destroy();
                }
            }
        }

    },

    Shim: {

        View: {
            build: function() {
                return View.build('img', { 'src': root + '/Images/Show/0/blank.png' });
            }
        }

    },

    Status: {

        Controller:{
            add: function() {
                lightWindow.activateWindow({
                    href: root + '/Statuses/List/0/index.xhtml',
                    title: 'Statuses',
                    width: 722,
                    height: 560,
                    type: 'external'
                });
            }
        }

    },

    TextArea: {

        Controller:{
            addAll: function(textAreas) {
                for (var index = 0; index < textAreas.length; index++) {
                    var textArea = textAreas[index];
                    StoryBoard.TextArea.Controller.add(textArea.identity, textArea.title, { x: textArea.x, y: textArea.y, width: textArea.width, height: textArea.height });
                }
            },
            add: function(identity, text, options) {
                StoryBoard.Controller.add(StoryBoard.TextArea.View.build(identity, text, options));
            },

            addWithOffset: function(text) {
                var offsetMultiplier = StoryBoard.TextArea.View.all().length;
                if (offsetMultiplier == 0) {
                    offsetMultiplier = 1;
                }
                var offset = (5 * offsetMultiplier);
                StoryBoard.TextArea.Controller.add(UUID.uuid(), text, { x: 100 + offset, y: 100 + offset });
            }
        },

        View:{
            build: function(identity, text, options) {
                var textAreaInner = View.build('div', { 'class':'editable_text_area_inner' }, text);
                textAreaInner.contentEditable = true;
                var textArea = View.build('div', {'id':'_' + identity, 'class':'editable_text_area dashed_border', 'style': 'position: absolute; top:' + options.y + 'px; left:' + options.x + 'px; width: ' + options.width + 'px; height: ' + options.height + 'px;'});
                var textAreaHandle = View.build('div', { 'class':'window_handle editable_text_area_handle' }, ' ');
                var shimView = StoryBoard.Shim.View.build();
                var closeButtonView = StoryBoard.CloseButton.View.build(function() {
                    Document.Controller.removeNode(identity)
                });
                textArea.appendChild(textAreaHandle);
                textAreaHandle.appendChild(shimView);
                textAreaHandle.appendChild(closeButtonView);
                textArea.appendChild(View.build('div', { 'style':'clear: both;' }));
                textArea.appendChild(textAreaInner);
                textArea.appendChild(View.build('div', { 'style':'clear: both;' }));
                StoryBoard.Resizeables.View.add(textArea, {});
                new Draggable(textArea, { handle: textAreaHandle });
                return textArea;
            },

            all: function() {
                return $$('.editable_text_area');
            },

            resize: function(xScale, yScale) {
                StoryBoard.TextArea.View.all().each(function(element) {
                    var dimensions = StoryBoard.View.dimensionsFor(element);
                    var x = dimensions.x * xScale;
                    var y = dimensions.y * yScale;
                    element.setStyle(new PxDecorator({'left': x, 'top': y }));
                });
            }
        }

    },

    Toolbar: {

        View:{
            build: function() {
                var toolBar = View.build('div', {'id': 'tool_bar', 'class': 'solid_border shadow'});
                var toolBarHandle = View.build('div', {'id': 'tool_bar_handle', 'class': 'window_handle'});
                var shimView = StoryBoard.Shim.View.build();
                var toolBarBody = View.build('div', {'id': 'tool_bar_body'});

                toolBarHandle.appendChild(shimView);
                toolBar.appendChild(toolBarHandle);
                toolBar.appendChild(toolBarBody);
                return toolBar;
            }
        },

        Controller:{
            initialize: function() {
                var toolBar = StoryBoard.Toolbar.View.build();
                Document.View.body().appendChild(toolBar);
                new Draggable('tool_bar', { handle: 'tool_bar_handle' });
                StoryBoard.Toolbar.ToolButtons.Controller.add(toolBar);
                StoryBoard.Toolbar.CanvasDoodleSeparator.Controller.add(toolBar);
                StoryBoard.Toolbar.CanvasDoodleButtons.Controller.add(toolBar);
                toolBar.setStyle({ 'left': (StoryBoard.View.Dimensions.Width.asNumber() - toolBar.getWidth() - 75) + 'px' });
            }
        },

        ToolButtons: {

            Controller: {
                add: function(container) {
                    container.appendChild(StoryBoard.Toolbar.ToolButtons.View.build());
                }
            },

            View:{
                build: function() {
                    var container = View.build('div', {});
                    var ibv = StoryBoard.Toolbar.ImageButton.View;
                    container.appendChild(ibv.build('text_button_image', 'Add Text', 'text_button', function() {
                        StoryBoard.TextArea.Controller.addWithOffset('Change Me');
                    }));
                    container.appendChild(ibv.build('use_board_button_image', 'Use Board', 'use_board_button', function() {
                        StoryBoard.Controller.useBoard();
                    }));
                    container.appendChild(StoryBoard.Toolbar.HotspotsButton.View.build());
                    container.appendChild(StoryBoard.Toolbar.ChangeFrameButton.View.build());
                    container.appendChild(ibv.build('add_status_button_image', 'Add Status', 'addHotspotAreaButton', function() {
                        StoryBoard.Status.Controller.add();
                    }));
                    container.appendChild(ibv.build('save_button_image', 'Save', 'saveButton', function() {
                        StoryBoard.Controller.save();
                    }));
                    return container;
                }
            }
        },

        HotspotsButton: {
            View:{
                build: function() {
                    var button = StoryBoard.Toolbar.HotspotsButton;
                    var container = View.build('div', {});
                    container.appendChild(button.Hide.View.build());
                    container.appendChild(button.Show.View.build());
                    container.appendChild(button.Add.View.build());
                    return container;
                },

                toggle: function() {
                    $('hotspots_hide_button').toggle();
                    $('hotspots_show_button').toggle();
                }
            },
            Hide: {
                Controller: {
                    now: function() {
                        StoryBoard.Toolbar.HotspotsButton.View.toggle();
                        StoryBoard.Hotspots.Controller.hide();
                    }
                },
                View:{
                    build: function() {
                        return StoryBoard.Toolbar.ImageButton.View.build('hotspots_hide_button_image', 'Hide Hotspots', 'hotspots_hide_button', StoryBoard.Toolbar.HotspotsButton.Hide.Controller.now);
                    }
                }
            },
            Show: {
                Controller: {
                    now: function() {
                        StoryBoard.Toolbar.HotspotsButton.View.toggle();
                        StoryBoard.Hotspots.Controller.show();
                    }                    },
                View:{
                    build: function() {
                        var showButton = StoryBoard.Toolbar.ImageButton.View.build('hotspots_show_button_image', 'Show Hotspots', 'hotspots_show_button', StoryBoard.Toolbar.HotspotsButton.Show.Controller.now);
                        showButton.setStyle({'display':'none'});
                        return showButton;
                    }
                }
            },
            Add: {
                View:{
                    build: function() {
                        var button = StoryBoard.Toolbar.ImageButton.View.build('hotspots_add_button_image', 'Add Hotspot', 'hotspots_add_button');
                        button.addTip(StoryBoard.Toolbar.HotspotAreaButtons.View.build, { 'fixed': true, 'stem': true, 'showOn': 'click', 'hideTrigger': 'closeButton' });
                        return button;
                    }
                }
            }
        },

        CanvasDoodleSeparator: {
            Controller:{
                add: function(container) {
                    container.appendChild(StoryBoard.Toolbar.Separator.View.build('Drawing'));
                }
            }
        },

        CanvasDoodleButtons: {
            Controller: {
                add: function(container) {
                    container.appendChild(StoryBoard.Toolbar.CanvasDoodleButtons.View.build());
                }
            },
            View:{
                build: function() {
                    var container = View.build('div', {});
                    CanvasDoodlePanel.initialize(container, CanvasDoodle.Brush.color);
                    return container;
                }
            }
        },

        ImageButton: {
            View:{
                build: function(image, title, identity, callback) {
                    var buttonImage = View.build('div', { 'id': image, 'class': 'button_image' });
                    var buttonView = View.build('div', { 'title': title, 'id': identity, 'class': 'status_button image_button pushButton clickable center' }, buttonImage);
                    if (callback) {
                        Event.observe(buttonView, 'click', callback);
                    }
                    return buttonView;
                }
            }
        },

        TextButton: {
            View:{
                build: function(text, title, identity, callback) {
                    var buttonView = View.build('div', { 'title': title, 'id': identity, 'class': 'status_button text_button pushButton clickable center' }, text);
                    if (callback) {
                        Event.observe(buttonView, 'click', callback);
                    }
                    return buttonView;
                }
            }
        },

        ChangeFrameButtons: {
            View:{
                build: function() {
                    var container = View.build('div', { 'id': 'changeFrameButtons' });
                    container.appendChild(StoryBoard.Toolbar.Separator.View.build('Change Frame'));
                    container.appendChild(StoryBoard.Shim.View.build());
                    container.appendChild(StoryBoard.Toolbar.TextButton.View.build('For SmartBoards', 'Change Frame', 'changeFrameSmartboard', function() {
                        StoryBoard.Frame.Controller.changeTo('smartboard');
                    }));
                    container.appendChild(StoryBoard.Toolbar.TextButton.View.build('For Projectors', 'Change Frame', 'changeFrameProjector', function() {
                        StoryBoard.Frame.Controller.changeTo('projector');
                    }));
                    container.appendChild(StoryBoard.Toolbar.TextButton.View.build('For Large TVs', 'Change Frame', 'changeFrameLargeTv', function() {
                        StoryBoard.Frame.Controller.changeTo('large_tv');
                    }));
                    container.appendChild(StoryBoard.Toolbar.TextButton.View.build('For Monitors', 'Change Frame', 'changeFrameMonitor', function() {
                        StoryBoard.Frame.Controller.changeTo('monitor');
                    }));
                    container.appendChild(StoryBoard.Toolbar.Separator.View.build(''));
                    return container;
                }
            }
        },

        ChangeFrameButton: {
            View:{
                build: function() {
                    var button = StoryBoard.Toolbar.ImageButton.View.build('change_frame_button_image', 'Change Frame', 'changeFrameButton');
                    button.addTip(StoryBoard.Toolbar.ChangeFrameButtons.View.build, { 'fixed': true, 'stem': true, 'showOn': 'click', 'hideTrigger': 'closeButton' });
                    return button;
                }
            }
        },

        HotspotAreaButtons: {
            View:{
                build: function() {
                    var container = View.build('div', { });
                    var statuses = Model.statuses;
                    container.appendChild(StoryBoard.Toolbar.Separator.View.build('Add Hotspot'));
                    container.appendChild(StoryBoard.Shim.View.build());
                    if (statuses.length > 0) {
                        for (var statusIndex = 0; statusIndex < statuses.length; statusIndex++) {
                            container.appendChild(StoryBoard.Toolbar.HotspotAreaButton.View.build(statuses[statusIndex]));
                        }
                    } else {
                        container.appendChild(View.build('div', { 'id': 'no_statuses_defined_toolbar_panel' }, 'There have been no statuses defined'));
                    }
                    container.appendChild($(Builder.node('div', { style: 'clear: both;' })));
                    return container;
                }
            }
        },

        HotspotAreaButton: {
            View:{
                build: function(status) {
                    var statusView = View.build('div', { 'title': status.title, 'id': '_' + status.identity, 'class': 'status_button text_button rounded solid_border clickable center' }, status.title);
                    Event.observe(statusView, 'click', function() {
                        StoryBoard.HotspotArea.Controller.addWithOffset(this.id.substring(1));
                    });
                    return statusView;
                }
            }
        },

        Separator: {
            View:{
                build: function(text) {
                    return View.build('div', {'style':'text-align:center; clear: both;'}, text);
                }
            }
        }
    },

    WindowHandle: {

        View:{
            all: function() {
                return StoryBoard.View.get().select('.window_handle');
            }
        }

    }

};
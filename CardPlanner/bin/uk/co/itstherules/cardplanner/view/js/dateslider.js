Date.prototype.getDiffDays = function(p_oDate) {
	p_iOneDay = 1000 * 60 * 60 * 24;
	return Math.ceil((p_oDate.getTime() - this.getTime()) / (p_iOneDay));
}

function DateSlider(p_sBarId, p_sStartDate, p_sEndDate, p_startYear,
		p_iEndYear, newoptions) {
	createSliderBar = function(p_sBarId) {
		var sliderDayDivWidth = options.dayWidth;
		l_iYear = startYear;
		while (l_iYear <= iEndYear) {
			l_oData = Date.parse('01-01-' + l_iYear);
			if (l_oData.isLeapYear())
				iDays = 366;
			else
				iDays = 365;

			divWidth = sliderDayDivWidth * iDays;
			l_oDiv = new Element('div', {
				className : 'slideYear',
				style : 'width:' + (divWidth - 1) + 'px'
			}).update(l_iYear);

			iTotalDays = 0;
			(12).times( function(e) {
				monthDivWidth = l_oData.getDaysInMonth() * options.dayWidth;
				l_oMonthDiv = new Element('div', {
					className : 'slideMonth',
					style : 'width:' + (monthDivWidth) + 'px; left:'
							+ iTotalDays + 'px'
				});
				if (e == 0) {
					$(l_oMonthDiv).addClassName('firstMonth');
				} else {
					$(l_oMonthDiv).update(l_oData.toString("MMM"));
				}
				l_oDiv.appendChild(l_oMonthDiv);
				iTotalDays += monthDivWidth;
				l_oData.addMonths(1);
			});
			$(p_sBarId).appendChild(l_oDiv);
			l_iYear++;
		}

		l_iCorrection = $(p_sBarId).parentNode.offsetWidth / 2;
		l_shiftLeft = 0 - barStartDate.getDiffDays(centerDate)
				* sliderDayDivWidth + l_iCorrection;
		l_oFinishDate = Date.parse((iEndYear + 1) + '-01-01');
		iBarWidth = barStartDate.getDiffDays(l_oFinishDate);
		$(p_sBarId).setStyle( {
			left : l_shiftLeft + 'px',
			width : iBarWidth * sliderDayDivWidth + 'px'
		});

		/* Make the background grid draggable */
		if (options.dragBar) {
			new Draggable($(p_sBarId), {
				snap : sliderLimitPos,
				constraint : 'horizontal',
				starteffect : '',
				endeffect : '',
				zindex : '0'
			});
		}
	};

	function _bgStopDrag() {
		l_iDiff = $(righthandle).offsetLeft + ($(barId).offsetLeft - 600);

		if (l_iDiff > -2) {
			/* Move the bgbar */
			var l_iLeft = '-' + ($(righthandle).offsetLeft - 590) + 'px';
			new Effect.Morph(barId, {
				style : {
					left : l_iLeft
				},
				duration : .5
			});
		}

		/* Call the callback function */
		if (options.onEnd)
			options.onEnd();
	}
	;

	function createHandles(p_sBarId, p_sStartDate, p_sEndDate) {
		l_oLeftHandle = new Element('span', {
			className : leftHandleClass,
			id : lefthandle,
			style : 'left:' + iLeftOffsetLH + 'px'
		}).update('&nbsp;');
		l_oRightHandle = new Element('span', {
			className : rightHandleClass,
			id : righthandle,
			style : 'left:' + iLeftOffsetRH + 'px'
		}).update('&nbsp;');

		$(p_sBarId).appendChild(l_oLeftHandle);
		$(p_sBarId).appendChild(l_oRightHandle);

		if (options.dragHandles) {
			/* Make the left handler draggable */
			new Draggable(l_oLeftHandle, {
				snap : handleLimitPos,
				containment : p_sBarId,
				constraint : 'horizontal',
				onDrag : _leftDrag,
				onEnd : _leftDrag
			});

			/* Make the right handler draggable */
			new Draggable(l_oRightHandle, {
				snap : handleLimitPos,
				containment : p_sBarId,
				constraint : 'horizontal',
				onDrag : _rightDrag,
				onEnd : _rightDrag
			});
		} else {
			l_oLeftHandle.setStyle( {
				opacity : .01,
				cursor : 'pointer'
			});
			l_oRightHandle.setStyle( {
				opacity : .01,
				cursor : 'pointer'
			});
		}
	}
	;

	function dragShiftPanel() {
		$(lefthandle).setStyle( {
			left : ($(shiftPanel).offsetLeft - sliderBarMargin) + 'px'
		});
		$(righthandle)
				.setStyle(
						{
							left : ($(shiftPanel).offsetLeft
									+ $(shiftPanel).offsetWidth - sliderBarMargin) + 'px'
						});
		_setDates();
	}
	;

	function createShiftPanel(p_sBarId, p_sStartDate, p_sEndDate) {
		l_iBarWidth = (iLeftOffsetRH - iLeftOffsetLH) + (2 * sliderBarMargin);

		l_oShiftPanel = new Element('div', {
			id : shiftPanel,
			className : shiftPanelClass,
			style : 'left:' + (iLeftOffsetLH) + 'px; width:' + l_iBarWidth
					+ 'px'
		});
		$(p_sBarId).appendChild(l_oShiftPanel);
		new Draggable(
				l_oShiftPanel,
				{
					snap : handleLimitPos,
					constraint : 'horizontal',
					starteffect : '',
					endeffect : '',
					zindex : '0',
					onEnd : _bgStopDrag.bindAsEventListener(this),
					onDrag : function() {
						/* Set the handlers while dragging the shiftpanel */
						$(lefthandle)
								.setStyle(
										{
											left : ($(shiftPanel).offsetLeft - sliderBarMargin) + 'px'
										});
						$(righthandle)
								.setStyle(
										{
											left : ($(shiftPanel).offsetLeft
													+ $(shiftPanel).offsetWidth - sliderBarMargin) + 'px'
										});
						_setDates();
					},
					onStart : function() {
						if (options.onStart)
							options.onStart();
					}
				});
	}
	;

	function sliderLimitPos(x, y, drag) {
		inbox = drag.element.getDimensions();
		outbox = Element.getDimensions(drag.element.parentNode);
		return [
				x > 0 ? 0 : (x > outbox.width - inbox.width ? x : outbox.width
						- inbox.width), y ];
	}
	;

	function handleLimitPos(x, y, drag) {
		inbox = drag.element.getDimensions();
		outbox = Element.getDimensions(drag.element.parentNode);
		maxPos = drag.element.hasClassName(leftHandleClass) ? parseInt($(righthandle).style.left)
				- inbox.width
				: outbox.width - inbox.width;

		minPos = drag.element.hasClassName(rightHandleClass) ? parseInt($(lefthandle).style.left)
				+ inbox.width
				: 0;
		return [ x > maxPos ? maxPos : (x < minPos ? minPos : x), y ];
	}
	;

	function _setDates() {
		l_iLeftPos = $(lefthandle).offsetLeft / options.dayWidth;
		l_iRightPos = $(righthandle).offsetLeft / options.dayWidth;

		l_oDate = barStartDate.clone().addDays(l_iLeftPos);
		if (numberOfDays == null) {
			l_oDate2 = barStartDate.clone().addDays(l_iRightPos);
		} else {
			l_oDate2 = l_oDate.clone().addDays(numberOfDays);
		}

		if (oStartField && oEndField) {
			oStartField.setValue(l_oDate.toString(options.dateFormat));
			oEndField.setValue(l_oDate2.toString(options.dateFormat));
		}
	}
	;

	function _rightDrag(e, ev) {
		if (ev.type == "mouseup" && options.onEnd != null)
			options.onEnd();
		l_panelLength = $(righthandle).offsetLeft - $(lefthandle).offsetLeft
				- 5;
		$(shiftPanel).setStyle( {
			width : (l_panelLength + 2 * sliderBarMargin) + 'px'
		});
		_setDates();
	}
	;
	function _leftDrag(e, ev) {
		if (ev.type == "mouseup" && options.onEnd != null)
			options.onEnd();
		l_panelLength = $(righthandle).offsetLeft - $(lefthandle).offsetLeft
				- 4;
		$(shiftPanel).setStyle( {
			left : ($(lefthandle).offsetLeft + 4) + 'px',
			width : l_panelLength + 'px'
		});
		_setDates();
	}
	;

	function morphTo(p_oDateStart, p_oDateEnd) {
		l_offsetLeftLH = barStartDate.getDiffDays(p_oDateStart)
				* options.dayWidth;
		l_offsetLeftRH = barStartDate.getDiffDays(p_oDateEnd)
				* options.dayWidth;
		l_panelLength = l_offsetLeftRH - l_offsetLeftLH - 4;
		$(lefthandle).morph('left:' + l_offsetLeftLH + 'px');
		$(righthandle).morph('left:' + l_offsetLeftRH + 'px');
		$(shiftPanel).morph(
				'width : ' + (l_panelLength + 2 * sliderBarMargin)
						+ 'px; left : ' + (l_offsetLeftLH + 2) + 'px');
	}
	;

	this.attachFields = function(p_oStartField, p_oEndField) {
		oStartField = p_oStartField;
		oEndField = p_oEndField;

		p_oStartField.setValue(oStartDate.toString(options.dateFormat));
		p_oEndField.setValue(oEndDate.toString(options.dateFormat));

		[ p_oStartField, p_oEndField ].each( function(e) {
			e.observe('blur', function() {
				l_oStartDate = Date.parse(p_oStartField.getValue());
				l_oEndDate = Date.parse(p_oEndField.getValue());
				morphTo(l_oStartDate, l_oEndDate);
			}); // end observe
			}); // end each
	};

	function _removeSliderBar() {
		$(barId).update('');
	}
	;

	function zoomIn() {
		_zoom(1);
	}
	;

	function zoomOut() {
		_zoom(-1);
	}
	;

	function _zoom(p_iFactor) {
		if ((options.dayWidth + p_iFactor) < 1)
			return;
		l_iLeftPos = $(lefthandle).offsetLeft / options.dayWidth;
		l_iRightPos = $(righthandle).offsetLeft / options.dayWidth;

		l_oDateStart = barStartDate.clone().addDays(l_iLeftPos);
		l_oDateEnd = barStartDate.clone().addDays(l_iRightPos);

		_removeSliderBar();
		options.dayWidth = options.dayWidth + p_iFactor;

		iLeftOffsetLH = barStartDate.getDiffDays(l_oDateStart)
				* options.dayWidth;
		iLeftOffsetRH = barStartDate.getDiffDays(l_oDateEnd) * options.dayWidth;

		createSliderBar(barId);
		createHandles(barId, l_oDateStart, l_oDateEnd);

		createShiftPanel(barId, l_oDateStart, l_oDateEnd);
		centerBar();
	}
	;

	function setZoom() {
		l_oZoomIn = new Element('a', {
			className : 'zoom',
			href : '#'
		}).update('zoom in').observe('click', function(ev) {
			zoomIn();
			ev.stop();
		});

		l_oZoomOut = new Element('a', {
			className : 'zoom',
			href : '#'
		}).update('zoom out').observe('click', function(ev) {
			zoomOut();
			ev.stop();
		});

		l_oZoomPanel = new Element('div', {
			className : 'zoomPanel'
		});

		l_oZoomPanel.appendChild(l_oZoomIn);
		l_oZoomPanel.appendChild(document.createTextNode(' | '))
		l_oZoomPanel.appendChild(l_oZoomOut);

		$(barId).up().appendChild(l_oZoomPanel);
	}
	;

	function centerBar() {

		var l_iPanelWidth = iLeftOffsetRH - iLeftOffsetLH;
		var l_iShiftContainerWidth = $(barId).up().getWidth();

		$(barId)
				.setStyle(
						{
							left : (iLeftOffsetLH - (2 * iLeftOffsetLH)
									+ (l_iShiftContainerWidth / 2) - (l_iPanelWidth / 2)) + 'px'
						});
	}

	var barStartDate = new Date().set( {
		year : p_startYear,
		month : 0,
		day : 1
	});
	var startYear = p_startYear;
	var iEndYear = p_iEndYear;
	var barEndDate = new Date().set( {
		year : p_iEndYear,
		month : 11,
		day : 31
	});
	var oStartDate = Date.parse(p_sStartDate);
	var oEndDate = Date.parse(p_sEndDate);
	var oStartField = null;
	var oEndField = null;
	var sliderBarMargin = 2;
	var l_oStartDate = Date.parse(p_sStartDate);
	var l_oEndDate = Date.parse(p_sEndDate);
	var options = {
		dayWidth : 1,
		dragHandles : true,
		dragBar : true,
		dateFormat : 'd MMM yyyy',
		zoom : false,
		onEnd : null,
		onStart : null
	};
	Object.extend(options, newoptions || {});
	var barId = p_sBarId;
	var shiftPanel = p_sBarId + 'ShiftPanel';
	var lefthandle = p_sBarId + 'LeftHandle';
	var righthandle = p_sBarId + 'RightHandle';
	var shiftPanelClass = 'shiftPanel';
	var leftHandleClass = 'leftHandle';
	var rightHandleClass = 'rightHandle';

	var numberOfDays = null;
	if (options.dragHandles == false)
		numberOfDays = oStartDate.getDiffDays(oEndDate);
	var centerDate = Date.today();
	if (options.centerDate != null)
		centerDate = Date.parse(options['centerDate']);

	var iLeftOffsetLH = barStartDate.getDiffDays(l_oStartDate)
			* options.dayWidth;
	var iLeftOffsetRH = barStartDate.getDiffDays(l_oEndDate) * options.dayWidth;

	createSliderBar(p_sBarId);
	createHandles(p_sBarId, p_sStartDate, p_sEndDate);
	createShiftPanel(p_sBarId, p_sStartDate, p_sEndDate);

	if (options.zoom)
		setZoom();
};
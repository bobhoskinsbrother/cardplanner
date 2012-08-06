Control.ProgressBar = Class.create( {
	initialize : function(container, options) {
		this.progress = 0;
		this.executer = false;
		this.active = false;
		this.poller = false;
		this.container = $(container);
		this.containerWidth = this.container.getDimensions().width
				- (parseInt(this.container.getStyle('border-right-width')
						.replace(/px/, ''), 10) + parseInt(this.container
						.getStyle('border-left-width').replace(/px/, ''), 10));
		this.progressContainer = Builder.node('div');
		this.progressContainer.setStyle( {
			width : this.containerWidth + 'px',
			height : '100%',
			position : 'absolute',
			top : '0px',
			right : '0px'
		});
		this.container.insert(this.progressContainer);
		this.options = {
			afterChange : Prototype.emptyFunction,
			interval : 0.25,
			step : 1,
			classNames : {
				active : 'progress_bar_active',
				inactive : 'progress_bar_inactive'
			}
		};
		Object.extend(this.options, options || {});
		this.container.addClassName(this.options.classNames.inactive);
		this.active = false;
	},
	
	setProgress : function(value) {
		this.progress = value;
		this.drawCanvas();
		if (this.progress >= 100) {
			this.stop(false);
		}
		this.notify('afterChange', this.progress, this.active);
	},
	
	progressTo: function(amount) {
		this.active = true;
		this.executer = new PeriodicalExecuter(this.stepUntil.bind(this, amount), this.options.interval);
	},
	
	stepUntil: function(amount) {
		this.progress = this.progress + 1;
		this.drawCanvas();
		if (this.progress >= amount) {
			this.progress = amount;
			this.stop(false);
			this.notify('afterChange', this.progress, this.active);
		}
		
	},

	start : function() {
		this.active = true;
		this.container.removeClassName(this.options.classNames.inactive);
		this.container.addClassName(this.options.classNames.active);
		this.executer = new PeriodicalExecuter(this.step.bind(this, this.options.step), this.options.interval);
	},
	
	stop : function(reset) {
		this.active = false;
		if (this.executer) {
			this.executer.stop();
		}
		this.container.removeClassName(this.options.classNames.active);
		this.container.addClassName(this.options.classNames.inactive);
		if (typeof reset === 'undefined' || reset === true) {
			this.reset();
		}
	},
	
	step : function(amount) {
		this.active = true;
		this.setProgress(Math.min(100, this.progress + amount));
	},
	
	reset : function() {
		this.active = false;
		this.setProgress(0);
	},
	
	drawCanvas : function() {
		this.progressContainer.setStyle( {
			width : (this.containerWidth - Math.floor((parseInt(this.progress, 10) / 100) * this.containerWidth)) + 'px'
		});
	},
	
	notify : function(event_name) {
		if (this.options[event_name]) {
			return [ this.options[event_name].apply(this.options[event_name], $A(arguments).slice(1)) ];
		}
	}
});
Event.extend(Control.ProgressBar);
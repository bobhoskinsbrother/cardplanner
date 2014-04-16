var BurnUp = Class.create({

    initialize: function(target, days, options) {
        if(!options) options = {};
        Object.extend(options, { width: 800, height: 600, margin: 5 });
        this.options = options;
        this.target = $(target);
        this.canvas = CanvasBuilder.build(options);
        this.target.appendChild(this.canvas);
        this.paint(days);
    },

    paint: function(days) {
        var amountOfDays = days.length;
        var spaceBetweenPoints = (this.options.width - (this.options.margin*2)) / (amountOfDays * 2); // cater for both burn up and down
        var x = this.options.margin;
        var y = 0;
        var points = [];
        for(var i = 0; i < amountOfDays; i++) {
            var day = days[i];
            y = this.calculateY(day.up);
            points.push({'x':x,'y':y});
            x+=spaceBetweenPoints;
            points.push({'x':x,'y':y});
            y = this.calculateY(day.down);
            x+=spaceBetweenPoints;
        }
    },

    calculateY: function(y) {
        var top = this.options.margin;
        var bottom = this.options.height - this.options.margin;

    }

});
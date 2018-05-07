Effect.Resize = Class.create(Effect.Base, {
    initialize: function (element, targetWidth, targetHeight) {
        this.element = $(element);
        if (!this.element) throw(Effect._elementDoesNotExistError);
        var options = Object.extend({
            scaleContent: false,
            increment: 8,
            onUpdate: null,
            onFinish: null,
            targetWidth: targetWidth,
            targetHeight:targetHeight
        }, arguments[3] || { });
        this.start(options);
    },
    setup: function () {
        this.scalingUpWidth = (this.options.targetWidth > this.element.scrollWidth);
        this.scalingUpHeight = (this.options.targetHeight > this.element.scrollHeight);
        this.incrementWidth = (this.options.targetWidth - this.element.scrollWidth) / this.options.increment;
        this.incrementHeight = (this.options.targetHeight - this.element.scrollHeight) / this.options.increment;

    },
    update: function () {
        var currentWidth = this.element.scrollWidth;
        var currentHeight = this.element.scrollHeight;

        var newWidth = currentWidth + this.incrementWidth;
        var newHeight = currentHeight + this.incrementHeight;
        if(newWidth > this.options.targetWidth && this.scalingUpWidth) newWidth = this.options.targetWidth;
        if(newHeight > this.options.targetHeight && this.scalingUpHeight) newHeight = this.options.targetHeight;
        if(newWidth < this.options.targetWidth && !this.scalingUpWidth) newWidth = this.options.targetWidth;
        if(newHeight < this.options.targetHeight && !this.scalingUpHeight) newHeight = this.options.targetHeight;

        this.setDimensions(newWidth, newHeight);
        if(this.options.onUpdate) { this.options.onUpdate(this.element); }
    },
    finish: function () {
        if(this.options.onFinish) { this.options.onFinish(this.element); }
    },
    setDimensions: function (width, height) {
        var d = { };
        d.width = width.round() + 'px';
        d.height = height.round() + 'px';
        this.element.setStyle(d);
    }
});
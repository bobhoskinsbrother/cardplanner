var SideBarView = {

    isExtended: false,
    toggle: function () {
        if (SideBarView.isExtended == false) {
            SideBarView.extend();
        } else {
            SideBarView.retract();
        }
    },
    extend: function () {
        new Effect.BlindDown($('sideBarContents'), { duration: 0.3, scaleX: 'true', scaleY: 'true;', scaleContent: false });
        var tab = $('sideBarTab');
        tab.addClassName("sidebar_tab_image_active");
        tab.removeClassName("sidebar_tab_image");
        new Effect.Fade('sideBarContents', { duration: 0.3, from: 0.0, to: 1.0 });
        SideBarView.isExtended = true;
    },
    retract: function () {
        new Effect.BlindUp($('sideBarContents'), { duration: 0.3, scaleX: 'true', scaleY: 'true;', scaleContent: false });
        var tab = $('sideBarTab');
        tab.addClassName("sidebar_tab_image");
        tab.removeClassName("sidebar_tab_image_active");
        new Effect.Fade('sideBarContents', { duration: 0.3, from: 1.0, to: 0.0 });
        SideBarView.isExtended = false;
    },
    initialize: function () {
        var tab = $('sideBarTab');
        Event.observe(tab, 'click', SideBarView.toggle, true);
        Event.observe(tab, 'touchstart', SideBarView.toggle, true);
    }
};
Event.observe(window, 'dom:loaded', SideBarView.initialize, true);
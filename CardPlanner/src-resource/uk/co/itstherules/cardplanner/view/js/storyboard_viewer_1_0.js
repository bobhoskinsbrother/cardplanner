var StoryBoard = {

    View: {

        initialize: function (board) {
            StoryBoard.View.Dimensions.X.set(board.x);
            StoryBoard.View.Dimensions.Y.set(board.y);
            StoryBoard.View.Dimensions.Width.set(board.width);
            StoryBoard.View.Dimensions.Height.set(board.height);
            StoryBoard.Frame.View.Dimensions.Width.set(board.width);
            StoryBoard.Frame.View.Dimensions.Height.set(board.height);
            StoryBoard.Frame.View.get().addEventListener("mousedown", function (event) {
                if (event.preventDefault) event.preventDefault();
            }, false);
            StoryBoard.CanvasDoodle.View.initialize(board.width, board.height);
        },
        get: function () {
            return $("board");
        },
        dimensionsFor: function (element) {
            var elementXY = element.cumulativeOffset();
            var x = elementXY[0] - StoryBoard.View.Dimensions.X.asNumber();
            var y = elementXY[1] - StoryBoard.View.Dimensions.Y.asNumber();
            return {x: x, y: y, width: element.getWidth(), height: element.getHeight() };
        },
        centerPointFor: function (element) {
            var elementXY = element.cumulativeOffset();
            var x = ((elementXY[0]) + (element.getWidth() / 2)) - StoryBoard.View.Dimensions.X.asNumber();
            var y = ((elementXY[1]) + (element.getHeight() / 2)) - (StoryBoard.View.Dimensions.Y.asNumber() * 2);
            return {x: x, y: y};
        },
        Dimensions: {
            X: {
                asPixels: function () {
                    return StoryBoard.View.Dimensions.X.asNumber() + "px";
                },
                asNumber: function () {
                    return StoryBoard.View.get().cumulativeOffset()[0];
                },
                set: function (x) {
                    StoryBoard.View.get().setStyle({"left": x + "px"});
                    StoryBoard.View.get().setAttribute("left", x + "px");
                }
            },
            Y: {
                asPixels: function () {
                    return StoryBoard.View.Dimensions.Y.asNumber() + "px";
                },
                asNumber: function () {
                    return StoryBoard.View.get().cumulativeOffset()[1];
                },
                set: function (y) {
                    StoryBoard.View.get().setStyle({"top": y + "px"});
                    StoryBoard.View.get().setAttribute("top", y + "px");
                }
            },
            Width: {
                asPixels: function () {
                    return StoryBoard.View.Dimensions.Width.asNumber() + "px";
                },
                asNumber: function () {
                    return StoryBoard.View.get().getWidth();
                },
                set: function (width) {
                    StoryBoard.View.get().setStyle({"width": width + "px"});
                    StoryBoard.View.get().setAttribute("width", width + "px");
                }
            },
            Height: {
                asPixels: function () {
                    return StoryBoard.View.Dimensions.Height.asNumber() + "px";
                },
                asNumber: function () {
                    return StoryBoard.View.get().getHeight();
                },
                set: function (height) {
                    StoryBoard.View.get().setStyle({"height": height + "px"});
                    StoryBoard.View.get().setAttribute("height", height + "px");
                }
            }
        }

    },
    Controller: {

        initialize: function (card, cards, board, backlog) {
            Opentip.defaultStyle = "glass";
            StoryBoard.Backlog.Controller.initialize();
            StoryBoard.View.initialize(board);
            StoryBoard.Frame.Controller.changeTo(board.frameType);
            StoryBoard.Line.Controller.addAll(board.lines);
            StoryBoard.TextArea.Controller.addAll(board.textAreas);
            StoryBoard.HotspotArea.Controller.addAll(board.hotspotAreas);
            StoryBoard.PostIt.Controller.addAll(board.postIts);
            StoryBoard.Card.Controller.initialize(cards, backlog);
            StoryBoard.Toolbar.Controller.initialize();
            StoryBoard.Controller.listenForUpdates();
            Document.Comms.View.destroy();
        },
        listenForUpdates: function () {
            new Ajax.PeriodicalUpdater("ajax", root + "/StoryBoard/Feed/0/index.js", {
                method: "post",
                frequency: 3,
                decay: 1,
                evalJSON: false,
                onSuccess: function (transport) {
                    var reply = transport.responseText.evalJSON(true);
                    reply.cards.each(function (change) {
                        if (change.action) {
                            StoryBoard.Card.Controller.dispatch(change.action, change.model);
                        }
                    });
                    reply.postIts.each(function (change) {
                        if (change.action) {
                            StoryBoard.PostIt.Controller.dispatch(change.action, change.model);
                        }
                    });
                }
            });
        },
        edit: function () {
            document.location = root + "/StoryBoard/Build/" + Model.card.identity + "/index.xhtml";
        },
        add: function (element) {
            StoryBoard.View.get().appendChild(element);
        },
        showParent: function (card) {
            document.location = root + "/StoryBoard/Show/" + card.parent.identity + "/index.xhtml";
        }

    },
    Backlog: {

        View: {

            backlogContents: function () {
                return $$(".backlog_inner_contents")[0];
            }

        },
        Controller: {

            isActive: false,
            initialize: function () {
                Event.observe("backlog_tab", "click", function () {
                    StoryBoard.Backlog.Controller.toggle();
                });
                Droppables.add(StoryBoard.Backlog.View.backlogContents(), { onDrop: StoryBoard.Card.Controller.droppedCardOntoBacklog, greedy: true, accept: "medium_size_card" });
            },
            toggle: function () {
                Effect.toggle(StoryBoard.Backlog.View.backlogContents(), "blind", { duration: 0.3, scaleContent: false });
                if (StoryBoard.Backlog.Controller.isActive) {
                    StoryBoard.Backlog.Controller.inActive();
                } else {
                    StoryBoard.Backlog.Controller.active();
                }
            },
            active: function () {
                $("backlog_tab").removeClassName("backlog_tab");
                $("backlog_tab").addClassName("backlog_tab_active");
                StoryBoard.Backlog.Controller.isActive = true;
            },
            inActive: function () {
                $("backlog_tab").removeClassName("backlog_tab_active");
                $("backlog_tab").addClassName("backlog_tab");
                StoryBoard.Backlog.Controller.isActive = false;
            }
        }

    },
    CanvasDoodle: {

        View: {

            target: function () {
                return $("doodle_target");
            },
            initialize: function (width, height) {
                CanvasDoodle.initialize(StoryBoard.CanvasDoodle.View.target(), width, height);
            }

        }
    },
    Card: {

        Max: {width: 320, height: 210},
        Min: {width: 90, height: 60},
        InfoButton: {

            View: {

                build: function (identity) {
                    var infoView = View.build("div", {"class": "card_info"}, "i");
                    infoView.addTip(function () {
                        return StoryBoard.Card.Toolbar.View.build(identity)
                    }, { "fixed": true, "stem": true, "showOn": "click", "hideTrigger": "closeButton" });
                    return infoView;
                },
                addTo: function (cardView) {
                    var identity = cardView.getAttribute("id").substr(1);
                    var cardInner = cardView.select(".medium_size_card_background")[0];
                    cardInner.insertBefore(StoryBoard.Card.InfoButton.View.build(identity), cardInner.firstChild)
                }

            }

        },
        ExpandCollapse: {

            Controller: {
                expand: function (identity) {
                    StoryBoard.Card.ExpandCollapse.Controller._toggle(identity, "Max");
                },
                collapse: function (identity) {
                    StoryBoard.Card.ExpandCollapse.Controller._toggle(identity, "Min");
                },
                _toggle: function (identity, dimension) {
                    var cardView = $('_' + identity);
                    new Effect.Resize(cardView, StoryBoard.Card[dimension].width, StoryBoard.Card[dimension].height, {
                        "onUpdate": StoryBoard.Card.Controller.onResize,
                        "onFinish": StoryBoard.Card.Controller.resizeEnd
                    });
                    $("_expand_"+identity).toggle();
                    $("_collapse_"+identity).toggle();
                }
            },
            View: {

                expand: function (identity) {
                    var expandView = View.build("div", {"id": "_expand_" + identity, "class": "expand"});
                    Event.observe(expandView, "click", function () { StoryBoard.Card.ExpandCollapse.Controller.expand(identity); });
                    return expandView;
                },
                collapse: function (identity) {
                    var collapseView = View.build("div", {"id": "_collapse_" + identity, "class": "collapse"});
                    Event.observe(collapseView, "click", function () { StoryBoard.Card.ExpandCollapse.Controller.collapse(identity); });
                    return collapseView;
                },
                addExpandTo: function (cardView) {
                    return StoryBoard.Card.ExpandCollapse.View._addTo(cardView, "expand");
                },
                addCollapseTo: function (cardView) {
                    return StoryBoard.Card.ExpandCollapse.View._addTo(cardView, "collapse");
                },
                _addTo: function (cardView, which) {
                    var identity = cardView.getAttribute("id").substr(1);
                    var cardInner = cardView.select(".medium_size_card_background")[0];
                    var reply = StoryBoard.Card.ExpandCollapse.View[which](identity);
                    cardInner.insertBefore(reply, cardInner.firstChild)
                    return reply;
                }


            }

        },
        Toolbar: {

            ImageButton: {

                View: {

                    build: function (imageClass, title, identity, callback) {
                        var imageView = View.build("div", {"class": imageClass + " smaller_button_image", "id": "image_" + identity});
                        var buttonView = View.build("div", { "title": title, "id": identity, "class": "status_button smaller_image_button pushButton clickable center" }, imageView);
                        if (callback) {
                            Event.observe(buttonView, "click", callback);
                        }
                        return buttonView;
                    }

                }
            },
            View: {

                removeAll: function () {
                    $$(".ot-container").each(function (element) {
                        element.remove();
                    });
                },
                build: function (identity) {
                    var container = View.build("div", {"class": "card_tool_buttons_container" });
                    var tb = StoryBoard.Card.Toolbar.View;
                    tb.addActionTo(container, identity, "Show");
                    tb.addActionTo(container, identity, "Edit");
                    tb.addActionTo(container, identity, "Archive");
                    tb.addActionTo(container, identity, "Delete");
                    tb.addActionTo(container, identity, "Attachments");
                    tb.addActionTo(container, identity, "Facts");
                    tb.addActionTo(container, identity, "People");
                    tb.addActionTo(container, identity, "Board");
                    return container;
                },
                addActionTo: function (container, identity, action) {
                    var a = action.toLowerCase();
                    container.appendChild(StoryBoard.Card.Toolbar.ImageButton.View.build(a + "_button_image", action, a + "_card_button_" + identity, function () {
                        StoryBoard.Card.Controller[action].now(identity);
                    }));
                }
            }
        },
        View: {

            get: function (identity) {
                return $("_" + identity);
            },
            initialize: function (cards, backlog) {
                cards.each(function (card) {
                    var cardView = StoryBoard.Card.View.build(card);
                    var target = StoryBoard.View.get();
                    var point = Document.View.calculateRelativePointFromAbsolutePoint(card, target);
                    if (card.status.identity === backlog.identity) {
                        target = StoryBoard.Backlog.View.backlogContents();
                        point = card;
                    }
                    cardView.setStyle(new PxDecorator({ "left": point.x, "top": point.y }));
                    target.appendChild(cardView);
                    StoryBoard.Card.Controller.repaintInfo(cardView); // to resize font size etc
                });
            },
            build: function (card) {
                var valueView = View.build("div", { "id": "_inner_value_" + card.identity, "class": "resizeable_card_foot_value"}, card.effort.amount + " " + card.effort.type.title);
                var buttonsView = View.build("div", { "id": "_inner_buttons_" + card.identity, "class": "resizeable_card_foot_buttons"});
                var toolbar = StoryBoard.Card.Toolbar.View;
                toolbar.addActionTo(buttonsView, card.identity, "Show");
                toolbar.addActionTo(buttonsView, card.identity, "Edit");
                toolbar.addActionTo(buttonsView, card.identity, "Attachments");
                toolbar.addActionTo(buttonsView, card.identity, "Facts");
                toolbar.addActionTo(buttonsView, card.identity, "People");
                toolbar.addActionTo(buttonsView, card.identity, "Board");
                toolbar.addActionTo(buttonsView, card.identity, "Archive");
                toolbar.addActionTo(buttonsView, card.identity, "Delete");
                var effortView = View.build("div", { "id": "_inner_effort_" + card.identity, "class": "resizeable_card_foot_effort"}, card.value.amount + " " + card.value.type.title);
                var cardFooterView = View.build("div", { "id": "_inner_foot_" + card.identity, "class": "resizeable_card_foot"});
                cardFooterView.appendChild(valueView);
                cardFooterView.appendChild(effortView);
                var cardTitleView = View.build("div", { "id": "_inner_title_" + card.identity, "class": "small_size_card_title"}, card.title);
                var cardBodyView = View.build("div", { "id": "_inner_body_" + card.identity, "class": "resizeable_card_body"}, card.body);
                var cardInnerView = View.build("div", { "id": "_inner_" + card.identity, "class": "medium_size_card_background"}, cardTitleView);
                cardInnerView.appendChild(cardBodyView);
                cardInnerView.appendChild(cardFooterView);
                cardInnerView.appendChild(buttonsView);
                var cardView = View.build("div", { "id": "_" + card.identity, "title": card.title, "class": "medium_size_card shadow", "style": "background: " + card.type.colour + "; width: " + card.width + "px; height: " + card.height + "px"});
                cardView.appendChild(cardInnerView);
                var expandView = StoryBoard.Card.ExpandCollapse.View.addExpandTo(cardView);
                var collapseView = StoryBoard.Card.ExpandCollapse.View.addCollapseTo(cardView);
                if(card.width === StoryBoard.Card.Max.width && card.height === StoryBoard.Card.Max.height) {
                    expandView.hide();
                } else {
                    collapseView.hide();
                }
                new Draggable(cardView, { "handle": cardInnerView, "revert": "failure", onStart: StoryBoard.Card.Controller.startMovingCard });
                new Resizeable(cardView, { "minWidth": StoryBoard.Card.Min.width, "minHeight": StoryBoard.Card.Min.height, "maxWidth": StoryBoard.Card.Max.width, "maxHeight": StoryBoard.Card.Max.height, "constrainAspectRatio": false, "onResize": StoryBoard.Card.Controller.onResize, "resizeEnd": StoryBoard.Card.Controller.resizeEnd });
                return cardView;
            },
            calculateRelativePointFromAbsoluteCardPoint: function (cardDimensions, hotspotView) {
                return Document.View.calculateRelativePointFromAbsolutePoint(cardDimensions, hotspotView);
            },
            calculateRelativePointFromAbsolute: function (cardView, hotspotView) {
                var cardDimensions = StoryBoard.View.dimensionsFor(cardView);
                return StoryBoard.Card.View.calculateRelativePointFromAbsoluteCardPoint(cardDimensions, hotspotView);
            },
            flash: function (identity, startcolor) {
                new Effect.Highlight("_" + identity, { keepBackgroundImage: true, startcolor: startcolor });
            }
        },
        Controller: {

            repaintInfo: function (cardView) {
                var setTitleClass = function (titleClass) {
                    titleView.removeClassName("small_size_card_title");
                    titleView.removeClassName("medium_size_card_title");
                    titleView.removeClassName("large_size_card_title");
                    titleView.addClassName(titleClass);
                };
                var invisible = function (element) {
                    element.removeClassName("visible");
                    element.addClassName("invisible");
                };
                var visible = function (element) {
                    element.addClassName("visible");
                    element.removeClassName("invisible");
                };
                var identity = cardView.getAttribute("id").substr(1);
                var titleView = $("_inner_title_" + identity);
                var bodyView = $("_inner_body_" + identity);
                var footView = $("_inner_foot_" + identity);
                var buttonsView = $("_inner_buttons_" + identity);
                var cardDimensions = StoryBoard.View.dimensionsFor(cardView);
                if (cardDimensions.width < 151) {
                    setTitleClass("small_size_card_title");
                    invisible(bodyView);
                    invisible(footView);
                    invisible(buttonsView);
                } else if (cardDimensions.width > 150 && cardDimensions.width < 251) {
                    setTitleClass("medium_size_card_title");
                    visible(bodyView);
                    invisible(footView);
                    invisible(buttonsView);
                } else if (cardDimensions.width > 250 && cardDimensions.width < 291) {
                    setTitleClass("large_size_card_title");
                    visible(bodyView);
                    visible(footView);
                    invisible(buttonsView);
                } else if (cardDimensions.width > 290 && cardDimensions.height > 185) {
                    setTitleClass("large_size_card_title");
                    visible(bodyView);
                    visible(footView);
                    visible(buttonsView);
                } else if (cardDimensions.height > 100 && cardDimensions.height < 186) {
                    visible(footView);
                    invisible(buttonsView);
                } else {
                    invisible(footView);
                    invisible(buttonsView);
                }
            },
            onResize: function (cardView) {
                StoryBoard.Card.Controller.repaintInfo(cardView);
            },
            resizeEnd: function (cardView) {
                var cardDimensions = StoryBoard.View.dimensionsFor(cardView);
                var identity = cardView.getAttribute("id").substr(1);
                var title = cardView.getAttribute("title");
                var parameters = {
                    "identity": identity,
                    "title": title,
                    "x": cardDimensions.x,
                    "y": cardDimensions.y,
                    "width": cardDimensions.width,
                    "height": cardDimensions.height
                };
                StoryBoard.Card.Controller.Save.now(parameters);
            },
            add: function (identity, title) {
                Document.Comms.PopOver.Controller.show("Add Card", "StoryBoard", "AddCard", "0", "index.xhtml", "cardIdentity=" + identity + "&cardTitle=" + encodeURIComponent(title));
            },
            initialize: function (cards, backlog) {
                StoryBoard.Card.View.initialize(cards, backlog);
            },
            dispatch: function (action, model) {
                StoryBoard.Card.Controller.Dispatch[action.toLowerCase()](model);
            },
            droppedCard: function (cardView, hotspotView) {
                var title = cardView.getAttribute("title");
                var identity = cardView.getAttribute("id").substr(1);
                var statusIdentity = hotspotView.getAttribute("id").substr(1);
                var cardDimensions = StoryBoard.View.dimensionsFor(cardView);
                var boardDimensions = StoryBoard.View.dimensionsFor(StoryBoard.View.get());
                //save absolute coords
                var parameters = {
                    "title": title,
                    "identity": identity,
                    "parent.identity": Model.card.identity,
                    "status.identity": statusIdentity,
                    "x": cardDimensions.x,
                    "y": cardDimensions.y
                };
                var cardX = cardDimensions.x - boardDimensions.x, cardY = cardDimensions.y - boardDimensions.y;
                cardView.setStyle({"left": cardX + "px", "top": cardY + "px" });
                StoryBoard.Card.Controller.Save.now(parameters);
                cardView.parentNode.removeChild(cardView);
                StoryBoard.View.get().appendChild(cardView);
            },
            droppedCardOntoBacklog: function (cardView, backlogView) {
                var title = cardView.getAttribute("title");
                var identity = cardView.getAttribute("id").substr(1);
                var statusIdentity = backlogView.getAttribute("id").substr(1);
                var cardDimensions = StoryBoard.View.dimensionsFor(cardView);
                var backlogDimensions = StoryBoard.View.dimensionsFor(backlogView);
                //save absolute coords
                var cardX = cardDimensions.x - backlogDimensions.x, cardY = cardDimensions.y - backlogDimensions.y
                var parameters = {
                    "title": title,
                    "identity": identity,
                    "parent.identity": Model.card.identity,
                    "status.identity": statusIdentity,
                    "x": cardX,
                    "y": cardY
                };
                StoryBoard.Card.Controller.Save.now(parameters);
                cardView.setStyle({"left": cardX + "px", "top": cardY + "px" });
                cardView.parentNode.removeChild(cardView);
                backlogView.appendChild(cardView);
            },
            startMovingCard: function (event) {
                StoryBoard.Card.Toolbar.View.removeAll();
            },
            Save: {

                now: function (parameters) {
                    new Ajax.Request(root + "/StoryBoard/MoveCard/" + parameters.identity + "/" + parameters.title, {
                        method: "post",
                        parameters: parameters,
                        onFailure: function (transport) {
                            StoryBoard.Card.Controller.Save.failed("Card failed to save");
                        },
                        onException: function (transport) {
                            StoryBoard.Card.Controller.Save.failed("An exception was raised when trying to save the card");
                        },
                        onSuccess: function (transport) {
                            StoryBoard.Card.Controller.Save.done(transport.responseText.evalJSON(true));
                        }
                    });
                },
                failed: function (message) {
                    Document.Comms.Failure.View.flash(message);
                },
                done: function (model) {
                    StoryBoard.Card.View.flash(model.identity, "#00FF00");
                }


            },
            Edit: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Edit Card", "StoryBoard", "EditCard", identity, "index.xhtml");
                }
            },
            Attachments: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Show Attachments to Card", "CardAttachments", "List", identity, "index.xhtml");
                }
            },
            Facts: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Show Facts", "CardFacts", "List", "0", "index.xhtml", "card.identity=" + identity);
                }
            },
            People: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Show People", "CardPeople", "List", identity, "index.xhtml");
                }
            },
            Board: {
                now: function (identity) {
                    document.location = root + "/StoryBoard/Show/" + identity + "/index.xhtml";
                }
            },
            Show: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Show Card", "StoryBoard", "ShowCard", identity, "index.xhtml");
                }
            },
            Delete: {
                now: function (identity) {
                    if (window.confirm("Do you really want to delete this card?")) {
                        new Ajax.Request(root + "/StoryBoard/DeleteCard/" + identity + "/index.xhtml?boardIdentity=" + Model.board.identity, {
                            method: "post",
                            onFailure: function (transport) {
                                StoryBoard.Card.Controller.Delete.failed("Card failed to delete");
                            },
                            onException: function (transport) {
                                StoryBoard.Card.Controller.Delete.failed("An exception was raised when trying to delete the card");
                            },
                            onSuccess: function (transport) {
                                StoryBoard.Card.Controller.Delete.done(transport.responseText.evalJSON(true).identity);
                            }
                        });
                    }
                },
                failed: function (message) {
                    Document.Comms.Failure.View.flash(message);
                },
                done: function (identity) {
                    StoryBoard.Card.Toolbar.View.removeAll();
                    var postItView = StoryBoard.Card.View.get(identity);
                    postItView.remove();
                }
            },
            Archive: {
                now: function (identity) {
                    if (window.confirm("Do you really want to archive this card?")) {
                        new Ajax.Request(root + "/StoryBoard/ArchiveCard/" + identity + "/index.xhtml?boardIdentity=" + Model.board.identity, {
                            method: "post",
                            onFailure: function (transport) {
                                StoryBoard.Card.Controller.Archive.failed("Failed to archive card");
                            },
                            onException: function (transport) {
                                StoryBoard.Card.Controller.Archive.failed("An exception was raised when trying to archive the card");
                            },
                            onSuccess: function (transport) {
                                StoryBoard.Card.Controller.Archive.done(transport.responseText.evalJSON(true).identity);
                            }
                        });
                    }
                },
                failed: function (message) {
                    Document.Comms.Failure.View.flash(message);
                },
                done: function (identity) {
                    StoryBoard.Card.Toolbar.View.removeAll();
                    var postItView = StoryBoard.Card.View.get(identity);
                    postItView.remove();
                }
            },
            Dispatch: {
                archive: function (card) {
                    Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been archived");
                    $("_" + card.identity).remove();
                },
                create: function (card) {
                    Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been added");
                    StoryBoard.Card.View.initialize([card]);
                },
                "delete": function (card) {
                    Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been deleted");
                    $("_" + card.identity).remove();
                },
                update: function (card) {
                    StoryBoard.Card.Toolbar.View.removeAll();
                    var identity = "_" + card.identity;
                    var cardView = $(identity);
                    if (card.status.identity === Model.backlog.identify) {
                        Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been moved to the backlog");
                        StoryBoard.Card.View.flash(card.identity, "#FFFFBB");
                        $(identity).remove();
                        StoryBoard.Card.View.initialize([card]);
                    } else if (card.parent.identity != Model.card.identity) {
                        Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been moved to a different board");
                        StoryBoard.Card.View.flash(card.identity, "#FFFFBB");
                        $(identity).remove();
                    } else {
                        if ($(identity)) {
                            Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been updated");
                            StoryBoard.Card.View.flash(card.identity, "#FFFFBB");
                            new Effect.Move(identity, {"x": card.x, "y": card.y, "mode": "absolute"});
                            new Effect.Resize(cardView, card.width, card.height, { "onUpdate": StoryBoard.Card.Controller.onResize});
                        } else {
                            Document.Comms.Notification.Controller.show("The card \"" + card.title + "\" has been moved to this board");
                            StoryBoard.Card.View.initialize([card]);
                        }
                    }
                }
            }
        }

    },
    Frame: {

        View: {

            Dimensions: {
                X: {
                    asPixels: function () {
                        return StoryBoard.Frame.View.Dimensions.X.asNumber() + "px";
                    },
                    asNumber: function () {
                        return StoryBoard.Frame.View.get().cumulativeOffset()[0];
                    },
                    set: function (x) {
                        StoryBoard.Frame.View.get().setStyle({"left": x + "px"});
                        StoryBoard.Frame.View.get().setAttribute("left", x + "px");
                    }
                },
                Y: {
                    asPixels: function () {
                        return StoryBoard.Frame.View.Dimensions.Y.asNumber() + "px";
                    },
                    asNumber: function () {
                        return StoryBoard.Frame.View.get().cumulativeOffset()[1];
                    },
                    set: function (y) {
                        StoryBoard.Frame.View.get().setStyle({"top": y + "px"});
                        StoryBoard.Frame.View.get().setAttribute("top", y + "px");
                    }
                },
                Width: {
                    asPixels: function () {
                        return StoryBoard.Frame.View.Dimensions.Width.asNumber() + "px";
                    },
                    asNumber: function () {
                        return StoryBoard.Frame.View.get().getWidth();
                    },
                    set: function (width) {
                        StoryBoard.Frame.View.get().setStyle({"width": width + "px"});
                        StoryBoard.Frame.View.get().setAttribute("width", width + "px");
                    }
                },
                Height: {
                    asPixels: function () {
                        return StoryBoard.Frame.View.Dimensions.Height.asNumber() + "px";
                    },
                    asNumber: function () {
                        return StoryBoard.Frame.View.get().getHeight();
                    },
                    set: function (height) {
                        StoryBoard.Frame.View.get().setStyle({"height": height + "px"});
                        StoryBoard.Frame.View.get().setAttribute("height", height + "px");
                    }
                }
            },
            frameType: "large_tv",
            get: function () {
                return $("frame_image");
            },
            change: function (frame) {
                StoryBoard.Frame.View.frameType = frame;
                StoryBoard.Frame.View.get().setAttribute("src", root + "/Images/Show/0/frame_" + frame + ".png");
            },
            flash: function (color) {
                new Effect.Highlight(StoryBoard.Frame.View.get(), { keepBackgroundImage: true, startcolor: color });
            }

        },
        Controller: {

            changeTo: function (newFrame) {
                StoryBoard.Frame.View.change(newFrame);
            }

        }

    },
    HotspotArea: {

        View: {

            build: function (identity, status, x, y, width, height) {
                var storyBoardHeight = StoryBoard.View.Dimensions.Height.asNumber();
                if (height > storyBoardHeight) height = storyBoardHeight;
                if (y < 1) y = 1;
                var hotspotAreaView = View.build("div", { "id": "_" + status.identity, "class": "hotspot_area", "style": "position: absolute; left: " + x + "px; top: " + y + "px; width: " + width + "px; height: " + height + "px" });
                Droppables.add(hotspotAreaView, { hoverclass: "hotspot_area_hover", onDrop: StoryBoard.Card.Controller.droppedCard, greedy: false, accept: "medium_size_card" });
                return hotspotAreaView;
            },
            all: function () {
                return $$(".hotspot_area");
            }

        },
        Controller: {

            addAll: function (hotspotAreas) {
                for (var index = 0; index < hotspotAreas.length; index++) {
                    var hotspotArea = hotspotAreas[index];
                    StoryBoard.HotspotArea.Controller.add(hotspotArea);
                }
            },
            add: function (hotspotArea) {
                var hotspotAreaView = StoryBoard.HotspotArea.View.build(hotspotArea.identity, hotspotArea.status, hotspotArea.x, hotspotArea.y, hotspotArea.width, hotspotArea.height);
                StoryBoard.Controller.add(hotspotAreaView);
            }

        }
    },
    Line: {

        Controller: {

            addAll: function (lines) {
                CanvasDoodle.Line.addAll(lines);
            }

        }

    },
    PostIt: {

        InfoButton: {

            View: {

                build: function (identity) {
                    var infoView = View.build("div", {"class": "post_it_info"}, "i");
                    infoView.addTip(function () {
                        return StoryBoard.PostIt.Toolbar.View.build(identity)
                    }, { "fixed": true, "stem": true, "showOn": "click", "hideTrigger": "closeButton" });
                    return infoView;
                },
                addTo: function (postItView) {
                    var postItBody = postItView.select(".post_it_body")[0];
                    var identity = postItView.getAttribute("id").substr(1);
                    postItBody.parentNode.insertBefore(StoryBoard.PostIt.InfoButton.View.build(identity), postItBody)
                }
            }

        },
        Toolbar: {

            ImageButton: {
                View: {
                    build: function (imageClass, title, identity, callback) {
                        var imageView = View.build("div", {"class": imageClass + " smaller_button_image", "id": "image_" + identity});
                        var buttonView = View.build("div", { "title": title, "id": identity, "class": "status_button smaller_image_button pushButton clickable center" }, imageView);
                        if (callback) {
                            Event.observe(buttonView, "click", callback);
                        }
                        return buttonView;
                    }
                }
            },
            View: {

                removeAll: function () {
                    $$(".ot-container").each(function (element) {
                        element.remove();
                    });
                },
                build: function (identity) {
                    var container = View.build("div", {"class": "post_it_tool_buttons_container" });
                    var tb = StoryBoard.PostIt.Toolbar.View;
                    tb.addActionTo(container, identity, "Show");
                    tb.addActionTo(container, identity, "Edit");
                    tb.addActionTo(container, identity, "Delete");
                    return container;
                },
                addActionTo: function (container, identity, action) {
                    container.appendChild(StoryBoard.PostIt.Toolbar.ImageButton.View.build(action.toLowerCase() + "_button_image", action + " Post It", action.toLowerCase() + "_post_it_button_" + identity, function () {
                        StoryBoard.PostIt.Controller[action].now(identity);
                    }));
                }

            }
        },
        View: {

            get: function (identity) {
                return $("_" + identity);
            },
            flash: function (identity, color) {
                new Effect.Highlight("_" + identity, { keepBackgroundImage: true, startcolor: color });
            },
            initialize: function (postIts) {
                postIts.each(function (postIt) {
                    var postItView = StoryBoard.PostIt.View.build(postIt);
                    var target = StoryBoard.View.get();
                    var point = Document.View.calculateRelativePointFromAbsolutePoint(postIt, target);
                    postItView.setStyle(new PxDecorator({ "left": point.x, "top": point.y }));
                    target.appendChild(postItView);
                });
            },
            build: function (postIt) {
                var title = View.build("div", {"class": "post_it_title"}, postIt.title.truncate(50));
                var br = View.build("br", {"class": "post_it_body"});
                var body = View.build("div", {});
                var postItInner = View.build("div", { "class": "post_it_inner" }, title);
                postItInner.appendChild(br);
                postItInner.appendChild(body);
                var postItView = View.build("div", { "id": "_" + postIt.identity, "title": postIt.title, "style": "position: absolute; left:" + postIt.x + "px; top:" + postIt.y + "px; background-color:" + postIt.colour + ";", "class": "post_it shadow" });
                postItView.appendChild(postItInner);
                StoryBoard.PostIt.InfoButton.View.addTo(postItView);
                new Draggable(postItView, { "onEnd": StoryBoard.PostIt.Controller.droppedPostIt, "onStart": StoryBoard.PostIt.Controller.startMovingPostIt });
                return postItView;
            },
            all: function () {
                return $$(".post_it");
            }

        },
        Controller: {

            Save: {

                now: function (parameters) {
                    new Ajax.Request(root + "/StoryBoard/MovePostIt/" + parameters.identity + "/" + parameters.title, {
                        method: "post",
                        parameters: parameters,
                        onFailure: function (transport) {
                            StoryBoard.PostIt.Controller.Save.failed("PostIt failed to save");
                        },
                        onException: function (transport) {
                            StoryBoard.PostIt.Controller.Save.failed("An exception was raised when trying to save the PostIt");
                        },
                        onSuccess: function (transport) {
                            StoryBoard.PostIt.Controller.Save.done(transport.responseText.evalJSON(true));
                        }
                    });
                },
                failed: function (message) {
                    Document.Comms.Failure.View.flash(message);
                },
                done: function (model) {
                    StoryBoard.Card.View.flash(model.identity, "#00FF00");
                }
            },
            Edit: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Edit Post It", "StoryBoard", "EditPostIt", identity, "index.xhtml");
                }
            },
            Show: {
                now: function (identity) {
                    Document.Comms.PopOver.Controller.show("Show Post It", "StoryBoard", "ShowPostIt", identity, "index.xhtml");
                }
            },
            Delete: {
                now: function (identity) {
                    if (window.confirm("Do you really want to delete this post it?")) {
                        new Ajax.Request(root + "/StoryBoard/DeletePostIt/" + identity + "/index.xhtml?boardIdentity=" + Model.board.identity, {
                            method: "post",
                            onFailure: function (transport) {
                                StoryBoard.PostIt.Controller.Delete.failed("Post It failed to delete");
                            },
                            onException: function (transport) {
                                StoryBoard.PostIt.Controller.Delete.failed("An exception was raised when trying to delete the Post It");
                            },
                            onSuccess: function (transport) {
                                StoryBoard.PostIt.Controller.Delete.done(transport.responseText.evalJSON(true).identity);
                            }
                        });
                    }
                },
                failed: function (message) {
                    Document.Comms.Failure.View.flash(message);
                },
                done: function (identity) {
                    StoryBoard.PostIt.Toolbar.View.removeAll();
                    var postItView = StoryBoard.PostIt.View.get(identity);
                    postItView.remove();
                }
            },
            Dispatch: {
                archive: function (postIt) {
                    if ($("_" + postIt.identity)) {
                        Document.Comms.Notification.Controller.show("The post it \"" + postIt.title + "\" has been archived");
                        $("_" + postIt.identity).remove();
                    }
                },
                create: function (postIt) {
                    Document.Comms.Notification.Controller.show("The post it \"" + postIt.title + "\" has been added");
                    StoryBoard.PostIt.View.initialize([postIt]);
                },
                delete: function (postIt) {
                    if ($("_" + postIt.identity)) {
                        Document.Comms.Notification.Controller.show("The post it \"" + postIt.title + "\" has been deleted");
                        $("_" + postIt.identity).remove();
                    }
                },
                update: function (postIt) {
                    if ($("_" + postIt.identity)) {
                        Document.Comms.Notification.Controller.show("The post it \"" + postIt.title + "\" has been updated");
                        StoryBoard.PostIt.View.flash(postIt.identity, "#FFFFBB");
                        StoryBoard.PostIt.Toolbar.View.removeAll();
                        new Effect.Move("_" + postIt.identity, {"x": postIt.x, "y": postIt.y, "mode": "absolute"});
                    }
                }
            },
            dispatch: function (action, model) {
                StoryBoard.PostIt.Controller.Dispatch[action.toLowerCase()](model);
            },
            droppedPostIt: function (event) {
                var postItView = event.element;
                var identity = postItView.getAttribute("id").substr(1);
                var title = postItView.getAttribute("title");
                var postItDimensions = StoryBoard.View.dimensionsFor(postItView);
                var parameters = { "identity": identity, "title": title, "x": postItDimensions.x, "y": postItDimensions.y };
                StoryBoard.PostIt.Controller.Save.now(parameters);
            },
            startMovingPostIt: function () {
                StoryBoard.PostIt.Toolbar.View.removeAll();
            },
            addAll: function (postIts) {
                StoryBoard.PostIt.View.initialize(postIts);
            },
            add: function (identity, title) {
                Document.Comms.PopOver.Controller.show("Add Post It", "StoryBoard", "AddPostIt", "0", "index.xhtml", "cardIdentity=" + identity + "&cardTitle=" + encodeURIComponent(title));
            }
        }

    },
    Shim: {

        View: {

            build: function () {
                return View.build("img", { "src": root + "/Images/Show/0/blank.png" });
            }

        }

    },
    TextArea: {

        View: {

            build: function (identity, text, options) {
                var textAreaInner = View.build("div", { "class": "editable_text_area_inner" }, text);
                textAreaInner.contentEditable = true;
                var textArea = View.build("div", {"id": "_" + identity, "class": "text_area", "style": "position: absolute; top:" + options.y + "px; left:" + options.x + "px; width: " + options.width + "px; height: " + options.height + "px;"});
                var textAreaHandle = View.build("div", { "class": "text_area_handle" }, " ");
                var shimView = StoryBoard.Shim.View.build();
                textArea.appendChild(textAreaHandle);
                textAreaHandle.appendChild(shimView);
                textArea.appendChild(View.build("div", { "style": "clear: both;" }));
                textArea.appendChild(textAreaInner);
                textArea.appendChild(View.build("div", { "style": "clear: both;" }));
                return textArea;
            },
            all: function () {
                return $$(".text_area");
            }

        },
        Controller: {

            addAll: function (textAreas) {
                for (var index = 0; index < textAreas.length; index++) {
                    var textArea = textAreas[index];
                    StoryBoard.TextArea.Controller.add(textArea.identity, textArea.title, { x: textArea.x, y: textArea.y, width: textArea.width, height: textArea.height });
                }
            },
            add: function (identity, text, options) {
                StoryBoard.Controller.add(StoryBoard.TextArea.View.build(identity, text, options));
            },
            addWithOffset: function (text) {
                var offsetMultiplier = StoryBoard.TextArea.View.all().length;
                if (offsetMultiplier == 0) {
                    offsetMultiplier = 1;
                }
                var offset = (5 * offsetMultiplier);
                StoryBoard.TextArea.Controller.add(UUID.uuid(), text, { x: 100 + offset, y: 100 + offset });
            }

        }

    },
    Toolbar: {

        View: {

            build: function () {
                var toolBarBody = View.build("div", {"id": "tool_bar_body"});
                return View.build("div", {"id": "tool_buttons_bar" }, toolBarBody);
            }

        },
        Controller: {

            initialize: function () {
                var toolBar = StoryBoard.Toolbar.View.build();
                StoryBoard.Toolbar.ToolButtons.Controller.add(toolBar);
                StoryBoard.View.get().appendChild(toolBar);
            }

        },
        ImageButton: {

            View: {

                build: function (image, title, identity, callback) {
                    var buttonView = View.build("div", { "title": title, "id": identity, "class": "tool_button status_button image_button pushButton clickable center" });
                    var buttonImage = View.build("div", { "id": image, "class": "button_image" });
                    buttonView.appendChild(buttonImage);
                    if (callback) {
                        Event.observe(buttonView, "click", callback);
                    }
                    return buttonView;
                }

            }

        },
        ToolButtons: {

            View: {

                build: function () {
                    var container = View.build("div", {});
                    var addCardButton = StoryBoard.Toolbar.ImageButton.View.build("add_card_button_image", "Add New Card", "add_card_button", function () {
                        StoryBoard.Card.Controller.add(Model.card.identity, Model.card.title);
                    });
                    var addPostItButton = StoryBoard.Toolbar.ImageButton.View.build("add_post_it_button_image", "Add New Post It", "add_post_it_button", function () {
                        StoryBoard.PostIt.Controller.add(Model.card.identity, Model.card.title);
                    });
                    var editBoardButton = StoryBoard.Toolbar.ImageButton.View.build("edit_board_button_image", "Edit StoryBoard Layout", "edit_board_button", function () {
                        StoryBoard.Controller.edit();
                    });
                    container.appendChild(addCardButton);
                    container.appendChild(addPostItButton);
                    container.appendChild(editBoardButton);
                    if (Model.card.parent) {
                        var upToParentButton = StoryBoard.Toolbar.ImageButton.View.build("up_to_parent_button_image", "Up To Parent", "up_to_parent_button", function () {
                            StoryBoard.Controller.showParent(Model.card);
                        });
                        container.appendChild(upToParentButton);
                    }
                    return container;
                }

            },
            Controller: {

                add: function (container) {
                    container.appendChild(StoryBoard.Toolbar.ToolButtons.View.build());
                }

            }

        }
    }

};
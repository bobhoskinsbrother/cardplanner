/**
 * DOMStorageAdaptor
 * ===================
 * DOM Storage implementation for Lawnchair.
 *
 * - originally authored by Joseph Pecoraro
 * - window.name code courtesy Remy Sharp: http://24ways.org/2009/breaking-out-the-edges-of-the-browser
 *
 */
var DOMStorageAdaptor = function(options) {
	for (var i in LawnchairAdaptorHelpers) {
		this[i] = LawnchairAdaptorHelpers[i];
	}
	this.init(options);
};


DOMStorageAdaptor.prototype = {
	init:function(options) {
		var self = this;
		this.storage = this.mergeObject(window.localStorage, options.storage);
		this.table = this.mergeObject('field', options.table);
		
		if (!window.Storage) {
			this.storage = (function () {
				// window.top.name ensures top level, and supports around 2Mb
				var data = window.top.name ? self.deserialize(window.top.name) : {};
				return {
					setItem: function (identity, value) {
						data[identity] = value+""; // force to string
						window.top.name = self.serialize(data);
					},
					removeItem: function (identity) {
						delete data[identity];
						window.top.name = self.serialize(data);
					},
					getItem: function (identity) {
						return data[identity] || null;
					},
					clear: function () {
						data = {};
						window.top.name = '';
					}
				};
			})();
		};
	},

	saveAll: function(array) {
		if(!Type.isArray(array)) { throw 'saveAll needs to be called with an Array'; }
		for(var i = 0; i < array.length; i++) { this.save(array[i]); }
	},
	
	save:function(obj, callback) {
		var id = this.table + '::' + (obj.identity || this.uuid());
		delete obj.identity;
		this.storage.setItem(id, this.serialize(obj));
		if (callback) {
		    obj.identity = id.split('::')[1];
		    callback(obj);
		}
	},

    get:function(identity, callback) {
        var obj = this.deserialize(this.storage.getItem(this.table + '::' + identity))
          , cb = this.terseToVerboseCallback(callback);
        
        if (obj) {
            obj.identity = identity;
            if (callback) cb(obj);
        } else {
			if (callback) cb(null);
		}
    },

	all:function(callback) {
		var cb = this.terseToVerboseCallback(callback);
		var results = [];
		for (var i = 0, l = this.storage.length; i < l; ++i) {
			var id = this.storage.key(i);
			var tbl = id.split('::')[0]
			var identity = id.split('::').slice(1).join("::");
			if (tbl == this.table) {
				var obj = this.deserialize(this.storage.getItem(id));
				obj.identity = identity;
				results.push(obj);
			}
		}
		if (cb)
			cb(results);
	},

	remove:function(identityOrObj, callback) {
		var identity = this.table + '::' + (typeof identityOrObj === 'string' ? identityOrObj : identityOrObj.identity);
		this.storage.removeItem(identity);
		if(callback)
		  callback();
	},

	nuke:function(callback) {
		var self = this;
		this.all(function(r) {
			for (var i = 0, l = r.length; i < l; i++) {
				self.remove(r[i]);
			}
			if(callback)
			  callback();
		});
	}
};

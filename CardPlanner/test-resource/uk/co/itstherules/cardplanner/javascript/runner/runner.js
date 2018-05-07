function assertUndefined(message, object) {
	if(typeof object != 'undefined') { throw {'message':message}; }
}

var JSRunner = {

	run : function(test_path, tests) {

		var setup = function() { return undefined; };
		var teardown = function() { };
		var before_all;
		var after_all;

		for (var fun in tests) {
			if (fun == 'setup') {
				setup = tests[fun];
			}
			if (fun == 'teardown') {
				teardown = tests[fun];
			}
			if (fun == 'before_all') {
				before_all = tests[fun];
			}
			if (fun == 'after_all') {
				after_all = tests[fun];
			}
		}

		println('Running test case');

		if (before_all) {
			println('Running before_all target before running any tests')
			before_all();
		}

		for (var fun in tests) {
			if (fun.indexOf('test') === 0) {
				try {
					println('Running test: "' + fun + '"')
					tests[fun](setup());
					println('Test passed: "' + fun + '"')
				} catch (e) {
					println('Test FAILED: "' + fun + '"');
					if (e.message) {
						println(test_path);
						println(e.message);
					}
					throw e;
				} finally {
					teardown();
				}
			}
		}

		if (after_all) {
			println('Running after_all target after running tests')
			after_all();
		}
		println('All tests passed')
	}
}
package uk.co.itstherules.cardplanner.javascript.runner;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

/*
 * Hello, I am a JSRunner class, and I'm responsible for collecting and running js unit tests
 * I do this by super duper magic file name matching inside packages. *test.js -  Now that's Paul Daniels!
 * 
 * The runner.js file has all the magic gubbins for running tests
 * 
 * To get tests running, start the test file by declaring 'var tests = {  }'.  This will run the test object automatically.
 * 
 * Start a test method with the word 'test', so declare something like:
 * 		 testWhatever: function(setupValue) {  }
 * If you declare a setup method like this:
 * 		setup: function() { return 'fish'; }
 * this will then call the test function with the returned value of the setup function (so no nasty scoping issues)
 * 
 * teardown likewise (though no value passing is required
 * 		teardown: function() { AllYourWorldsAreBelongToUs(); }
 * 
 * If included, the before_all, and after_all functions are run before and after the tests are run for that file
 * 
 */

public class JSRunner {

	public static void main(String[] args) throws Throwable {
	    new JSRunner(args[0]);
    }

	public JSRunner(String packageName) throws Throwable {
		JSTestFinder jsTestFinder = new JSTestFinder();
		List<String> testPaths = jsTestFinder.collectTestsFromAllPackagesNamed(packageName);
		
		
		ScriptEngineManager manager = new ScriptEngineManager(JSRunner.class.getClassLoader());
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        IO io = new IO(engine);

		for (String testPath : testPaths) {
			engine.setContext(new SimpleScriptContext());
			
			engine.eval("importPackage(org.junit);");
			engine.put("IO", io);
			io.include("uk/co/itstherules/cardplanner/javascript/runner/runner.js");
			io.include(testPath);
			engine.eval("JSRunner.run('" + testPath + "', tests);");
        }
	}

}
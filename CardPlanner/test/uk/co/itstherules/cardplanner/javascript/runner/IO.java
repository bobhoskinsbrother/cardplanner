package uk.co.itstherules.cardplanner.javascript.runner;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public final class IO {

        private final ScriptEngine engine;

        public IO(ScriptEngine engine) {
            this.engine = engine;
        }

        public void include(String scriptPath) throws ScriptException {
            try {
                InputStream resource = JSRunner.class.getClassLoader().getResourceAsStream(scriptPath);
                this.engine.eval(new InputStreamReader(resource));
            } catch (ScriptException e) {
                System.out.println(new StringBuffer("Error in file: \"").append(scriptPath).append("\" at line number: ").append(e.getLineNumber()).append(", column number: ").append(e.getColumnNumber()).toString());
                throw e;
            }
        }
}
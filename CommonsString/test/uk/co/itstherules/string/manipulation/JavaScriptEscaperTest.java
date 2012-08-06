package uk.co.itstherules.string.manipulation;

import org.junit.Assert;
import org.junit.Test;

public class JavaScriptEscaperTest {
	
	@Test public void canEscape() {
		JavaScriptEscaper unit = new JavaScriptEscaper();
		Assert.assertEquals("I\\'m a nice little \\\"javascript\\\" string\\t\\t<nice/\\>", unit.manipulate("I'm a nice little \"javascript\" string		<nice/>"));
		Assert.assertEquals("\\\\ \\b \\f \\n \\r \\t ", unit.manipulate("\\ \b \f \n \r \t "));
    }
	
}

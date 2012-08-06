package uk.co.itstherules.string.manipulation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConstantizeTest {

	@Test
    public void simple() throws Exception {
	    Constantize unit = new Constantize();
	    assertEquals("IM_A_DROMEDARY", unit.manipulate("Im A Dromedary"));
	    assertEquals("HAL", unit.manipulate("Hal"));
	    assertEquals("HAL", unit.manipulate("HAL"));
	    assertEquals("HAL", unit.manipulate("hAl"));
    }

}

package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class CamelCaseTest {

	@Test
    public void simple() throws Exception {
	    CamelCase unit = new CamelCase();
	    assertEquals("ImADromedary", unit.manipulate("im_a_dromedary"));
	    assertEquals("Hal", unit.manipulate("hal"));
	    assertEquals("HAL", unit.manipulate("h_a_l"));
	    assertEquals("HAL", unit.manipulate("h.a.l"));
    }
	
	@Test
    public void lessSimple() throws Exception {
	    CamelCase unit = new CamelCase();
	    assertEquals("IMADROmEdaRy", unit.manipulate("i\"m_/a*d?r<om-eda>ry"));
    }

}

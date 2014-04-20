package uk.co.itstherules.yawf.view.helper;

import org.junit.Assert;
import org.junit.Test;

public class MathHelperTest {
	
	@Test public void abs() {
		MathHelper unit = new MathHelper();
		Assert.assertEquals(1.0, unit.abs(-1.0), 0.001);
		Assert.assertEquals(0.01, unit.abs(-0.01), 0.001);
		Assert.assertEquals(0.0, unit.abs(-0.0), 0.001);
		Assert.assertEquals(0.01, unit.abs(0.01), 0.001);
    }
	
}

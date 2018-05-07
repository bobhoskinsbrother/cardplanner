package uk.co.itstherules.yawf.model.serializer;

import org.junit.Assert;
import org.junit.Test;

public class JsonSerialiserTest {
	
	@Test
	public void canSerialise() {
		Json<Object> unit = new Json<Object>();
		String reply = unit.serialize(new LevelOne());
		Assert.assertEquals("{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelOne\",\"two\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelTwo\",\"three\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelThree\",\"four\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelFour\",\"five\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelFive\"}}}}}", reply);
    }
	
	@Test
	public void canDeepSerialise() {
		Json<Object> unit = new Json<Object>();
		String reply = unit.deepSerialize(new LevelOne());
		Assert.assertEquals("{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelOne\",\"two\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelTwo\",\"three\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelThree\",\"four\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelFour\",\"five\":{\"class\":\"uk.co.itstherules.yawf.model.serializer.LevelFive\"}}}}}", reply);
    }
}

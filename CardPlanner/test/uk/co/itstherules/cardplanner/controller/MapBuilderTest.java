package uk.co.itstherules.cardplanner.controller;

import java.util.Map;

import org.junit.Test;
import uk.co.itstherules.yawf.MapBuilder;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MapBuilderTest {

	@Test public void simpleBuild() {
		MapBuilder<String, Integer> unit = new MapBuilder<String, Integer>();
		Map<String, Integer> reply = unit.put("one", 1).put("two", 2).put("three", 3).build();
		assertThat(reply.get("one"), is(1));
		assertThat(reply.get("two"), is(2));
		assertThat(reply.get("three"), is(3));
	}
	
}

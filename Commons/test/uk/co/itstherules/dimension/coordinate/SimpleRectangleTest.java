package uk.co.itstherules.dimension.coordinate;

import org.junit.Assert;
import org.junit.Test;


public class SimpleRectangleTest {

	@Test
	public void canMakeRectangle(){
		TopRight topRight = new TopRight(300, 100);
		BottomLeft bottomLeft = new BottomLeft(200, 200);
		
		Rectangle unit = new SimpleRectangle(bottomLeft, topRight);
		
		Assert.assertEquals(200, unit.getBottomLeft().getX());
		Assert.assertEquals(200, unit.getBottomLeft().getY());

		Assert.assertEquals(300, unit.getTopRight().getX());
		Assert.assertEquals(100, unit.getTopRight().getY());

		Assert.assertEquals(200, unit.getTopLeft().getX());
		Assert.assertEquals(100, unit.getTopLeft().getY());

		Assert.assertEquals(300, unit.getBottomRight().getX());
		Assert.assertEquals(200, unit.getBottomRight().getY());

	}

	@Test
	public void canMakeRectangleTutherWay(){
		Rectangle unit = new SimpleRectangle(200,100,300,200);
		
		Assert.assertEquals(200, unit.getBottomLeft().getX());
		Assert.assertEquals(200, unit.getBottomLeft().getY());

		Assert.assertEquals(300, unit.getTopRight().getX());
		Assert.assertEquals(100, unit.getTopRight().getY());

		Assert.assertEquals(200, unit.getTopLeft().getX());
		Assert.assertEquals(100, unit.getTopLeft().getY());

		Assert.assertEquals(300, unit.getBottomRight().getX());
		Assert.assertEquals(200, unit.getBottomRight().getY());

	}
	@Test
	public void canGetHeight() {
		BottomLeft bottomLeft = new BottomLeft(200, 100);
		TopRight topRight = new TopRight(300, 200);
		
		Rectangle unit = new SimpleRectangle(bottomLeft, topRight);
		Assert.assertEquals(100, unit.getHeight());
	}
	
	@Test
	public void canGetHeight2() {
		TopRight topRight = new TopRight(300, 500);
		BottomLeft bottomLeft = new BottomLeft(200, 100);
		
		Rectangle unit = new SimpleRectangle(bottomLeft, topRight);
		Assert.assertEquals(400, unit.getHeight());
	}
	
	@Test
	public void toStringMethod(){
		Rectangle unit = new SimpleRectangle(200,100,300,200);
		Assert.assertEquals("Rectangle: [[TopLeft:200,100],[TopRight:300,100],[BottomRight:300,200],[BottomLeft:200,200]]", unit.toString());
		
	}
}

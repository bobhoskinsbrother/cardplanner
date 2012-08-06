package uk.co.itstherules.dimension.coordinate;

import org.junit.Assert;
import org.junit.Test;


public class CoordinateTranslatorTest {

	
	@Test
	public void canTranslateFromImagePointToPdfPoint(){
		int height = 1000;
		TopLeft topLeft =  new TopLeft(200, 100);
		BottomRight bottomRight =  new BottomRight(400, 150);
		Rectangle rectangle = new SimpleRectangle(topLeft, bottomRight);
		
		CoordinateTranslator unit = new CoordinateTranslator(height);
		
		Rectangle reply = unit.to00AtTopLeft(rectangle);
		
		Assert.assertEquals(200, reply.getTopLeft().getX());
		Assert.assertEquals(850, reply.getTopLeft().getY());

		Assert.assertEquals(400, reply.getTopRight().getX());
		Assert.assertEquals(850, reply.getTopRight().getY());


		Assert.assertEquals(200, reply.getBottomLeft().getX());
		Assert.assertEquals(900, reply.getBottomLeft().getY());

		Assert.assertEquals(400, reply.getBottomRight().getX());
		Assert.assertEquals(900, reply.getBottomRight().getY());
	}
	
	
	@Test
	public void canTranslateFromPdfPointToImagePoint(){
		int height = 1000;
		TopLeft topLeft =  new TopLeft(200, 800);
		BottomRight bottomRight =  new BottomRight(400, 850);
		Rectangle rectangle = new SimpleRectangle(topLeft, bottomRight);
		
		CoordinateTranslator unit = new CoordinateTranslator(height);
		
		Rectangle reply = unit.to00AtBottomLeft(rectangle);
		
		Assert.assertEquals(200, reply.getTopLeft().getX());
		Assert.assertEquals(200, reply.getTopLeft().getY());

		Assert.assertEquals(400, reply.getTopRight().getX());
		Assert.assertEquals(200, reply.getTopRight().getY());


		Assert.assertEquals(200, reply.getBottomLeft().getX());
		Assert.assertEquals(150, reply.getBottomLeft().getY());

		Assert.assertEquals(400, reply.getBottomRight().getX());
		Assert.assertEquals(150, reply.getBottomRight().getY());
	}
	
}

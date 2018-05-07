package uk.co.itstherules.string.manipulation;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MultiChompTest {
	
	@Test public void canSimpleChomp(){
		List<String> toChomp = new LinkedList<String>();
		toChomp.add(" pi");
		toChomp.add(" sin");
		
		String toBeChompedFrom = "radius sin pi";
		Assert.assertEquals("radius", new MultiChomp(toChomp).manipulate(toBeChompedFrom));
	}
	
	@Test public void canChompIfOnlyOneApplies(){
		List<String> toChomp = new LinkedList<String>();
		toChomp.add(" spies");
		toChomp.add(" lies");
		toChomp.add(" pi");
		
		String toBeChompedFrom = "radius sin pi";
		Assert.assertEquals("radius sin", new MultiChomp(toChomp).manipulate(toBeChompedFrom));
	}

	@Test public void wontCutOffInTheMiddle(){
		List<String> toChomp = new LinkedList<String>();
		toChomp.add(" sin");
		
		String toBeChompedFrom = "radius sin pi";
		Assert.assertEquals("radius sin pi", new MultiChomp(toChomp).manipulate(toBeChompedFrom));
	}
}

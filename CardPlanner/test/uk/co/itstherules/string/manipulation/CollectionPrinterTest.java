package uk.co.itstherules.string.manipulation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class CollectionPrinterTest {
	
	@Test
    public void canPrintEmptyFromList() throws Exception {
	    assertEquals("", CollectionPrinter.print(new ArrayList<String>()));
    }

	@Test
    public void canPrintOneFromList() throws Exception {
	    assertEquals("one", CollectionPrinter.print(Collections.singletonList("one")));
    }

	@Test
    public void canPrintTwoFromList() throws Exception {
	    assertEquals("one,ten", CollectionPrinter.print(Arrays.asList("one","ten")));
    }

	@Test
    public void canPrintManyFromList() throws Exception {
	    assertEquals("one,three,five,seven,eleven", CollectionPrinter.print(Arrays.asList("one","three","five","seven","eleven")));
    }
	@Test
    public void canPrintEmptyFromVarArgs() throws Exception {
	    assertEquals("", CollectionPrinter.print());
    }

	@Test
    public void canPrintOneFromVarArgs() throws Exception {
	    assertEquals("one", CollectionPrinter.print("one"));
    }

	@Test
    public void canPrintTwoFromVarArgs() throws Exception {
	    assertEquals("one,ten", CollectionPrinter.print("one","ten"));
    }

	@Test
    public void canPrintManyFromVarArgs() throws Exception {
	    assertEquals("one,three,five,seven,eleven", CollectionPrinter.print("one","three","five","seven","eleven"));
    }

}

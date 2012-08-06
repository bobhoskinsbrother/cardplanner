package uk.co.itstherules.converter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class EnumConverterTest {

	enum SimpleToConvert {Rodger, Dodger, Spodger};

	enum LessSimpleToConvert {
		Rodger("The Dodger"), Dodger("The Spodger"), Spodger("The Rodger");
		private final String string;

		private LessSimpleToConvert(String string) {
			this.string = string;
        }
		@Override
		public String toString() {
			return string;
		}
	};
	
	@Test public void canConvertInOrder(){
		EnumMapConverter unit = new EnumMapConverter();
		Map<String, String> reply = unit.convert(SimpleToConvert.values());
		Set<Entry<String, String>> entrySet = reply.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		Entry<String, String> rodger = iterator.next();
		Entry<String, String> dodger = iterator.next();
		Entry<String, String> spodger  = iterator.next();
		
		Assert.assertEquals("Rodger", rodger.getKey());
		Assert.assertEquals("Rodger", rodger.getValue());

		Assert.assertEquals("Dodger", dodger.getKey());
		Assert.assertEquals("Dodger", dodger.getValue());
		
		Assert.assertEquals("Spodger", spodger.getKey());
		Assert.assertEquals("Spodger", spodger.getValue());

		Assert.assertFalse(iterator.hasNext());
		
	}

	@Test public void canConvertInOrderWithString(){
		EnumMapConverter unit = new EnumMapConverter();
		Map<String, String> reply = unit.convert(LessSimpleToConvert.values());
		Set<Entry<String, String>> entrySet = reply.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		Entry<String, String> rodger = iterator.next();
		Entry<String, String> dodger = iterator.next();
		Entry<String, String> spodger  = iterator.next();
		
		Assert.assertFalse(iterator.hasNext());
		
		Assert.assertEquals("Rodger", rodger.getKey());
		Assert.assertEquals("The Dodger", rodger.getValue());

		Assert.assertEquals("Dodger", dodger.getKey());
		Assert.assertEquals("The Spodger", dodger.getValue());
		
		Assert.assertEquals("Spodger", spodger.getKey());
		Assert.assertEquals("The Rodger", spodger.getValue());
	}

}

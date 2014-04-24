package uk.co.itstherules.yawf.inbound.annotations.processor;

import org.junit.Test;
import uk.co.itstherules.yawf.inbound.MapValuesProvider;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;

import java.util.*;

import static org.junit.Assert.*;

public class BasicValuesProviderBinderTest {

	@Test public void canBindOneString() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "cheeseStrings");
		StringFieldModel model = new StringFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertEquals("cheeseStrings", model.getString());
		assertNull(model.getString2());
    }
	
	@Test public void canBindInteger() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "42");
		IntegerFieldModel model = new IntegerFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertEquals(new Integer(42), model.getInteger());
		assertNull(model.getInteger2());
    }
	
	
	@Test public void cannotBindNonInteger() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "badgers");
		IntegerFieldModel model = new IntegerFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertEquals(new Integer(0), model.getInteger());
		assertNull(model.getInteger2());
    }
	
	@Test public void canBindBoolean() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "false");
		BooleanFieldModel model = new BooleanFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertEquals(Boolean.FALSE, model.getBoolean());
		assertNull(model.getBoolean2());
    }

	@Test public void cannotBindNonBoolean() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "wibble");
		BooleanFieldModel model = new BooleanFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertNull(model.getBoolean());
		assertNull(model.getBoolean2());
    }
	
	
	@Test public void canBindLong() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "42");
		LongFieldModel model = new LongFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertEquals(new Long(42), model.getLong());
		assertNull(model.getLong2());
    }	
	
	@Test public void cannotBindNonLong() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "bleh_bump_on_yer_head_like_this");
		LongFieldModel model = new LongFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertEquals(new Long(0), model.getLong());
		assertNull(model.getLong2());
    }
	
	@Test public void canBindDouble() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "42");
		DoubleFieldModel model = new DoubleFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertEquals(new Double(42), model.getDouble());
		assertNull(model.getDouble2());
    }	
	
	@Test public void cannotBindNonDouble() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "bleh_bump_on_yer_head_like_this");
		DoubleFieldModel model = new DoubleFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertEquals(new Double(0.0), model.getDouble());
		assertNull(model.getDouble2());
    }

	@Test public void canBindDate() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "October 21, 2009");
		DateFieldModel model = new DateFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		Date date = model.getDate();
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		assertEquals(Calendar.OCTOBER, instance.get(Calendar.MONTH));
		assertEquals(21, instance.get(Calendar.DAY_OF_MONTH));
		assertEquals(2009, instance.get(Calendar.YEAR));
		assertNull(model.getDate2());
    }	
	
	@Test public void cannotBindNonDate() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "bleh_bump_on_yer_head_like_this");
		DateFieldModel model = new DateFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertNull(model.getDate());
		assertNull(model.getDate2());
    }

	@Test public void canBindListOfDelegateObjects() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe.0.title", "every");
		map.put("bindToMe.1.title", "dog");
		map.put("bindToMe.2.title", "has");
		map.put("bindToMe.3.title", "its");
		map.put("bindToMe.4.title", "bed");
		ListOfDelegateObjectsModel model = new ListOfDelegateObjectsModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		List<TitleModel> titles = model.getTitles();
		assertEquals("every", titles.get(0).getTitle());
		assertEquals("dog", titles.get(1).getTitle());
		assertEquals("has", titles.get(2).getTitle());
		assertEquals("its", titles.get(3).getTitle());
		assertEquals("bed", titles.get(4).getTitle());
    }	

	@Test public void canBindListOfDelegateObjectsAFewLayersDeep() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("title", "flintstone family");
		map.put("delegates.12.title", "Pee");
		map.put("delegates.11.title", "Gleen");
		map.put("delegates.4.title", "Bream");
		map.put("delegates.4.rifle", "Queen");
		map.put("delegates.10.title", "Free");
		map.put("delegates.3.title", "Green");
		map.put("delegates.6.title", "Seen");
		map.put("delegates.14.title", "Beam");
		map.put("delegates.8.title", "Ream");
		map.put("delegates.7.title", "Obscene");
		map.put("delegates.5.title", "Bean");
		map.put("delegates.13.title", "Wii");
		map.put("delegates.2.title", "Dino");
		map.put("delegates.1.delegates.0.delegates.0.title", "pebbles and bambams love child (obviously when they're a bit older)");
		map.put("delegates.0.title", "fred flintstone");
		map.put("delegates.1.delegates.0.delegates.1.title", "pebbles child after the divorce");
		map.put("delegates.1.title", "wilma flintstone");
		map.put("delegates.9.title", "Lean");
		map.put("delegates.1.delegates.0.title", "pebbles flintstone");
		RecursiveListOfDelegateObjectsModel model = new RecursiveListOfDelegateObjectsModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		List<RecursiveListOfDelegateObjectsModel> delegates = model.getDelegates();
		
		assertEquals("flintstone family", model.getTitle());
		assertEquals("pebbles flintstone", delegates.get(1).getDelegates().get(0).getTitle());
		assertEquals(2, delegates.get(1).getDelegates().get(0).getDelegates().size());
		assertEquals("pebbles and bambams love child (obviously when they're a bit older)", delegates.get(1).getDelegates().get(0).getDelegates().get(0).getTitle());
		assertEquals("pebbles child after the divorce", delegates.get(1).getDelegates().get(0).getDelegates().get(1).getTitle());
		
		assertEquals("fred flintstone", delegates.get(0).getTitle());
		assertEquals("wilma flintstone", delegates.get(1).getTitle());
		assertEquals("Dino", delegates.get(2).getTitle());
		assertEquals("Green", delegates.get(3).getTitle());
		assertEquals("Bream", delegates.get(4).getTitle());
		assertEquals("Queen", delegates.get(4).getRifle());
		assertEquals("Bean", delegates.get(5).getTitle());
		assertEquals("Seen", delegates.get(6).getTitle());
		assertEquals("Obscene", delegates.get(7).getTitle());
		assertEquals("Ream", delegates.get(8).getTitle());
		assertEquals("Lean", delegates.get(9).getTitle());
		assertEquals("Free", delegates.get(10).getTitle());
		assertEquals("Gleen", delegates.get(11).getTitle());
		assertEquals("Pee", delegates.get(12).getTitle());
		assertEquals("Wii", delegates.get(13).getTitle());
		assertEquals("Beam", delegates.get(14).getTitle());
    }
	
	@Test public void canBindListOfStrings() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "every,dog,has,its,bed");
		ListOfStringsFieldModel model = new ListOfStringsFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		List<String> strings = model.getStrings();
		assertEquals("every", strings.get(0));
		assertEquals("dog", strings.get(1));
		assertEquals("has", strings.get(2));
		assertEquals("its", strings.get(3));
		assertEquals("bed", strings.get(4));
		assertNull(model.getStrings2());
    }	
	
	@Test public void canBindAnEmptyListOfStrings() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "");
		ListOfStringsFieldModel model = new ListOfStringsFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertEquals(0, model.getStrings().size());
		assertNull(model.getStrings2());
    }

	@Test public void canBindMapOfStrings() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "one=every,two=dog,three=has,four=its,five=bed");
		MapOfStringsFieldModel model = new MapOfStringsFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		Map<String, String> strings = model.getStrings();
		assertEquals("every", strings.get("one"));
		assertEquals("dog", strings.get("two"));
		assertEquals("has", strings.get("three"));
		assertEquals("its", strings.get("four"));
		assertEquals("bed", strings.get("five"));
		assertNull(model.getStrings2());
    }	
	
	@Test public void canBindAnEmptyMapOfStrings() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "");
		MapOfStringsFieldModel model = new MapOfStringsFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		assertNull(model.getStrings().get(""));
		assertNull(model.getStrings2());
    }
	
	@Test public void canBindEnum() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "Maybe");
		EnumFieldModel model = new EnumFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertFalse(violations.isRegistered());
		TestEnum reply = model.getEnum();
		assertEquals(TestEnum.Maybe, reply);
		assertNull(model.getEnum2());
    }	
	
	
	@Test public void cannotBindAnIllegalEnum() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "IDontExist");
		EnumFieldModel model = new EnumFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertNull(model.getEnum());
		assertNull(model.getEnum2());
    }
	
	@Test public void cannotBindAnEmptyEnum() {
		BasicValuesProviderBinder unit = new BasicValuesProviderBinder(new ImplementationProviderRegister());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("bindToMe", "");
		EnumFieldModel model = new EnumFieldModel();
		QueryKeyViolations violations = unit.bind(new MapValuesProvider(map), model, null);
		assertTrue(violations.isRegistered());
		assertNull(model.getEnum());
		assertNull(model.getEnum2());
    }

}
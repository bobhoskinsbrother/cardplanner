package uk.co.itstherules.yawf.inbound.binders;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.yawf.inbound.annotations.processor.BasicValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.annotations.processor.TestEnum;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;

public class ConvertersTest {

	@Test public void canHandleBoolean() throws Exception {
		BooleanBinder unit = new BooleanBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{true, false, false, false, false, false, false, false, false});
	}
	
	@Test public void canHandleDate() throws Exception {
		DateBinder unit = new DateBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, true, false, false, false, false, false, false, false});
	}
	
	
	@Test public void canHandleDouble() throws Exception {
		DoubleBinder unit = new DoubleBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, true, false, false, false, false, false, false});
	}	
	
	
	@Test public void canHandleEnum() throws Exception {
		EnumBinder unit = new EnumBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, false, true, false, false, false, false, false});
	}	
	
	
	@Test public void canHandleInteger() throws Exception {
		IntegerBinder unit = new IntegerBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, false, false, true, false, false, false, false});
	}	
	
	
	@Test public void canHandleList() throws Exception {
		ListOfStringBinder unit = new ListOfStringBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, false, false, false, true, false, false, false});
	}	
	
	
	@Test public void canHandleMap() throws Exception {
		MapOfStringsBinder unit = new MapOfStringsBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, false, false, false, false, true, false, false});
	}	
	
	@Test public void canHandleString() throws Exception {
		StringBinder unit = new StringBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, false, false, false, false, false, true, false});
	}			

	@Test public void canHandleLong() throws Exception {
		LongBinder unit = new LongBinder(new BasicValuesProviderBinder(new ImplementationProviderRegister()),new ImplementationProviderRegister());
		assertFor(unit, new boolean[]{false, false, false, false, false, false, false, false, true});
	}	
	
	private void assertFor(QueryValueBinder unit, boolean[] assertions) throws Exception {
		Assert.assertEquals(assertions[0], unit.canHandle(Boolean.class, Boolean.class));
		Assert.assertEquals(assertions[1], unit.canHandle(Date.class, Date.class));
		Assert.assertEquals(assertions[2], unit.canHandle(Double.class, Double.class));
		Assert.assertEquals(assertions[3], unit.canHandle(TestEnum.class, Enum.class));
		Assert.assertEquals(assertions[4], unit.canHandle(Integer.class, Integer.class));
		Assert.assertEquals(assertions[5], unit.canHandle(List.class, new Object(){public List<String> list;}.getClass().getField("list").getGenericType()));
		Assert.assertEquals(assertions[6], unit.canHandle(Map.class, new Object(){public Map<String, String> map;}.getClass().getField("map").getGenericType()));
		Assert.assertEquals(assertions[7], unit.canHandle(String.class, String.class));
		Assert.assertEquals(assertions[8], unit.canHandle(Long.class, Long.class));
	}
	
}

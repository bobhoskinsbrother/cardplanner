package uk.co.itstherules.cardplanner.model;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import uk.co.itstherules.cardplanner.model.CachedInstance.Identities;
import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.model.SimpleAttachmentModel;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class CachedInstanceTest {
	
	@Test
	public void checkThatDefaultObjectsAreStored() {
		FakeObjectCache fakeObjectCache = new FakeObjectCache(new HashSet<>());
		CachedInstance unit = new CachedInstance();
		
		unit.initialise(fakeObjectCache);
		
		List<Object> objects = fakeObjectCache.getObjects();
		assertThat(objects, contains(1, ConfigurationModel.class));
		ConfigurationModel configurationModel = findFirst(ConfigurationModel.class, objects);
		assertFalse(configurationModel.getFirstRun().booleanValue());
		
		assertThat(objects, contains(1, ValueTypeModel.class));
		ValueTypeModel valueTypeModel = findFirst(ValueTypeModel.class, objects);
		assertThat(valueTypeModel.getIdentity(), is(Identities.CURRENCY_VALUE_TYPE.getIdentity()));
		
		assertThat(objects, contains(1, ValueModel.class));
		ValueModel valueModel = findFirst(ValueModel.class, objects);
		assertThat(valueModel.getIdentity(), is(Identities.DEFAULT_VALUE.getIdentity()));

		assertThat(objects, contains(1, EffortTypeModel.class));
		EffortTypeModel effortTypeModel = findFirst(EffortTypeModel.class, objects);
		assertThat(effortTypeModel.getIdentity(), is(Identities.IDEAL_DAY_EFFORT_TYPE.getIdentity()));

		assertThat(objects, contains(1, EffortModel.class));
		EffortModel effortModel = findFirst(EffortModel.class, objects);
		assertThat(effortModel.getIdentity(), is(Identities.DEFAULT_EFFORT.getIdentity()));

		assertThat(objects, contains(1, SimpleAttachmentModel.class));
		SimpleAttachmentModel simpleAttachmentModel = findFirst(SimpleAttachmentModel.class, objects);
		assertThat(simpleAttachmentModel.getIdentity(), is(Identities.DEFAULT_ATTACHMENT.getIdentity()));

		assertThat(objects, contains(1, CardModel.class));
		CardModel cardModel = findFirst(CardModel.class, objects);
		assertThat(cardModel.getIdentity(), is(Identities.INVISIBLE_CARD.getIdentity()));

		assertThat(objects, contains(1, CardTypeModel.class));
		CardTypeModel cardTypeModel = findFirst(CardTypeModel.class, objects);
		assertThat(cardTypeModel.getIdentity(), is(Identities.USER_STORY_ITEM_TYPE.getIdentity()));

		assertThat(objects, contains(1, PersonModel.class));
		assertThat(objects, contains(4, StatusModel.class));
	}
	
	
	private <T> T findFirst(Class<T> theClass, List<Object> objects) {
		for (Object object : objects) {
			if(theClass.equals(object.getClass())) return theClass.cast(object);
		}
		throw new RuntimeException();
	}


	private Matcher<List<Object>> contains(int amount, Class<?> theClass) {
		return new ContainsAmountOfInstancesOfClasses(amount, theClass);
	}

	private static class ContainsAmountOfInstancesOfClasses extends TypeSafeMatcher<List<Object>> {

		private final int amount;
		private final Class<?> theClass;

		public ContainsAmountOfInstancesOfClasses(int amount, Class<?> theClass) {
			this.amount = amount;
			this.theClass = theClass;
		}

		public void describeTo(Description description) {
			description.appendText(new StringBuilder(theClass.getCanonicalName()).append(" was expected ").append(amount).append(" of times in the list").toString());
		}

		@Override
		public boolean matchesSafely(List<Object> objects) {
			int count = 0;
			for (Object object : objects) {
		        if(theClass.equals(object.getClass())) count++;
	        }
			return count == amount;
		}
		
	}
}
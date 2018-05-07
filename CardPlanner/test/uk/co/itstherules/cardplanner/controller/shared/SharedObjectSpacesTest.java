package uk.co.itstherules.cardplanner.controller.shared;

import static org.hamcrest.CoreMatchers.is;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class SharedObjectSpacesTest {
	
	
	@Test public void canAddObjectsButWillNotShowUpInOwnSpace() {
		SharedObjectSpacesListener unit = new SharedObjectSpacesListener();
		unit.contextInitialized(null);
		SharedObjectSpaceClient cardClientOne = SharedObjectSpacesListener.reAttachClient("idOne");
		SharedObjectSpaceClient cardClientTwo = SharedObjectSpacesListener.reAttachClient("idTwo");
		cardClientOne.shareObject(String.class, new SharedObject(SharedObject.Action.CREATE, "hello mom"));
		cardClientOne.shareObject(String.class, new SharedObject(SharedObject.Action.DELETE, "goodbye mom"));
		cardClientTwo.shareObject(String.class, new SharedObject(SharedObject.Action.UPDATE, "fred's dead"));
		
		Collection<SharedObject> messagesOne = cardClientOne.getSharedObjects(String.class);
		Collection<SharedObject> messagesTwo = cardClientTwo.getSharedObjects(String.class);
		
		Iterator<SharedObject> messagesOneIterator = messagesOne.iterator();
		Iterator<SharedObject> messagesTwoIterator = messagesTwo.iterator();

		Assert.assertThat(messagesOneIterator.next().toString(), is("{\"action\":\"UPDATE\",\"model\":fred's dead}"));
		Assert.assertThat(messagesOneIterator.hasNext(), is(false));
		Assert.assertThat(messagesTwoIterator.next().toString(), is("{\"action\":\"CREATE\",\"model\":hello mom}"));
        Assert.assertThat(messagesTwoIterator.next().toString(), is("{\"action\":\"DELETE\",\"model\":goodbye mom}"));
        Assert.assertThat(messagesTwoIterator.hasNext(), is(false));

		messagesOne = cardClientOne.getSharedObjects(String.class);
		messagesTwo = cardClientTwo.getSharedObjects(String.class);

		Assert.assertThat(messagesOne.isEmpty(), is(true));
		Assert.assertThat(messagesTwo.isEmpty(), is(true));
	}

	@Test public void sameClientDifferentSpace() {
		SharedObjectSpacesListener unit = new SharedObjectSpacesListener();
		unit.contextInitialized(null);
		SharedObjectSpaceClient cardClientOne = SharedObjectSpacesListener.reAttachClient("idOne");
		SharedObjectSpaceClient cardClientTwo = SharedObjectSpacesListener.reAttachClient("idTwo");
		cardClientOne.shareObject(String.class, new SharedObject(SharedObject.Action.CREATE, "hello mom"));
		cardClientOne.shareObject(String.class, new SharedObject(SharedObject.Action.DELETE, "goodbye mom"));
		cardClientOne.shareObject(Integer.class, new SharedObject(SharedObject.Action.UPDATE, "999"));
		
		Collection<SharedObject> messagesOneString = cardClientOne.getSharedObjects(String.class);
		Collection<SharedObject> messagesOneInteger = cardClientOne.getSharedObjects(Integer.class);
		Collection<SharedObject> messagesTwoString = cardClientTwo.getSharedObjects(String.class);
		Collection<SharedObject> messagesTwoInteger = cardClientTwo.getSharedObjects(Integer.class);

		Assert.assertThat(messagesOneString.isEmpty(), is(true));
		Assert.assertThat(messagesOneInteger.isEmpty(), is(true));
		Assert.assertThat(messagesTwoString.isEmpty(), is(false));
		Assert.assertThat(messagesTwoInteger.isEmpty(), is(false));

        Assert.assertThat(messagesTwoInteger.iterator().next().toString(), is("{\"action\":\"UPDATE\",\"model\":999}"));
        Iterator<SharedObject> iterator = messagesTwoString.iterator();
        Assert.assertThat(iterator.next().toString(), is("{\"action\":\"CREATE\",\"model\":hello mom}"));
        Assert.assertThat(iterator.next().toString(), is("{\"action\":\"DELETE\",\"model\":goodbye mom}"));
    }


}

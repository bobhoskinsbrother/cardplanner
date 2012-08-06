package uk.co.itstherules.yawf.inbound;

import java.util.Enumeration;
import java.util.Iterator;

public class IteratorEnumeration<T> implements Enumeration<T> {
	public IteratorEnumeration() {
	}

	public IteratorEnumeration(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	public T nextElement() {
		return iterator.next();
	}

	public Iterator<T> getIterator() {
		return iterator;
	}

	public void setIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	private Iterator<T> iterator;
}

//$Id: LobTest.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.test.annotations.lob;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.test.annotations.TestCase;

/**
 * @author Emmanuel Bernard
 */
public class LobTest extends TestCase {
	public void testSerializableToBlob() throws Exception {
		Book book = new Book();
		Editor editor = new Editor();
		editor.setName( "O'Reilly" );
		book.setEditor( editor );
		book.setCode2( new char[]{'r'} );
		Session s;
		Transaction tx;
		s = openSession();
		tx = s.beginTransaction();
		s.persist( book );
		tx.commit();
		s.close();
		s = openSession();
		tx = s.beginTransaction();
		Book loadedBook = (Book) s.get( Book.class, book.getId() );
		assertNotNull( loadedBook.getEditor() );
		assertEquals( book.getEditor().getName(), loadedBook.getEditor().getName() );
		loadedBook.setEditor( null );
		tx.commit();
		s.close();
		s = openSession();
		tx = s.beginTransaction();
		loadedBook = (Book) s.get( Book.class, book.getId() );
		assertNull( loadedBook.getEditor() );
		tx.commit();
		s.close();

	}

	public void testClob() throws Exception {
		Session s;
		Transaction tx;
		s = openSession();
		tx = s.beginTransaction();
		Book b = new Book();
		b.setShortDescription( "Hibernate Bible" );
		b.setFullText( "Hibernate in Action aims to..." );
		b.setCode( new Character[]{'a', 'b', 'c'} );
		b.setCode2( new char[]{'a', 'b', 'c'} );
		s.persist( b );
		tx.commit();
		s.close();

		s = openSession();
		tx = s.beginTransaction();
		Book b2 = (Book) s.get( Book.class, b.getId() );
		assertNotNull( b2 );
		assertEquals( b2.getFullText(), b.getFullText() );
		assertEquals( b2.getCode()[1].charValue(), b.getCode()[1].charValue() );
		assertEquals( b2.getCode2()[2], b.getCode2()[2] );
		tx.commit();
		s.close();
	}

	public void testBlob() throws Exception {
		Session s;
		Transaction tx;
		s = openSession();
		tx = s.beginTransaction();
		CompiledCode cc = new CompiledCode();
		Byte[] header = new Byte[2];
		header[0] = new Byte( (byte) 3 );
		header[1] = new Byte( (byte) 0 );
		cc.setHeader( header );
		int codeSize = 5;
		byte[] full = new byte[codeSize];
		for ( int i = 0; i < codeSize ; i++ ) {
			full[i] = (byte) ( 1 + i );
		}
		cc.setFullCode( full );
		s.persist( cc );
		tx.commit();
		s.close();
		s = openSession();
		tx = s.beginTransaction();
		CompiledCode recompiled = (CompiledCode) s.get( CompiledCode.class, cc.getId() );
		assertEquals( recompiled.getHeader()[1], cc.getHeader()[1] );
		assertEquals( recompiled.getFullCode()[codeSize - 1], cc.getFullCode()[codeSize - 1] );
		tx.commit();
		s.close();
	}

	public void testBinary() throws Exception {
		Session s;
		Transaction tx;
		s = openSession();
		tx = s.beginTransaction();
		CompiledCode cc = new CompiledCode();
		byte[] metadata = new byte[2];
		metadata[0] = (byte) 3;
		metadata[1] = (byte) 0;
		cc.setMetadata( metadata );
		s.persist( cc );
		tx.commit();
		s.close();
		s = openSession();
		tx = s.beginTransaction();
		CompiledCode recompiled = (CompiledCode) s.get( CompiledCode.class, cc.getId() );
		assertEquals( recompiled.getMetadata()[1], cc.getMetadata()[1] );
		tx.commit();
		s.close();
	}

	public LobTest(String x) {
		super( x );
	}

	protected Class[] getMappings() {
		return new Class[]{
				Book.class,
				CompiledCode.class
		};
	}
}

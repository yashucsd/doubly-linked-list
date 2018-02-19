
package hw2;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ListIterator;

/**
 * the tester for the DLL12 class
 * 
 * @author yash nevatia
 * @version 1.0.0
 */
public class DoublyLinkedList12Tester {
	private DoublyLinkedList12<Integer> empty;
	private DoublyLinkedList12<Integer> one;
	private DoublyLinkedList12<Integer> several;
	private DoublyLinkedList12<String> slist;
	static final int DIM = 5;

	/**
	 * Standard Test Fixture. An empty list, a list with one entry (0) and a
	 * list with several entries (0,1,2)
	 */
	@Before
	public void setUp() {
		empty = new DoublyLinkedList12<Integer>();
		one = new DoublyLinkedList12<Integer>();
		one.add(0, new Integer(0));
		
		several = new DoublyLinkedList12<Integer>();
		// List: 1,2,3,...,Dim
		for (int i = DIM; i > 0; i--)
			several.add(new Integer(i));

		// List: "First","Last"
		slist = new DoublyLinkedList12<String>();
		slist.add("First");
		slist.add("Last");
	}

	/** Test if heads of the lists are correct */
	@Test
	public void testHead() {
		assertEquals("Check head", new Integer(0), one.get(0));
		assertEquals("Check head", new Integer(5), several.get(0));
	}

	/**
	 * Test if adds to list at end, and exception for adding null.
	 */
	@Test
	public void testAdd() {
		one.add(new Integer(1));
		assertEquals("Check index two is updated", new Integer(1), one.get(1));

		try {
			empty.add(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
			//
		}
	}

	/** Test if size of lists are correct */
	@Test
	public void testListSize() {
		assertEquals("Check Empty Size", 0, empty.size());
		assertEquals("Check One Size", 1, one.size());
		assertEquals("Check Several Size", DIM, several.size());
	}

	/**
	 * Test setting a specific entry, and exception for out of bound or null
	 * addition
	 */
	@Test
	public void testSet() {
		slist.set(1, "Final");
		assertEquals("Setting specific value", "Final", slist.get(1));
		try {
			empty.set(0, 5);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
		try {
			one.set(0, null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
			//
		}
	}

	/** Test clearing of list */
	@Test
	public void testClear() {
		several.clear();
		assertTrue("Check cleared list is empty", several.isEmpty());
	}

	/**
	 * Test removal of an element from list, and exception for out of bounds
	 */
	@Test
	public void testRemove() {
		several.remove(2);
		assertEquals("Check new list size", DIM - 1, several.size());
		try {
			empty.remove(0);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
	}

	/** Test isEmpty */
	@Test
	public void testEmpty() {
		assertTrue("empty is empty", empty.isEmpty());
		assertTrue("one is not empty", !one.isEmpty());
		assertTrue("several is not empty", !several.isEmpty());
	}

	/**
	 * Test getting of element at index, and exception for out of bound
	 */
	@Test
	public void testGet() {
		assertEquals("Check getting 3", new Integer(2), several.get(3));

		try {
			empty.get(0);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
	}

	/** Test iterator on empty list and several list */
	@Test
	public void testIterator() {
		int counter = 0;
		ListIterator<Integer> iter;
		
		for (iter = empty.listIterator(); iter.hasNext();) {
			fail("Iterating empty list and found element");
		}
		
		counter = 0;
		for (iter = several.listIterator(); iter.hasNext(); iter.next()){
			counter++;
		}
		assertEquals("Iterator several count", counter, DIM);
		
		for(; iter.hasPrevious(); iter.previous()){
			counter--;
		}
		assertEquals("Iterator reverse countdown", counter, 0);
		
		iter.add(new Integer(9));
		assertEquals("Iterator adder", new Integer(9), iter.next());
		
		iter.set(new Integer(8));
		assertEquals("Iterator setter", new Integer(8), iter.previous());
		
		iter.remove();
		assertEquals("Iterator removal", new Integer(5), several.get(0));
		
		iter.next();
		assertEquals("Iterator next index request", 1, iter.nextIndex());
		
		iter.next();
		assertEquals("Iterator previous index request", 0, iter.previousIndex());
		
		
	}
}

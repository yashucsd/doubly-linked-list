package hw2;

import static org.junit.Assert.*;

import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

public class BasicTester {

	private DoublyLinkedList12<Integer> empty;
	private DoublyLinkedList12<String> one;
	private DoublyLinkedList12<Integer> several;
	
	private DoublyLinkedList12<Integer> test;
	private ListIterator <Integer> testIter;
	private ListIterator <Integer> iterator;
	
	@Before
    public void setup() {
    	
		empty = new DoublyLinkedList12<>();
		one = new DoublyLinkedList12<>();
		several = new DoublyLinkedList12<>();
		test = new DoublyLinkedList12<>();
	
    }
	
	@Test
	public void testAllDLinkedListMethodsExist() {
		one.add("Testing");
		
		for(int i=0; i<25; i++)
			several.add(i,i);
		
		int size = several.size();
		int elem = several.get(5);
		int item = several.set(7, 700);
		
		String s1 = one.remove(0);
		several.clear();
		one.isEmpty();
	}
	
	@Test
	public void testAllIteratorMethodsExist() {
		int data, index;
		boolean flag;
		
		for(int i=0; i<25; i++)
			several.add(i,i);
		
		testIter= several.listIterator();
		iterator = test.listIterator();
		
		data = testIter.next();
		index = testIter.nextIndex();
		index = testIter.previousIndex();
		flag = testIter.hasNext();
		flag = testIter.hasPrevious();
		testIter.add(500);
		testIter.next();
		testIter.remove();
		testIter.next();
		testIter.set(100);
		data = testIter.previous();
		
		iterator.add(1);
		iterator.add(2);
		iterator.add(3);
		iterator.previous();
		iterator.next();
		iterator.remove();
	}

}

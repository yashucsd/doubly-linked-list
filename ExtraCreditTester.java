
package hw2;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * the tester for the DLL12 class
 * 
 * @author yash nevatia
 * @version 1.0.0
 */
public class ExtraCreditTester {
	private DoublyLinkedList12<Integer> several = new DoublyLinkedList12<Integer>();
	private DoublyLinkedList12<Integer> many = new DoublyLinkedList12<Integer>();
	private DoublyLinkedList12<Integer> addA = new DoublyLinkedList12<Integer>();
	private DoublyLinkedList12<Integer> addB = new DoublyLinkedList12<Integer>();



	
	@Before
	public void setUp() {
		// List: 5,4,3,2,1
		for (int i = 5; i > 0; i--)
			several.add(new Integer(i));
		
		// List: 1,2,3,4,5
		for (int i = 1; i < 6; i++)
			many.add(new Integer(i));
		
		addA.add(1);
		addA.add(1);
		addA.add(1);
		addA.add(1);

		addB.add(9);
		addB.add(9);
	}
	
	/**
	 * Tests the reverse and concatenate method 
	 */
	@Test
	public void testRaC() {
		DoublyLinkedList12<Integer> severalRaC = new DoublyLinkedList12<>();
		several.reverseAndConcat(severalRaC);
		
		assertEquals("Check array is flipped and concatenated", severalRaC.get(severalRaC.size() - 3), several.get(2));
	}
	
	/**
	* Tests the sum of two lists method
	*/
	@Test
	public void testSum() {
		DoublyLinkedList12<Integer> sum = new DoublyLinkedList12<>();
		addA.sumOfTwoLists(addB, sum);
		
		// Printed arrays are nice to visualise the addition
		// System.out.println(addA + " +");
		// System.out.println(addB + " =");
		// System.out.println(sum);
		assertEquals("Check array is sum", new Integer(2), sum.get(1));
		
		sum.clear();
		many.sumOfTwoLists(several, sum);

		// Printed arrays are nice to visualise the addition
		// System.out.println(many + " +");
		// System.out.println(several + " =");
		// System.out.println(sum);
		assertEquals("Check array is sum", new Integer(6), sum.get(1));
	}
	

	/**
	 * Tests the rotate list method
	 */
	@Test
	public void testRotate(){
		several.rotateList(2);		
		assertEquals("Check list is rotated", several.get(0), new Integer(3));
	}
}
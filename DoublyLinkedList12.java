package hw2;

import java.util.*;

/**
 * This is a linked list with double (both forward and reverse) linking
 * 
 * @author yash nevatia
 * @version 1.0.0
 * @param <E>
 *            the type of object that the list will hold
 */
public class DoublyLinkedList12<E> extends AbstractList<E> {

	private int nelems;
	private Node head;
	private Node tail;

	/** Node class */
	protected class Node {

		E data;
		Node next;
		Node prev;

		/** Constructor to create singleton Node */
		public Node(E element) {
			this.data = element;
		}

		/**
		 * Constructor to create singleton link it between previous and next
		 * 
		 * @param element
		 *            Element to add, can be null
		 * @param prevNode
		 *            predecessor Node, can be null
		 * @param nextNode
		 *            successor Node, can be null
		 */
		public Node(E element, Node prevNode, Node nextNode) {
			this.data = element;
			this.next = nextNode;
			this.prev = prevNode;

			if (prevNode != null)
				prevNode.setNext(this);
			if (nextNode != null)
				nextNode.setPrev(this);
		}

		/** Remove this node from the list. Update previous and next nodes */
		public void remove() {
			next.setPrev(prev);
			prev.setNext(next);
			nelems--;
		}

		/**
		 * Set the previous node in the list
		 *
		 * @param p
		 *            new previous node
		 */
		public void setPrev(Node p) {
			this.prev = p;
		}

		/**
		 * Set the next node in the list
		 * 
		 * @param n
		 *            new next node
		 */
		public void setNext(Node n) {
			this.next = n;
		}

		/**
		 * Set the element
		 * 
		 * @param e
		 *            new element
		 */
		public void setElement(E e) {
			this.data = e;
		}

		/** Accessor to get the next Node in the list */
		public Node getNext() {
			return this.next;
		}

		/** Accessor to get the prev Node in the list */
		public Node getPrev() {
			return this.prev;
		}

		/** Accessor to get the Nodes Element */
		public E getElement() {
			return this.data;
		}
	}

	/** ListIterator implementation */
	protected class MyListIterator implements ListIterator<E> {

		private boolean forward;
		private boolean canRemove;
		private Node left, right; // Cursor sits between these two nodes
		private int idx; // Tracks current position. what next() would return

		public MyListIterator() {
			idx = -1;
			left = head;
			if (head.getNext() != null)
				right = head.getNext();
			canRemove = false;
			forward = true;
		}

		/**
		 * Adds an element to the list. The element is inserted between the left
		 * and right pointers, that is, immediately before the node that would
		 * be retrieved by next() and immediately after the node that would be
		 * retrieved by previous()
		 * 
		 * @throws NullPointerException
		 *             if the data received is null
		 * @param e
		 *            the element to be added
		 */
		@Override
		public void add(E e) throws NullPointerException {
			nullCheck(e);
			Node nn = new Node(e, left, right);
			left.setNext(nn);
			right.setPrev(nn);
			canRemove = false;
			nelems++;

			if (forward)
				left = nn;
			else
				right = nn;
		}

		/**
		 * Checks if there is another element to be retrieved by calling next
		 */
		@Override
		public boolean hasNext() {
			try {
				right.getElement();
			} catch (NullPointerException e) {
				return false;
			}
			return right.getElement() != null;
		}

		@Override
		/**
		 * Checks if there is another element to be retrieved by calling
		 * previous
		 */
		public boolean hasPrevious() {
			return left.getElement() != null;
		}

		@Override
		/**
		 * Advances through the list by one index, and retrieves the next
		 * element
		 *
		 * @throws NoSuchElementException
		 *             if there are no more elements remaining in the list for
		 *             the iterator to retrieve when moving forward
		 */
		public E next() throws NoSuchElementException {
			if (right.getNext() == null)
				throw new NoSuchElementException();
			E r = right.getElement();

			idx++;
			left = right;
			right = right.getNext();

			canRemove = true;
			forward = true;

			return r;
		}

		/**
		 * Advances through the list by one index, and retrieves the next node
		 *
		 * @throws NoSuchElementException
		 *             if there are no more elements remaining in the list for
		 *             the iterator to retrieve when moving forward
		 */
		private Node nextNode() throws NoSuchElementException {
			if (right.getNext() == null)
				throw new NoSuchElementException();
			Node r = right;

			idx++;
			left = right;
			right = right.getNext();

			canRemove = true;
			forward = true;

			return r;
		}

		@Override
		/**
		 * Retrieves the index of the next element (that would be retrieved by
		 * next() call)
		 */
		public int nextIndex() {
			return idx + 1;
		}

		@Override
		/**
		 * Retreats through the list by one index, and retrieves the previous
		 * element
		 * 
		 * @throws NoSuchElementException
		 *             if there are no more elements remaining in the list for
		 *             the iterator to retrieve when moving backwards
		 */
		public E previous() throws NoSuchElementException {
			if (left.getPrev() == null) // checks if at tail
				throw new NoSuchElementException();
			Node r = left;

			idx--;
			right = left;
			left = left.getPrev();

			canRemove = true;
			forward = false;

			return r.getElement();
		}

		@Override
		/**
		 * Retrieves the index of the previous element (that would be retrieved
		 * by previous() call)
		 */
		public int previousIndex() {
			return idx - 1;
		}

		@Override
		/**
		 * Removes from the list the last element that was returned by next() or
		 * previous()
		 * 
		 * @throws IllegalStateException
		 *             if neither next() nor previous() were called, or if add()
		 *             or remove()
		 */
		public void remove() throws IllegalStateException {
			if (!canRemove)
				throw new IllegalStateException();
			Node rm = (forward ? left : right);

			if (forward)
				left = left.getPrev();
			else
				right = right.getNext();

			rm.remove();
			canRemove = false;
		}

		@Override
		/**
		 * Replaces the last element returned by next() or previous() with a
		 * given element.
		 * 
		 * @throws NullPointerException
		 *             if the data given is null
		 * 
		 * @throws IllegalStateException
		 *             if neither next() nor previous() were called, or if add()
		 *             or remove() were called since the last next()/previous()
		 *             call.
		 */
		public void set(E e) throws NullPointerException, IllegalStateException {
			nullCheck(e);
			if (!canRemove)
				throw new IllegalStateException();
			if (forward)
				left.setElement(e);
			else
				right.setElement(e);
		}

	}

	/** DoublyLinkedList12 Class */
	/**
	 * Creates a new, empty doubly-linked list.
	 */
	public DoublyLinkedList12() {
		nelems = 0;
		// dummy nodes
		head = new Node(null);
		tail = new Node(null, head, null);
	}

	// Extra Credit Methods

	/**
	 * takes a linked list and concatenates the reversed version of itself.
	 * Original list is unchanged.
	 * 
	 * @param newList
	 *            is the reversed list
	 */
	public void reverseAndConcat(DoublyLinkedList12<E> newList) {
		ListIterator<E> iter = this.listIterator();

		// copies current list
		while (iter.hasNext())
			newList.add(iter.next());

		// adds reverse list
		while (iter.hasPrevious())
			newList.add(iter.previous());
	}

	/**
	 * populates a third list with the sum of the numbers represented by the two
	 * lists
	 * 
	 * @param list2
	 *            the list that this should be added to
	 * @param result
	 *            the sum of the this and list2
	 */
	public void sumOfTwoLists(DoublyLinkedList12<Integer> list2, DoublyLinkedList12<Integer> result) {
		int carry = 0; // carry

		// add prep
		this.reverse();
		list2.reverse();
		result.clear();

		// distinguish larger and smaller lists
		DoublyLinkedList12<Integer> lrg = new DoublyLinkedList12<>();
		DoublyLinkedList12<Integer> sml = new DoublyLinkedList12<>();
		if (list2.size() > this.size()) {
			lrg.copy(list2);
			sml.copy((DoublyLinkedList12<Integer>) this);
		} else {
			lrg.copy((DoublyLinkedList12<Integer>) this);
			sml.copy(list2);
		}

		int sum, rs; // temporary sum and raw sum

		ListIterator<Integer> lrgItr = lrg.listIterator();
		ListIterator<Integer> smlItr = sml.listIterator();
		while (lrgItr.hasNext()) {
			rs = carry;

			if (smlItr.hasNext())
				rs += lrgItr.next() + smlItr.next();
			else
				rs += lrgItr.next();

			sum = rs % 10;
			carry = rs / 10;
			result.add(sum);
		}

		if (carry != 0)
			result.add(carry);

		this.reverse();
		list2.reverse();
		result.reverse();
	}

	/**
	 * rotates the list counter-clockwise (to the left) k times
	 * 
	 * @param k
	 *            the number of terms to rotate
	 */
	public void rotateList(int k) {
		Iterator<E> itr = this.iterator();
		E t;
		while (k > 0) {
			t = itr.next();
			itr.remove();
			this.add(t);
			k--;
		}
	}

	// Helper method to get the Node at the Nth index
	private Node getNth(int index) {
		MyListIterator itr = this.iterator();

		while (itr.hasNext()) {
			if (itr.nextIndex() == index)
				return itr.nextNode();
			else
				itr.next();
		}

		return null;
	}

	/**
	 * Add an element to the end of the list
	 * 
	 * @param data
	 *            data to be added
	 * @throws NullPointerException
	 *             if data received is null
	 */
	public boolean add(E data) throws NullPointerException {
		nullCheck(data);
		Node on = tail.getPrev(); // (o)ld (n)ode at end
		Node nn = new Node(data, on, tail); // (n)ew (n)ode at end
		tail.setPrev(nn);
		on.setNext(nn);
		nelems++;
		return true;
	}

	/**
	 * Adds an element to a certain index in the list, shifting exist elements
	 * create room. Does not accept null values.
	 * 
	 * @param index
	 *            Where in the list to add the element.
	 * @param data
	 *            Data to be added.
	 * @throws IndexOutOfBoundsException
	 *             if index received is out of bounds for the current list.
	 * @throws NullPointerException
	 *             if data received is null.
	 */
	@Override
	public void add(int index, E data) throws IndexOutOfBoundsException, NullPointerException {
		if (index == nelems) {
			add(data);
			return;
		}

		if (index != 0)
			outOfBoundsCheck(index);

		nullCheck(data);
		Node on, nn; // (o)ld and (n)ew (n)odes at the index

		on = getNth(index);
		nn = new Node(data, on.getPrev(), on);
		on.setPrev(nn);

		nelems++;
	}

	/**
	 * Retrieves the element stored with a given index on the list.
	 * 
	 * @param index
	 *            The index of the desired element.
	 * @return The element stored in the Node with the desired index.
	 * @throws IndexOutOfBoundsException
	 *             if index received is out of bounds for the current list.
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		outOfBoundsCheck(index);
		return getNth(index).getElement();
	}

	/**
	 * Sets the value of an element at a certain index in the list.
	 * 
	 * @param index
	 *            Where in the list the data should be added.
	 * @param data
	 *            Data to add.
	 * @return Element that was previously at this index.
	 * @throws IndexOutOfBoundsException
	 *             if index received is out of bounds for the current list.
	 * @throws NullPointerException
	 *             if data received is null.
	 */
	public E set(int index, E data) throws IndexOutOfBoundsException, NullPointerException {
		outOfBoundsCheck(index);
		nullCheck(data);

		E re = getNth(index).getElement();
		getNth(index).setElement(data);

		return re;
	}

	/**
	 * remove the element from position index in this list
	 * 
	 * @param index
	 *            the index of the node to remove
	 * @return element that was removed
	 * @throws IndexOutOfBoundsExcepti
	 *             on as needed
	 */
	public E remove(int index) throws IndexOutOfBoundsException {
		outOfBoundsCheck(index);
		E rm = getNth(index).getElement();
		getNth(index).remove();

		return rm;
	}

	/**
	 * remove all elements from the list
	 */
	public void clear() {
		head.setNext(tail);
		tail.setPrev(head);
		nelems = 0;
	}

	/**
	 * Determine if the list empty
	 * 
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return nelems == 0;
	}

	/**
	 * Retrieves the amount of elements that are currently on the list.
	 * 
	 * @return Number of elements currently on the list
	 */
	@Override
	public int size() {
		return nelems;
	}

	public MyListIterator iterator() {
		return new MyListIterator();
	}

	public MyListIterator listIterator() {
		return new MyListIterator();
	}

	private void outOfBoundsCheck(int index) {
		if (index > (nelems - 1))
			throw new IndexOutOfBoundsException();
	}

	private void nullCheck(E data) {
		if (data == null)
			throw new NullPointerException();
	}

	private void reverse() {
		MyListIterator itr = this.iterator();
		DoublyLinkedList12<E> t = new DoublyLinkedList12<>();

		while (itr.hasNext())
			itr.next();
		while (itr.hasPrevious())
			t.add(itr.previous());

		this.copy(t);
	}

	private void copy(DoublyLinkedList12<E> o) {
		this.clear();
		MyListIterator itr = o.iterator();

		while (itr.hasNext())
			this.add(itr.next());
	}
}

package util;

/**
 * linked list for project 1
 * 
 * @author Hunter Mintz (hmintz)
 *
 * @param <E>
 */
public class LinkedList<E> {

	private Node front;

	private int size;

	public LinkedList() {
		this.front = new Node(null);
		this.size = 0;
	}

	/**
	 * add the object to the front of the list
	 * 
	 * @param obj
	 */
	public void addTofront(E obj) {
		if (obj == null) {
			throw new NullPointerException("Data in list cannot be null");
		}
		front = new Node(obj, front);
		size++;
	}
	/**
	 * will add the given object to the back of the list
	 * @param obj
	 */
	public void add(E obj) {
		if (obj == null) {
			throw new NullPointerException("Data in list cannot be null");
		}
		if (size == 0) {
			front = new Node(obj);
			return;
		}
		Node current = front;
		while (current.next != null) {
			current = current.next;
		}
		current.next = new Node(obj);
	}

	/**
	 * will remove the element at the given index from the array
	 * 
	 * @param idx
	 */
	public void remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Cannot remove an out of bounds index");
		}
		if (idx == 0) {
			front = front.next;
			size--;
			return;
		}
		Node current = front;
		for (int i = 0; i < idx - 1; i++) {
			current = current.next;
		}
		current.next = current.next.next;
		size--;
	}

	/**
	 * will remove the given object from the array and return its index
	 * 
	 * @param obj
	 * @return the index of the object 
	 * 	-1 if not found
	 */
	public int remove(E obj) {
		Node current = front;
		if (front.data.equals(obj)) {
			front = front.next;
			size--;
			return 0;
		}
		for (int i = 1; i < size; i++) {
			if (current.next.data.equals(obj)) {
				current.next = current.next.next;
				size--;
				return i;
			}
			current = current.next;
		}
		return -1;
	}
	
	/**
	 * will return the element at the given index
	 * @param idx desired index
	 * @return element at index
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Cannot acsess an out of bounds index");
		}
		if (idx == 0) {
			return front.data;
		}
		Node current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;

	}
	
	public int size() {
		return size;
	}

	/**
	 * returns a string representation of the list for testing
	 */
	public String toString() {
		if (size == 0) {
			return "[]";
		}
		String s = "[";
		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				s += get(i).toString() + "]";
			} else {
				s += get(i).toString() + ", ";
			}
		}
		return s;
	}
	
	
	
	
	
	/**
	 * Node class for holding elements in the linked list
	 * @author Hunter Mintz (hmintz)
	 *
	 */
	class Node {
		private E data;

		private Node next;

		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}

		public Node(E data) {
			this(data, null);
		}

	}
}

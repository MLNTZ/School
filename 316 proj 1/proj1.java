import java.util.Scanner;


public class proj1{
	static LinkedList<String> wordList = new LinkedList<>();

	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		String line = read.nextLine();
		if (line.charAt(0) == '0') {
			line = line.substring(2);
			String uncompressedLine = uncompressLine(line);
			System.out.println(uncompressedLine);
			while(read.hasNextLine()) {
				line = read.nextLine();
				if (!line.equals("") && line.charAt(0) == '0') {
					break;
				}
				uncompressedLine = uncompressLine(line);
				System.out.println(uncompressedLine);
			}
		} else {
			int beforeSize = line.getBytes().length;
			int afterSize = 0;
			System.out.print("0 ");
			String compressedLine = compressLine(line);
			afterSize += compressedLine.getBytes().length;
			System.out.println(compressedLine);
			while(read.hasNextLine()) {
				line = read.nextLine();
				beforeSize += line.getBytes().length;
				compressedLine = compressLine(line);
				afterSize += compressedLine.getBytes().length;
				System.out.println(compressedLine);
			
			}
		System.out.println("0 Uncompressed: " + beforeSize + " bytes;  Compressed: " + afterSize + " bytes");
		read.close();
	}
	
}

private static String compressLine(String line) {
	char current = ' ';
	StringBuilder compressedLine = new StringBuilder(line.length());
	StringBuilder wordBuild = new StringBuilder (20);
	for (int i = 0; i < line.length(); i++) {
		current = line.charAt(i);
		if (!(Character.isAlphabetic(current))  || i == line.length() - 1) {
			if (Character.isAlphabetic(current)) {
				wordBuild.append(current);
			}
			if (wordBuild.length() != 0) {
				String word = wordBuild.toString();
				int index = wordList.findRemove(word);
				
				if (index == -1) {
					wordList.add(word);
					compressedLine.append(word);
				} else {
					wordList.add(word);
					compressedLine.append(index + 1);
				}
				if (!(Character.isAlphabetic(current))) {
					compressedLine.append(current);
				}
				wordBuild.delete(0, wordBuild.capacity());
			
			} else {
				compressedLine.append(current);
				
			}
		} else {
			wordBuild.append(current);
		}
	}
	compressedLine.trimToSize();
	return compressedLine.toString();
	
	
	}


	private static String uncompressLine(String line) {
		StringBuilder uncompressedLine = new StringBuilder(line.length());
		StringBuilder wordBuild = new StringBuilder(line.length());
		char current = ' ';
		for (int i = 0; i < line.length(); i++) {
			current = line.charAt(i);
			if (!(Character.isAlphabetic(current) || Character.isDigit(current))  || i == line.length() - 1) {
				if (Character.isAlphabetic(current) || Character.isDigit(current)) {
					wordBuild.append(current);
				}
				if (wordBuild.length() != 0) {
					int position = 0;
					boolean number = true;
					String word = wordBuild.toString();
					try {
						position = Integer.parseInt(word);
					} catch (NumberFormatException e) {
						number = false;
					}
					if (number) {
						word = wordList.get(position - 1);
						uncompressedLine.append(word);
						wordList.findRemove(word);
						wordList.add(word);
					} else {
						wordList.add(word);
						uncompressedLine.append(word);
					}
					if (!(Character.isAlphabetic(current) || Character.isDigit(current))) {
						uncompressedLine.append(current);
					}
					wordBuild.delete(0, wordBuild.capacity());
				} else {
					uncompressedLine.append(current);
				}
			} else {
				wordBuild.append(current);
			}
			
			
		}
		return uncompressedLine.toString();
	}



	
}
	
	
	
	
	
	

/**
	 * linked list for project 1
	 * 
	 * @author Hunter Mintz (hmintz)
	 *
	 * @param <E>
	 */
	class LinkedList<E> {

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
		public void add(E obj) {
			if (obj == null) {
				throw new NullPointerException("Data in list cannot be null");
			}
			front = new Node(obj, front);
			size++;
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
		public int findRemove(E obj) {
			if (size == 0) {
				return -1;
			}
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




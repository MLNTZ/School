

public class EdgeHeap {
	private Edge[] heap;
	private int size;
	private int capacity;
	

	public EdgeHeap(int cap) {
		
		this.capacity = cap;
		this.size = 0;
		heap = new Edge[capacity + 1];
		heap[0] = null;
	}
	
	/**
	 * will return the parent of the edge at position pos
	 * @param pos the desired position
	 * @return the position of the parent node
	 */
	public int getParent(int pos) {
		return pos / 2;
	}
	
	/**
	 * will return the position of left child of the edge at the given position
	 * @param pos the desired position
	 * @return the position of the left child
	 */
	public int getLeftChild(int pos) {
		return (2 * pos);
	}
	/**
	 * will return the position of the right child of the edge at the given position
	 * @param pos the desired position
	 * @return the position of the right child
	 */
	public int getRightChild(int pos) {
		return (2 * pos) + 1;
	}
	/**
	 * will tell if the edge at the given position is a leaf node
	 * @param pos the desired position
	 * @return true if a leaf
	 * 			false if not
	 */
	public boolean isLeaf(int pos) {
		if (pos == 1 && size != 1) {
			return false;
		}
		if (pos >= (size / 2) && pos <= size) {
			return true;
		}
		return false;
	}
	/**
	 * will swap the edges at the given positions
	 * @param pos1 position of first node
	 * @param pos2 position of second node
	 */
	public void swapEdge(int pos1, int pos2) {
		Edge temp = heap[pos1];
		heap[pos1] = heap[pos2];
		heap[pos2] = temp;
	}
	/**
	 * will change the heap so that the element at pos  maintains the heap property
	 * @param pos
	 */
	public void heapify(int pos) {
		if (size == 0) {
			return;
		}
		Edge tmp = heap[pos];
		Edge left = heap[getLeftChild(pos)];
		Edge right = heap[getRightChild(pos)];
		if (!isLeaf(pos)) {
			if(tmp.compareTo(left) == 1 || tmp.compareTo(right) == 1) {
				if(left.compareTo(right) == -1) {
					swapEdge(pos, getLeftChild(pos));
					heapify(getLeftChild(pos));
				} else {
					swapEdge(pos, getRightChild(pos));
					heapify(getRightChild(pos));
				}
			}
		}
	}
	/**
	 * will insert the given edge into the heap
	 * @param e
	 */
	public void insert(Edge e) {
		if (size == 0) {
			heap[1] = e;
			size++;
			return;
		}
		size ++;
		heap[size] = e;
		int temp = size;
		
		while (heap[temp].compareTo(heap[getParent(temp)]) == -1) {
			swapEdge(temp, getParent(temp));
			temp = getParent(temp);
		}
	}
	/**
	 * will build the heap from the current heap array to have properties of heap
	 */
	public void makeHeap() {
		for (int current = (size / 2); current >= 1; current--) {
			heapify(current);
		}
	}
	
	public Edge getMin() {
		Edge ret = heap[1];
		heap[1] = heap[size];
		size--;
		heapify(1);
		return ret;
	}
	
	
	public String printArray() {
		String s = "[";
		
		for (int i = 1; i <= size; i++) {
			s += heap[i].getWeight();
			if (i != size) {
				s += ", ";
			} else {
				s += "]";
			}
		}
		return s;
	}
	
	
}
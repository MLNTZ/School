
/**
 * minimum heap of edges will sort them by weight
 * @author mehun
 *
 */
public class EdgeHeap {
	private Edge[] heap;
	public int size;
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
	public void minHeap(int pos) {
		if (size == 0) {
			return;
		}
		Edge tmp = heap[pos];
		Edge left = heap[getLeftChild(pos)];
		Edge right = heap[getRightChild(pos)];
		if (!isLeaf(pos)) {
			if(tmp.compareWeight(left) == 1 || tmp.compareWeight(right) == 1) {
				if(right.compareWeight(left) == -1) {
					swapEdge(pos, getRightChild(pos));
					minHeap(getRightChild(pos));
				} else {
					swapEdge(pos, getLeftChild(pos));
					minHeap(getLeftChild(pos));
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
		
		while (heap[temp].compareWeight(heap[getParent(temp)]) == -1) {
			swapEdge(temp, getParent(temp));
			temp = getParent(temp);
		}
	}
	/**
	 * will return the minimum element in heap and remove it from the heap
	 */
	public Edge getMin() {
		Edge ret = heap[1];
		heap[1] = heap[size];
		size--;
		minHeap(1);
		return ret;
	}
	
	/**
	 * will print out the heap array for testing
	 * @return
	 */
	public String printArray() {
		String s = "[";
		
		for (int i = 1; i <= size; i++) {
			s += (int) heap[i].getWeight() / (int) 1;
			if (i != size) {
				s += ", ";
			} else {
				s += "]";
			}
		}
		return s;
	}
	
	public Edge[] getArray() {
		return heap;
	}
	
	
}
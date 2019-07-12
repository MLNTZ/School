import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EdgeHeapTest {

	@Test
	void test() {
		EdgeHeap heap = new EdgeHeap(100);
		Edge edge0 = new Edge(0,0, 0);
		Edge edge1 = new Edge(0,0, 1);
		Edge edge2 = new Edge(0,0, 2);
		Edge edge3 = new Edge(0,0, 3);
		Edge edge4 = new Edge(0,0, 4);
		Edge edge5 = new Edge(0,0, 5);
		Edge edge6 = new Edge(0,0, 6);
		Edge edge7 = new Edge(0,0, 7);
		Edge edge8 = new Edge(0,0, 8);
		Edge edge9 = new Edge(0,0, 9);
		Edge edge10 = new Edge(0,0, 10);
		
		heap.insert(edge0);
		assertEquals("[0]", heap.printArray());
		heap.insert(edge1);
		assertEquals("[0, 1]", heap.printArray());
		heap.insert(edge2);
		assertEquals("[0, 1, 2]", heap.printArray());
		heap.makeHeap();
		assertEquals("[0, 1, 2]", heap.printArray());
		heap.insert(edge3);
		assertEquals("[0, 1, 2, 3]", heap.printArray());
		heap.insert(edge4);
		assertEquals("[0, 1, 2, 3, 4]", heap.printArray());
		heap.insert(edge5);
		assertEquals("[0, 1, 2, 3, 4, 5]", heap.printArray());
		heap.insert(edge6);
		assertEquals("[0, 1, 2, 3, 4, 5, 6]", heap.printArray());
		heap.insert(edge7);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7]", heap.printArray());
		heap.insert(edge8);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8]", heap.printArray());
		heap.insert(edge9);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", heap.printArray());
		heap.insert(edge10);
		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", heap.printArray());
		
		assertEquals(0, heap.getMin().getWeight());
		assertEquals("[1, 3, 2, 7, 4, 5, 6, 10, 8, 9]", heap.printArray());
		assertEquals(1, heap.getMin().getWeight());
		assertEquals("[2, 3, 5, 7, 4, 9, 6, 10, 8]", heap.printArray());
		assertEquals(2, heap.getMin().getWeight());
		assertEquals("[3, 4, 5, 7, 8, 9, 6, 10]", heap.printArray());
		assertEquals(3, heap.getMin().getWeight());
		assertEquals("[4, 7, 5, 10, 8, 9, 6]", heap.printArray());
		assertEquals(4, heap.getMin().getWeight());
		assertEquals("[5, 7, 6, 10, 8, 9]", heap.printArray());
		assertEquals(5, heap.getMin().getWeight());
		assertEquals("[6, 7, 9, 10, 8]", heap.printArray());
		assertEquals(6, heap.getMin().getWeight());
		assertEquals("[7, 8, 9, 10]", heap.printArray());
		assertEquals(7, heap.getMin().getWeight());
		assertEquals("[8, 10, 9]", heap.printArray());
		assertEquals(8, heap.getMin().getWeight());
		assertEquals("[9, 10]", heap.printArray());
		assertEquals(9, heap.getMin().getWeight());
		assertEquals("[10]", heap.printArray());
		assertEquals(10, heap.getMin().getWeight());
		
		
		
		EdgeHeap heap2 = new EdgeHeap(100);
		heap2.insert(edge10);
		assertEquals("[10]", heap2.printArray());
		heap2.insert(edge9);
		assertEquals("[9, 10]", heap2.printArray());
		heap2.insert(edge8);
		assertEquals("[8, 10, 9]", heap2.printArray());
		heap2.insert(edge7);
		assertEquals("[7, 8, 9, 10]", heap2.printArray());
		heap2.insert(edge6);
		assertEquals("[6, 7, 9, 10, 8]", heap2.printArray());
		heap2.insert(edge5);
		assertEquals("[5, 7, 6, 10, 8, 9]", heap2.printArray());
		heap2.insert(edge4);
		assertEquals("[4, 7, 5, 10, 8, 9, 6]", heap2.printArray());
		heap2.insert(edge3);
		assertEquals("[3, 4, 5, 7, 8, 9, 6, 10]", heap2.printArray());
		heap2.insert(edge2);
		assertEquals("[2, 3, 5, 4, 8, 9, 6, 10, 7]", heap2.printArray());
		heap2.insert(edge1);
		assertEquals("[1, 2, 5, 4, 3, 9, 6, 10, 7, 8]", heap2.printArray());
		heap2.insert(edge0);
		assertEquals("[0, 1, 5, 4, 2, 9, 6, 10, 7, 8, 3]", heap2.printArray());
	
		assertEquals(0, heap2.getMin().getWeight());
		assertEquals("[1, 2, 5, 4, 3, 9, 6, 10, 7, 8]", heap2.printArray());
		assertEquals(1, heap2.getMin().getWeight());
		assertEquals("[2, 3, 5, 4, 8, 9, 6, 10, 7]", heap2.printArray());
		assertEquals(2, heap2.getMin().getWeight());
		assertEquals("[3, 4, 5, 7, 8, 9, 6, 10]", heap2.printArray());
		assertEquals(3, heap2.getMin().getWeight());
		assertEquals("[4, 7, 5, 10, 8, 9, 6]", heap2.printArray());
		assertEquals(4, heap2.getMin().getWeight());
		assertEquals("[5, 7, 6, 10, 8, 9]", heap2.printArray());
		assertEquals(5, heap2.getMin().getWeight());
		assertEquals("[6, 7, 9, 10, 8]", heap2.printArray());
		assertEquals(6, heap2.getMin().getWeight());
		assertEquals("[7, 8, 9, 10]", heap2.printArray());
		assertEquals(7, heap2.getMin().getWeight());
		assertEquals("[8, 10, 9]", heap2.printArray());
		assertEquals(8, heap2.getMin().getWeight());
		assertEquals("[9, 10]", heap2.printArray());
		assertEquals(9, heap2.getMin().getWeight());
		assertEquals("[10]", heap2.printArray());
		assertEquals(10, heap2.getMin().getWeight());
		
	}
	
	
	@Test
	void test2() {
		EdgeHeap heap = new EdgeHeap(100);
		heap.insert( new Edge(0,0, 7));
		heap.insert(new Edge(0,0, 12));
		heap.insert(new Edge(0,0, 14));
		heap.insert( new Edge(0,0, 5));
		heap.insert(new Edge(0,0, 10));
		heap.insert(new Edge(0,0, 6));
		
		assertEquals(0, heap.getParent(1));
		
		
		assertEquals("[5, 7, 6, 12, 10, 14]", heap.printArray());
	
		
	}

}

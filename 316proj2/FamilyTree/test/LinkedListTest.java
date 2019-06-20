

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class LinkedListTest {
	LinkedList<String> list;
	@Before
	void setUp() {
		
	}
	
	@Test
	void testLinkedList() {
		MyTree<String> tree = new MyTree<String>("a");
		list = new LinkedList<>();
		
		list.addToFront("a");
		list.addToFront("b");
		
		assertEquals("b", list.get(0));
		assertEquals("a", list.get(1));
	}
	
	
	@Test
	void testRemove() {
		list = new LinkedList<>();
		
		list.addToFront("a");
		list.addToFront("b");
		list.addToFront("c");
		list.addToFront("d");
		list.addToFront("e");
		list.addToFront("f");
		list.addToFront("g");
		list.addToFront("h");
		assertEquals(8, list.size());
		assertEquals("[h, g, f, e, d, c, b, a]", list.toString());
		//hgfedcba
		list.remove(2);
		assertEquals(7, list.size());
		assertEquals("[h, g, e, d, c, b, a]", list.toString());
		//hgedcba
		assertEquals("h", list.get(0));
		assertEquals("g", list.get(1));
		assertEquals("e", list.get(2));
		assertEquals("d", list.get(3));
		assertEquals("c", list.get(4));
		assertEquals("b", list.get(5));
		assertEquals("a", list.get(6));
		
		list.remove(6);
		assertEquals(6, list.size());
		assertEquals("[h, g, e, d, c, b]", list.toString());
		//hgedcb
		assertEquals("h", list.get(0));
		assertEquals("g", list.get(1));
		assertEquals("e", list.get(2));
		assertEquals("d", list.get(3));
		assertEquals("c", list.get(4));
		assertEquals("b", list.get(5));
		
		list.remove(0);
		assertEquals(5, list.size());
		assertEquals("[g, e, d, c, b]", list.toString());
		//gedcb
		assertEquals("g", list.get(0));
		assertEquals("e", list.get(1));
		assertEquals("d", list.get(2));
		assertEquals("c", list.get(3));
		assertEquals("b", list.get(4));
		
		
	}
	
	@Test
	public void testRemoveObj() {
		list = new LinkedList<>();
		
		assertEquals(-1, list.remove("u"));
		
		list.addToFront("a");
		list.addToFront("b");
		list.addToFront("c");
		list.addToFront("d");
		list.addToFront("e");
		list.addToFront("f");
		list.addToFront("g");
		list.addToFront("h");
		assertEquals(8, list.size());
		assertEquals("[h, g, f, e, d, c, b, a]", list.toString());
		
		assertEquals(3, list.remove("e"));
		assertEquals(7, list.size());
		assertEquals("[h, g, f, d, c, b, a]", list.toString());
		
		assertEquals(6, list.remove("a"));
		assertEquals(6, list.size());
		assertEquals("[h, g, f, d, c, b]", list.toString());
		
		
		assertEquals(0, list.remove("h"));
		assertEquals(5, list.size());
		assertEquals("[g, f, d, c, b]", list.toString());
		
		assertEquals(4, list.remove("b"));
		assertEquals(4, list.size());
		
		
		assertEquals(-1, list.remove("e"));
		assertEquals(-1, list.remove("a"));
		assertEquals(-1, list.remove("h"));
		
		
		
		
		
	}
	
	@Test
	void testAdd() {
		list = new LinkedList<>();
		list.add("a");
		assertEquals(1, list.size());
		assertEquals("a", list.get(0));
		list.add("b");
		assertEquals(2, list.size());
		assertEquals("b", list.get(1));
		list.add("c");
		assertEquals(3, list.size());
		assertEquals("c", list.get(2));
		
		assertEquals("[a, b, c]", list.toString());
	}

}

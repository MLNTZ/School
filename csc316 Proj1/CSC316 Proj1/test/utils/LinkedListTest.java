package utils;

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
		list = new LinkedList<>();
		
		list.add("a");
		list.add("b");
		
		assertEquals("b", list.get(0));
		assertEquals("a", list.get(1));
	}
	
	
	@Test
	void testRemove() {
		list = new LinkedList<>();
		
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
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
		
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
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

}

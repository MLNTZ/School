import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class MyTreeTest {
//	public static void main(String[] args) {
//	
//		MyTree<String> tree = new MyTree<String>("a");
//		System.out.println(tree.preOrder());
//		
//		//System.out.println( tree.lookup("a"));
//		tree.addChild("b", "a");
//		tree.addChild("c", "a");
//		System.out.println(tree.preOrder());
//		tree.addChild("d", "b");
//		tree.addChild("e", "c");
//		System.out.println(tree.lookup("e").data);
//		
//		System.out.println(tree.preOrder());
//		
//	}
		
	@Test
	void testTree() {
		MyTree<String> tree = new MyTree<String>("a");
		tree.addChild("b", "a");
		tree.addChild("c", "a");
		tree.addChild("d", "b");
		tree.addChild("e", "c");
		assertEquals("abdce", tree.preOrder());
		assertEquals("dbeca", tree.postOrder());
		assertEquals(2, tree.lookup("a").getNumChild());
		assertEquals(1, tree.lookup("b").getNumChild());
		assertEquals(1, tree.lookup("c").getNumChild());
		tree.addChild("f", "a");
		assertEquals(3, tree.lookup("a").getNumChild());
		assertEquals(0, tree.lookup("f").getNumChild());
		assertEquals("abdcef", tree.preOrder());
		assertEquals("dbecfa", tree.postOrder());
		tree.addChild("h", "e");
		tree.addChild("g", "e");
		assertEquals(2, tree.lookup("e").getNumChild());
		assertEquals("abdcehgf", tree.preOrder());
		assertEquals("dbhgecfa", tree.postOrder());
		assertEquals(null, tree.lookup("z"));
	}
	
	@Test
	void TestTree2() {
		MyTree<String> tree = new MyTree<String>("D");
		tree.addChild("H", "D");
		tree.addChild("B", "H");
		tree.addChild("T", "H");
		tree.addChild("C", "H");
		tree.addChild("G", "B");
		tree.addChild("M", "B");
		tree.addChild("W", "B");
		tree.addChild("F", "B");
		tree.addChild("X", "T");
		tree.addChild("Z", "T");
		tree.addChild("R", "C");
		tree.addChild("P", "C");
		tree.addChild("Q", "D");
		tree.addChild("N", "Q");
		assertEquals("DHBGMWFTXZCRPQN", tree.preOrder());
		assertEquals("GMWFBXZTRPCHNQD", tree.postOrder());
		assertEquals(2, tree.lookup("D").getNumChild());
		assertEquals(4, tree.lookup("B").getNumChild());
		assertEquals(2, tree.lookup("T").getNumChild());
		
		
		
		assertEquals(1, tree.heightDif("G", "B"));
		assertEquals(2, tree.heightDif("Z", "H"));
		assertEquals(3, tree.heightDif("P", "D"));
		
		
		tree.markAncestors("W");
		assertTrue(tree.lookup("W").isMarked);
		assertTrue(tree.lookup("B").isMarked);
		assertTrue(tree.lookup("H").isMarked);
		assertTrue(tree.lookup("D").isMarked);
		
		assertEquals(0, tree.heightDif("D", "D"));
		
		assertEquals("H", tree.getFirstCommonMarked("Z"));
		assertEquals("B", tree.getFirstCommonMarked("F"));
		assertEquals("B", tree.getFirstCommonMarked("B"));
		assertEquals(null, tree.getFirstCommonMarked("_"));
		assertEquals("D", tree.getFirstCommonMarked("N"));
		
		tree.unmarkAncestors("W");
		assertFalse(tree.lookup("W").isMarked);
		assertFalse(tree.lookup("B").isMarked);
		assertFalse(tree.lookup("H").isMarked);
		assertFalse(tree.lookup("D").isMarked);
		
		
		assertEquals("DHQBTCNGMWFXZRP", tree.levelOrder(tree.getRoot()));
		
		
	}
	
	@Test 
	void TestTree3(){
		MyTree<String> tree = new MyTree<String>("A");
		tree.addChild("B", "A");
		tree.addChild("C", "B");
		tree.addChild("Q", "A");
		tree.addChild("D", "B");
		tree.addChild("R", "Q");
		tree.addChild("S", "R");
		tree.addChild("T", "R");
		tree.addChild("X", "T");
		tree.addChild("Y", "X");
		tree.addChild("U", "S");
		tree.addChild("E", "D");
		tree.addChild("F", "D");
		tree.addChild("G", "F");
		tree.addChild("N", "G");
		tree.addChild("H", "G");
		tree.addChild("O", "N");
		tree.addChild("I", "E");
		tree.addChild("J", "I");
		tree.addChild("K", "J");
		tree.addChild("P", "H");
		tree.addChild("L", "J");
		tree.addChild("W", "U");
		tree.addChild("M", "J");
		tree.addChild("Z", "A");
		tree.addChild("V", "U");
		assertEquals("ABCDEIJKLMFGNOHPQRSUWVTXYZ", tree.preOrder());
		assertEquals("CKLMJIEONPHGFDBWVUSYXTRQZA", tree.postOrder());
		
		assertEquals(0, tree.getHeight("A"));
		assertEquals(1, tree.getHeight("Q"));
		assertEquals(2, tree.getHeight("D"));
		assertEquals(3, tree.getHeight("S"));
		assertEquals(4, tree.getHeight("I"));
		assertEquals(5, tree.getHeight("W"));
		assertEquals(6, tree.getHeight("M"));
		
		assertEquals("ABQZCDREFSTIGUXJNHWVYKLMOP", tree.levelOrder(tree.getRoot()));
	}
	
	

}

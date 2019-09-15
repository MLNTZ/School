import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UpTreeTest {

	@Test
	void test() {
		UpTree tree = new UpTree(6);
		assertEquals("[0, 1, 2, 3, 4, 5]", tree.printSet());
		tree.union(0, 1);
		assertEquals("[0, 0, 2, 3, 4, 5]", tree.printSet());
		assertEquals("[2, 1, 1, 1, 1, 1]", tree.printSizes());
		tree.union(1, 2);
		assertEquals("[0, 0, 0, 3, 4, 5]", tree.printSet());
		assertEquals("[3, 1, 1, 1, 1, 1]", tree.printSizes());
		tree.union(3, 2);
		assertEquals("[0, 0, 0, 0, 4, 5]", tree.printSet());
		assertEquals("[4, 1, 1, 1, 1, 1]", tree.printSizes());
	}

}

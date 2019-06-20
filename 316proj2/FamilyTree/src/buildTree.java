import java.util.ArrayList;

import MyTree.Tnode;

public class buildTree {
	static ArrayList<Character> preTrav = new ArrayList<Character>();
	static ArrayList<Character> postTrav = new ArrayList<Character>();
	static MyTree<Character> tree = null;
	
	public static void main(String args[]) {
		String pre = "acedbfg";
		String post = "edcgfba";
		
		for (int i = 0; i < pre.length(); i++) {
			preTrav.add(pre.charAt(i));
			postTrav.add(post.charAt(i));
		}
		
		buildTree(preTrav.size(), 0, 0);
	}

	private static Tnode<Character> buildTree(int size, int preStart, int postStart) {
		if (preStart == 0 && postStart == 0) {
			tree = new MyTree<>(preTrav.get(0));
			Tnode<Character> root = tree.getRoot();
			
			
		}
		Character root = preTrav.get(preStart);
		int nextSize = 0; 
		while(next != postTrav.get(nextSize)) {
			nextSize++;
			
		}
		
		
	}

}

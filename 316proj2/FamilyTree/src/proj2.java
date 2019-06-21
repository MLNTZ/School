import java.util.ArrayList;

public class proj2 {
	static ArrayList<Character> preTrav = new ArrayList<Character>();
	static ArrayList<Character> postTrav = new ArrayList<Character>();
	static MyTree<Character> tree = null;
	
	public static void main(String args[]) {
		tree = new MyTree<>();
		String pre = "DHBGMWFTXZCRPQN";
		String post = "GMWFBXZTRPCHNQD";
		
		for (int i = 0; i < pre.length(); i++) {
			preTrav.add(pre.charAt(i));
			postTrav.add(post.charAt(i));
		}
		
		Tnode<Character> root = buildTree(preTrav.size(), 0, 0);
		tree.setRoot(root);
		String newPre = tree.preOrder();
		String newPost = tree.postOrder();
		System.out.println(newPre);
		System.out.println(newPost);
		if (newPre.equals(pre) && newPost.contentEquals(post)){
			System.out.println("pass");
		} else {
			System.out.println("fail");
		}
		System.out.println(relate('W', 'W'));
		System.out.println(relate('D', 'H'));
		System.out.println(relate('D', 'M'));
		
	}

	public static Tnode<Character> buildTree(int size, int preStart, int postStart) {
		if (size == 0) {
			return null;
		}
		if (size == 1) {
			return tree.makeNode(preTrav.get(preStart));
		}
		Tnode<Character> root = tree.makeNode(preTrav.get(preStart));
		int childSize = 1;
		int i = postStart;
		preStart += 1;
		while(!postTrav.get(i).equals(preTrav.get(preStart))) {
			childSize++;
			i++;
		}
		int nextSize = childSize;
		for (int j = 0; j < size - 1; j += 0) {
			tree.addChildNode(root, buildTree(nextSize, preStart, postStart));
			preStart += nextSize;
			postStart += nextSize;
			int temp = 1;
			int k = postStart;
			if (preStart >= preTrav.size()) {
				break;
			}
			while(!postTrav.get(k).equals(preTrav.get(preStart))) {
				temp++;
				k++;
			}
			j+= nextSize;
			nextSize = temp;
				
		}
		return root;	
	}
	
	public static String relate(Character a, Character b) {
		tree.markAnsestors(a);
		Character common = tree.getFirstCommonMarked(b);
		int aDist = tree.heightDif(a, common);
		int bDist = tree.heightDif(b, common);
		String aName = a.toString();
		String bName = b.toString();
		
		String combine = aName + " is " + bName + "'s ";
		tree.unmarkAnsestors(a);
		if (aDist == 0) {
			switch(bDist) {
				case 0:
					return aName + " is " + bName;
				case 1:
					return combine + "parent";
				case 2:
					return combine + "grandparent";
				case 3:
					return combine + "great-grandparent";
				default:
					return combine + getGreat(bDist) + "-grandparent";
			}
		} else if (aDist == 1) {
			
		}
		return null;
		
		
	}

	private static String getGreat(int bDist) {
		String great = "";
		for (int i = 0; i < bDist - 2; i++) {
			if(i == bDist - 3) {
				great += "great";
			}
			great += "great ";
		}
		return great;
	}

}

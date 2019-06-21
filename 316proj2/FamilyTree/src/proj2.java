import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class proj2 {
	static ArrayList<Character> preTrav = new ArrayList<Character>();
	static ArrayList<Character> postTrav = new ArrayList<Character>();
	static MyTree<Character> tree = null;
	/**
	 * main method will prompt user for input and output files, then call the methods for generating the tree
	 * and printing information to the output file.
	 * @param args not used
	 */
	public static void main(String args[]) {
		tree = new MyTree<>();
		System.out.print("Welcome to family tree.\nInput File: ");
		Scanner cmd = new Scanner(System.in);
		File inputFile = new File(cmd.nextLine());
		
		System.out.print("\nOutput File: ");
		File outFile = new File(cmd.nextLine());
		Scanner read = null;
		PrintStream write = null;;
		try {
			read = new Scanner(inputFile);
			
		} catch (FileNotFoundException e) {
			System.out.println("input file cannot be opened... aborting");
			System.exit(1);
		}
		try {
			write = new PrintStream(outFile);
			
		} catch (FileNotFoundException e) {
			System.out.println("output file cannot be opened... aborting");
			System.exit(1);
		}
		String preLine = read.nextLine();
		String postLine = read.nextLine();
		
		String pre = parseLine(preLine);
		String post = parseLine(postLine);
		
		for (int i = 0; i < pre.length(); i++) {
			preTrav.add(pre.charAt(i));
			postTrav.add(post.charAt(i));
		}
		LinkedList<String> querie = new LinkedList<>();
		while(read.hasNextLine()) {
			String temp = read.nextLine();
			if (temp == null || temp.equals(" ") || temp.equals("")) {
				break;
			}
			querie.add(parseLine(temp));
		}
		
		Tnode<Character> root = buildTree(preTrav.size(), 0, 0);
		tree.setRoot(root);
		for(int i = 0; i < querie.size(); i++) {
			String s = querie.get(i);
			write.println(relate(s.charAt(0), s.charAt(1)));
		}
		write.println(printLevel(tree));
		read.close();
		write.close();
		cmd.close();
		
		
	}
	
	/**
	 * will read a line of input from the file and return a string that contains only the important info
	 * @param line
	 * @return
	 */
	public static String parseLine(String line) {
		Scanner lineScan = new Scanner(line);
		String s = "";
		lineScan.next();
		while (lineScan.hasNext()) {
			s += lineScan.next().substring(0, 1);
		}
		lineScan.close();
		return s;
	}
	/**
	 * will recursivley build the tree from the post and pre order traversals
	 * @param size
	 * @param preStart
	 * @param postStart
	 * @return
	 */
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
	/**
	 * will return a string with information about 2 objects relashion in the tree
	 * @param a
	 * @param b
	 * @return
	 */
	public static String relate(Character a, Character b) {
		tree.markAncestors(a);
		Character common = tree.getFirstCommonMarked(b);
		int aDist = tree.heightDif(a, common);
		int bDist = tree.heightDif(b, common);
		String aName = a.toString();
		String bName = b.toString();
		
		String combine = aName + " is " + bName + "'s ";
		tree.unmarkAncestors(a);
		if (aDist == 0) {
			switch(bDist) {
				case 0:
					return aName + " is " + bName + ".";
				case 1:
					return combine + "parent.";
				case 2:
					return combine + "grandparent.";
				case 3:
					return combine + "great-grandparent.";
				default:
					return combine + getGreat(bDist) + "-grandparent.";
			}
		} else if (aDist == 1) {
			switch(bDist) {
				case 0:
					return combine + "child.";
				case 1:
					return combine + "sibling.";
				case 2:
					return combine + "aunt/uncle.";
				default:
					return combine + getGreat(bDist) + "-aunt/uncle.";
			}
		
			
		} else if (aDist == 2 && bDist == 1) {
			return combine + "niece/nephew.";
			
		} else if (aDist > 2 && bDist == 1) {
			return combine + getGreat(aDist) + "-niece/nephew.";
		} else if(aDist >= 2 && bDist >= 2) {
			return combine + (aDist > bDist ? bDist - 1 : aDist - 1) + "th cousin " + (aDist - bDist < 0 ? bDist - aDist : aDist - bDist) + " times removed.";
		} else if (aDist >= 3 && bDist == 0) {
			return combine + getGreat(aDist) + " grandchild.";
		}
		
		return null;
		
		
	}
	/**
	 * will return a string with the correct number of "great" for the relationship
	 * @param bDist
	 * @return
	 */
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
	/**
	 * will print the level order traversal of the given tree starting with its root
	 * @param tree
	 * @return
	 */
	private static String printLevel(MyTree<Character> tree) {
		String trav = tree.levelOrder(tree.getRoot());
		String s = "";
		for (int i = 0; i < trav.length(); i ++) {
			if (i == trav.length() - 1) {
				s += trav.charAt(i) + ".";
			} else {
				s += trav.charAt(i) + ", ";
			}
	
			
		}
		return s;
	}

}




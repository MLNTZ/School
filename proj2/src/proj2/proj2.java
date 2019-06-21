package proj2;

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
			tree.insert(root, buildTree(nextSize, preStart, postStart));
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
			} else {
				great += "great ";
			}
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

/**
 * linked list for project 1
 * 
 * @author Hunter Mintz (hmintz)
 *
 * @param <E>
 */
class LinkedList<E> {

	private Node front;

	private int size;

	public LinkedList() {
		this.front = new Node(null);
		this.size = 0;
	}

	/**
	 * add the object to the front of the list
	 * 
	 * @param obj
	 */
	public void addToFront(E obj) {
		if (obj == null) {
			throw new NullPointerException("Data in list cannot be null");
		}
		front = new Node(obj, front);
		size++;
	}
	/**
	 * will add the given object to the back of the list
	 * @param obj
	 */
	public void add(E obj) {
		if (obj == null) {
			throw new NullPointerException("Data in list cannot be null");
		}
		if (size == 0) {
			front = new Node(obj);
			size++;
			return;
		}
		Node current = front;
		while (current.next != null) {
			current = current.next;
		}
		current.next = new Node(obj);
		size++;
	}

	/**
	 * will remove the element at the given index from the array
	 * 
	 * @param idx
	 */
	public void remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Cannot remove an out of bounds index");
		}
		if (idx == 0) {
			front = front.next;
			size--;
			return;
		}
		Node current = front;
		for (int i = 0; i < idx - 1; i++) {
			current = current.next;
		}
		current.next = current.next.next;
		size--;
	}

	/**
	 * will remove the given object from the array and return its index
	 * 
	 * @param obj
	 * @return the index of the object 
	 * 	-1 if not found
	 */
	public int remove(E obj) {
		Node current = front;
		if(front.data == null) {
			return -1;
		}
		if (front.data.equals(obj)) {
			front = front.next;
			size--;
			return 0;
		}
		for (int i = 1; i < size; i++) {
			if (current.next.data.equals(obj)) {
				current.next = current.next.next;
				size--;
				return i;
			}
			current = current.next;
		}
		return -1;
	}
	
	/**
	 * will return the element at the given index
	 * @param idx desired index
	 * @return element at index
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Cannot acsess an out of bounds index");
		}
		if (idx == 0) {
			return front.data;
		}
		Node current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;

	}
	
	public int size() {
		return size;
	}

	/**
	 * returns a string representation of the list for testing
	 */
	public String toString() {
		if (size == 0) {
			return "[]";
		}
		String s = "[";
		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				s += get(i).toString() + "]";
			} else {
				s += get(i).toString() + ", ";
			}
		}
		return s;
	}
	
	
	
	
	
	/**
	 * Node class for holding elements in the linked list
	 * @author Hunter Mintz (hmintz)
	 *
	 */
	class Node {
		private E data;

		private Node next;

		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}

		public Node(E data) {
			this(data, null);
		}

	}
}



class MyTree<E> {
	Tnode<E> top;
	Tnode<E> visit;
	
	/**
	 * will create a tree with a root that holds the given object
	 * @param obj
	 */
	MyTree(E obj){
		top = new Tnode<E>(obj);

	}
	MyTree(){
		top = null;

	}
	
	Tnode<E> lookup(E obj) {
		return lookup(top, obj);
		
	}

	
	/**
	 * will look up and return the node of the tree that holds the given object
	 * uses preorder traversal
	 * @param obj
	 * @return the Tnode containing the given object
	 * 			null if it is not found
	 */
	Tnode<E> lookup(Tnode<E> root, E obj){
		Tnode<E> temp = root;
		if (temp.data.equals(obj)) {
			return temp;
		}
		if (temp.getNumChild() == 0) {
			return null;
		}
		LinkedList<Tnode<E>> children = temp.getChildren();
		
		for (int i = 0; i < children.size(); i++) {
			temp = lookup(children.get(i), obj);
			if (temp != null) {
				return temp;
			}
		}
		return null;
		
	}
	/**
	 * will get the height difference of the nodes that hold the given objects
	 * @param a
	 * @param b
	 * @return 
	 */
	 int heightDif(E a, E b) {
		 int dist = getHeight(a) - getHeight(b);
		
		 return dist > 0? dist : 0 - dist;
	}
	
	/**
	 * will ad the given object after the node that holds the given key
	 * @param key
	 * @param obj
	 */
	void addChild(E obj, E key) {
		Tnode<E> parent = lookup(key);
		parent.addChild(obj);
	}
	/**
	 * will add the given node as a child of the given root node
	 * @param root
	 * @param node
	 */
	void insert(Tnode<E> root, Tnode<E> node) {
		node.parent = root;
		root.getChildren().add(node);
	}
	
	/**
	 * will return the root of this tree "top"
	 * @return top
	 */
	Tnode<E> getRoot() {
		return top;
	}
	/**
	 * will set the root of this tree to the given object
	 * @param root
	 */
	void setRoot(Tnode<E> root) {
		this.top = root;
	}
	
	
	/**
	 * will return a reference to a newly created node, with no children or parent
	 * @param obj
	 * @return
	 */
	Tnode<E> makeNode(E obj){
		return new Tnode<E>(obj);
	}
	/**
	 * will return the height of the node that holds the given object
	 * @param obj
	 * @return
	 */
	int getHeight(E obj) {
		Tnode<E> temp = lookup(obj);
		int height = 0;
		while(temp.getParent() != null) {
			height++;
			temp = temp.getParent();
		}
		return height;
	}
	
	
	/**
	 * will mark all ancestors of the given element
	 * @param obj
	 */
	public void markAncestors(E obj) {
		Tnode<E> temp = lookup(obj) ;
		if (temp == null) {
			throw new IllegalArgumentException("element does not exist");
		}
		temp.markAncestors();
		
	}
	/**
	 * will unmark all of the ancestors of the Node that contains the given object
	 * @param obj
	 */
	public void unmarkAncestors(E obj) {
		Tnode<E> temp = lookup(obj) ;
		if (temp == null) {
			throw new IllegalArgumentException("element does not exist");
		}
		temp.unmarkAncestors();
		
	}
	/**
	 * will search the ancestors of the node containing the given object and return the data contained in the first marked node found
	 * @param obj the element to search up from
	 * @return the element contained in the first marked node
	 */
	public E getFirstCommonMarked(E obj) {
		Tnode<E> temp = lookup(obj);
		if (temp == null) {
			return null;
		}
		if (temp.isMarked) {
			return obj;
		}
		while(temp.parent != null) {
			if(temp.parent.isMarked) {
				return temp.parent.data;
			}
			temp = temp.parent;
		}
		return null;
	}
	/**
	 * will return a string representation of the preorder traversal for testing
	 * @return
	 */
	String preOrder() {
		String trav = "";
		if(top.getNumChild() == 0) {
			return top.data.toString();
		}
		trav = preOrder(top, trav);
		return trav;
		
	}
	String preOrder(Tnode<E> root, String trav) {
		Tnode<E> temp = root;
		trav += temp.data.toString();
		if(temp.getNumChild() == 0) {
			return trav;
		}
		
		LinkedList<Tnode<E>> children = temp.getChildren();
		for (int i = 0; i < children.size(); i++) {
			trav = preOrder(children.get(i), trav);
		}
		return trav;
	}
	/**
	 * will return a string representation of the post order traversal of this tree
	 * @return
	 */
	String postOrder() {
		String trav = "";
		if(top.getNumChild() == 0) {
			return top.data.toString();
		}
		trav = postOrder(top);
		return trav;
	}
	
	String postOrder(Tnode<E> root) {
		String trav = "";
		Tnode<E> temp = root;
		if(temp.getNumChild() == 0) {
			return trav += temp.data.toString();	
		}
		LinkedList<Tnode<E>> children = temp.getChildren();
		for(int i = 0; i < children.size(); i++) {
			trav += postOrder(children.get(i));
		}
		trav += temp.data.toString();
		return trav;
	}
	
	
	/**
	 * will return the level order of this tree as a string
	 * @param root the root to start with
	 * @return the level order traversal
	 */
	public String levelOrder(Tnode<E> root) {
		LinkedList<Tnode<E>> queue = new LinkedList<>();
		String s = "";
		if (root == null) {
			return null;
		}
		
		queue.add(root);
		s += root.data.toString();
		while(queue.size() != 0) {
			Tnode<E> temp = queue.get(0);
			queue.remove(0);
			for(int i = 0; i < temp.getNumChild(); i++) {
				Tnode<E> child = temp.getChild(i);
				s += child.data.toString();
				queue.add(child);
			}
		}
		return s;
	}
	
	
	

	
	
	
}

 class Tnode<E>{
	Tnode<E> parent;
	LinkedList<Tnode<E>> child;
	boolean isMarked;
	E data;
	/**
	 * create a new node with given parent, holding the given data
	 * @param parent
	 * @param data
	 */
	Tnode(Tnode<E> parent, E data){
		this.parent = parent;
		this.child = new LinkedList<>();
		this.data = data;
		isMarked = false;
	}
	/**
	 * create new node with no parent and no children
	 * @param data
	 */
	Tnode(E data){
		this.parent = null;
		this.child = new LinkedList<>();
		this.data = data;
		isMarked = false;
	}
	/**
	 * returns the list of children of this node
	 * @return
	 */
	LinkedList<Tnode<E>> getChildren(){
		return child;
	}
	/**
	 * will return the child at the given index of this node
	 * @param idx
	 * @return
	 */
	Tnode<E> getChild(int idx) {
		return child.get(idx);
	}
	/**
	 * returns the number of children this node has
	 * @return
	 */
	int getNumChild() {
		return child.size();
	}
	/**
	 * will return the parent of this node
	 * @return
	 */
	Tnode<E> getParent() {
		return parent;
	}
	/**
	 * will add a child to the end of the child list
	 * @param data
	 */
	void addChild(E data){
		child.add(new Tnode<E>(this, data));
	}
	/**
	 * will mark all ancestors of this node
	 */
	public void markAncestors() {
		isMarked = true;
		if (this.parent == null) {
			return;
		}
		parent.markAncestors();
		
	}
	public void unmarkAncestors() {
		isMarked = false;
		if (this.parent == null) {
			return;
		}
		parent.unmarkAncestors();
	}
}









public class MyTree<E> {
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
	void addChildNode(Tnode<E> root, Tnode<E> node) {
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
	
	public void markAnsestors(E obj) {
		Tnode<E> temp = lookup(obj) ;
		if (temp == null) {
			throw new IllegalArgumentException("element does not exist");
		}
		temp.markAncestors();
		
	}
	
	public void unmarkAnsestors(E obj) {
		Tnode<E> temp = lookup(obj) ;
		if (temp == null) {
			throw new IllegalArgumentException("element does not exist");
		}
		temp.unmarkAncestors();
		
	}
	
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
	
	public String toString() {
		return data.toString();
	}
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



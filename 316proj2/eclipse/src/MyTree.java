

public class MyTree<E> {
	Tnode top;
	Tnode visit;
	int size;
	
	/**
	 * will create a tree with a root that holds the given object
	 * @param obj
	 */
	MyTree(E obj){
		top = new Tnode(obj);
		size = 1;
	}
	
	Tnode lookup(E obj) {
		return lookup(top, obj);
		
	}

	
	/**
	 * will look up and return the node of the tree that holds the given object
	 * uses preorder traversal
	 * @param obj
	 * @return the Tnode containing the given object
	 * 			null if it is not found
	 */
	Tnode lookup(Tnode root, E obj){
		Tnode temp = root;
		if (temp.data.equals(obj)) {
			return temp;
		}
		if (temp.getNumChild() == 0) {
			return null;
		}
		LinkedList<Tnode> children = temp.getChildren();
		
		for (int i = 0; i < children.size(); i++) {
			temp = lookup(children.get(i), obj);
			if (temp != null) {
				return temp;
			}
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
	String preOrder(Tnode root, String trav) {
		Tnode temp = root;
		trav += temp.data.toString();
		if(temp.getNumChild() == 0) {
			return trav;
		}
		
		LinkedList<Tnode> children = temp.getChildren();
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
	
	String postOrder(Tnode root) {
		String trav = "";
		Tnode temp = root;
		if(temp.getNumChild() == 0) {
			return trav += temp.data.toString();	
		}
		LinkedList<Tnode> children = temp.getChildren();
		for(int i = 0; i < children.size(); i++) {
			trav += postOrder(children.get(i));
		}
		trav += temp.data.toString();
		return trav;
	}
	
	/**
	 * will ad the given object after the node that holds the given key
	 * @param key
	 * @param obj
	 */
	void addChild(E obj, E key) {
		Tnode parent = lookup(key);
		parent.addChild(obj);
		size++;
	}
	/**
	 * will return the height of the node that holds the given object
	 * @param obj
	 * @return
	 */
	int getHeight(E obj) {
		Tnode temp = lookup(obj);
		int height = 0;
		while(temp.getParent() != null) {
			height++;
			temp = temp.getParent();
		}
		return height;
	}
	
	
	

	public class Tnode{
		Tnode parent;
		LinkedList<Tnode> child;
		E data;
		/**
		 * create a new node with given parent, holding the given data
		 * @param parent
		 * @param data
		 */
		Tnode(Tnode parent, E data){
			this.parent = parent;
			this.child = new LinkedList<>();
			this.data = data;
		}
		/**
		 * create new node with no parent and no children
		 * @param data
		 */
		Tnode(E data){
			this.parent = null;
			this.child = new LinkedList<>();
			this.data = data;
		}
		/**
		 * returns the list of children of this node
		 * @return
		 */
		LinkedList<Tnode> getChildren(){
			if (child.size() == 0) {
				return null;
			}
			return child;
		}
		/**
		 * will return the child at the given index of this node
		 * @param idx
		 * @return
		 */
		Tnode getChild(int idx) {
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
		Tnode getParent() {
			return parent;
		}
		/**
		 * will add a child to the end of the child list
		 * @param data
		 */
		void addChild(E data){
			child.add(new Tnode(this, data));
		}
		
		public String toString() {
			return data.toString();
		}
		
	}
	
}



package util;

public class Tree<E> {
	Tnode root;
	Tnode visit;
	int size;
	
	/**
	 * will create a tree with a root that holds the given object
	 * @param obj
	 */
	Tree(E obj){
		root = new Tnode(obj);
		size = 1;
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
		LinkedList<Tnode> children = temp.getChildren();
		for (int i = 0; i < children.size(); i++) {
			lookup(children.get(i), obj);
		}
		return null;
		
	}
	
	
	String preOrder() {
		String trav = root.data.toString();
		trav = preOrder(root, trav);
		return trav;
		
	}
	
	String preOrder(Tnode root, String trav) {
		Tnode temp = root;
		trav += temp.data.toString();
		LinkedList<Tnode> children = temp.getChildren();
		for (int i = 0; i < children.size(); i++) {
			preOrder(children.get(i), trav);
		}
		return trav;
	}
	
	
	addChild(E root, E obj){
		
	}
	
	

	
	
	

	class Tnode{
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
		
	}
}


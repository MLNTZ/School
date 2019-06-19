package util;

public class Tree<E> {
	Tnode root;
	
	
	

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


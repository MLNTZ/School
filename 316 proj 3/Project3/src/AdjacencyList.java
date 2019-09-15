import java.util.Collections;
import java.util.LinkedList;
/**
 * represents an adjacency list
 * initialized as an array of linked list with size 5000 (max vertices)
 * @author hmintz  Hunter Mintz
 *
 */
public class AdjacencyList {
	LinkedList<Integer> list[];
	int numVertex;
	/**
	 * constructs empty adj list
	 */
	@SuppressWarnings("unchecked")
	public AdjacencyList() {
		this.numVertex = 0;
		list = new LinkedList[5000];
		for (int i = 0; i < 5000; i++ ) {
			list[i] = new LinkedList<>();
		}
	}
	/**
	 * adds the given edge to the adjacency list
	 * @param e the edge to add
	 */
	public void addEdge(Edge e) {
		list[e.getVertex1()].addFirst(e.getVertex2());
		list[e.getVertex2()].addFirst(e.getVertex1());
		numVertex = Math.max(numVertex, Math.max(e.getVertex1(), e.getVertex2()));
	}
	/**
	 * prints out the adjacency list 
	 */
	public String toString() {
		String s = "";
		for(int i = 0; i <= numVertex; i++) {
			LinkedList<Integer> tmp = list[i];
			Collections.sort(tmp);
			for (int j = 0; j < tmp.size(); j++) {
				s += String.format("%4s", tmp.get(j));
				s += " ";
			}
			s += "\n";
		}
		
		return s;
	}
}

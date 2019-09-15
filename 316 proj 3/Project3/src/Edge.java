import java.util.Comparator;
/**
 * will represent an edge in a graph
 * @author hmintz Hunter Mintz
 *
 */
public class Edge {
	private int vertex1;
	private int vertex2;
	private double weight;
	private Edge next;
	
	
	public Edge(int v1, int v2, double weight) {
		vertex1 = v1;
		vertex2 = v2;
		this.weight = weight;
		this.next = null;
	}
	/**
	 * will add the given as the next edge, and will return the this next edge
	 * @param e the edge to be added
	 * @return the edge created
	 */
	public Edge addNext(Edge e) {
		next = e;
		return next;
	}
	/**
	 * will compare the weights of 2 Edges
	 * @param that
	 * @return 1 if this goes after that
	 * 		  -1 if this goes before that
	 * 			0 if equal;
	 */
	public int compareWeight(Edge that) {
		if (that == null) {
			return 1;
		}
		
		if (this.weight > that.weight) {
			return 1;
		} 
		if (that.weight > this.weight) {
			return -1;
		}
		return 0;
	}
	public int getVertex1() {
		return vertex1;
	}
	public void setVertex1(int vertex1) {
		this.vertex1 = vertex1;
	}
	public int getVertex2() {
		return vertex2;
	}
	public void setVertex2(int vertex2) {
		this.vertex2 = vertex2;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Edge getNext() {
		return next;
	}
	public void setNext(Edge next) {
		this.next = next;
	}
	/**
	 * will print this edge to match formating for output
	 */
	public String toString() {
		String s = "";
		if (vertex1 > vertex2) {
			s += String.format("%4s", vertex2);
			s += " ";
			s += String.format("%4s", vertex1);
			return s;
		}
		s += String.format("%4s", vertex1);
		s += " ";
		s += String.format("%4s", vertex2);
		return s;
	}
	/**
	 * will sort edges lexicographic order
	 */
	public static Comparator<Edge> edgeSort = new Comparator<Edge>() {

		@Override
		public int compare(Edge a, Edge b) {
			int amin = Math.min(a.getVertex1(), a.getVertex2());
			int aMax = Math.max(a.getVertex1(), a.getVertex2());
			int bmin = Math.min(b.getVertex1(), b.getVertex2());
			int bMax = Math.max(b.getVertex1(), b.getVertex2());
			if (amin < bmin) {
				return - 1;
			} else if (bmin < amin) {
				return 1;
			}
			if (aMax < bMax) {
				return -1;
			} else if (bMax < aMax) {
				return 1;
			}
			return 0;
		}
	};
	
}

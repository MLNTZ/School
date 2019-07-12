
public class Edge {
	private int vertex1;
	private int vertex2;
	private int weight;
	private Edge next;
	
	
	public Edge(int v1, int v2, int weight) {
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
	public int compareTo(Edge that) {
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
	public int getWeight() {
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
	
	public String toString() {
		String s = "";
		s += String.format("%4s", vertex1);
		s += String.format("%4s", vertex2);
		return s;
	}
	
}

/**
 * array implementation of up tree
 * with path compression for find 
 * and balanced union
 * @author hmintz Hunter Mintz
 *
 */
public class UpTree {
	int[] set;
	int[] sizes;
	
	public UpTree(int setSize) {
		set = new int[setSize];
		sizes = new int[setSize];
		for (int i = 0; i < setSize; i++) {
			set[i] = i;
			sizes[i] = 1;
		}
		
	}
	/**
	 * will return the root index of the given element, and compress path as it does
	 * @param idx
	 * @return
	 */
	public int find(int idx) {
		while(set[idx] != idx) {
			int child = set[idx];
			set[idx] = set[child];
			idx = set[idx];
		}
		return idx;
	}
	/**
	 * will return true if the 2 elements given are in the same set
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean isConnected(int a, int b) {
		if(find(a) == find(b)) {
			return true;
		}
		return false;
	}
	/**
	 * will create a union between the two elements given
	 * @param a
	 * @param b
	 */
	public void union(int a, int b) {
		int aRep = find(a);
		int bRep = find(b);
		int aSize = sizes[aRep];
		int bSize = sizes[bRep];
		if (sizes[aRep] < sizes[bRep]) {
			set[aRep] = set[bRep];
			sizes[bRep] += aSize;
		} else {
			set[bRep] = set[aRep];
			sizes[aRep] += bSize;
		}
	}
	/**
	 * will print the contents of the set for testing
	 * @return
	 */
	public String printSet() {
		String s = "[";
		
		for (int i = 0; i < set.length; i++) {
			s += (int) set[i] / (int) 1;
			if (i != set.length - 1) {
				s += ", ";
			} else {
				s += "]";
			}
		}
		return s;
	}
	/**
	 * will print the contents of the sizes array for testing
	 * @return
	 */
	public String printSizes() {
		String s = "[";
		
		for (int i = 0; i < sizes.length; i++) {
			s += (int) sizes[i] / (int) 1;
			if (i != sizes.length - 1) {
				s += ", ";
			} else {
				s += "]";
			}
		}
		return s;
	}
}


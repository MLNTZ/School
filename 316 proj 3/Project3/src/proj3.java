import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * project 3 this program will read in a list of edges in a graph and print out a heap and MST as well as an adjacency list
 * @author hmintz Hunter Mintz
 *
 */
public class proj3 {
	public static EdgeHeap heap;
	public static AdjacencyList adjList;
	public static ArrayList<Edge> mst;
	public static void main(String[] args) {
		System.out.print("Input file: ");
		Scanner stdin = new Scanner(System.in);
		Scanner read = null;
		PrintStream write = null;
		try {
			read = new Scanner(new File(stdin.next()));
			System.out.print("\nOutput file: ");
			write = new PrintStream(new File(stdin.next()));
		} catch (FileNotFoundException e) {
			System.out.println("error opening files");
		}
		heap = new EdgeHeap(5001);
		adjList = new AdjacencyList();
		readHeap(read);
		printHeap(write);
		//write.println();
		Kruskal();
		mst.sort(Edge.edgeSort);
		printMst(write);
		write.print(adjList.toString());
		
		read.close();
		write.close();
		stdin.close();
	}
	
	private static void printMst(PrintStream write) {
		for (int i = 0; i < mst.size(); i++) {
			write.println(mst.get(i).toString());
		}
		
	}

	public static void readHeap(Scanner read) {
		int v1 = 0;
		int v2 = 0;
		double weight = 0;
		while((v1 = read.nextInt()) != -1) {
			v2 = read.nextInt();
			weight = read.nextDouble();
			Edge tmp = new Edge(v1, v2, weight);
			heap.insert(tmp);
			adjList.addEdge(tmp);
		}
	}
	
	
	public static void printHeap(PrintStream write) {
		Edge[] heapArray = heap.getArray();
		for (int i = 1; i <= heap.size; i++) {
			write.println(heapArray[i].toString());
		}
	}
	
	
	public static void Kruskal() {
		Edge[] heapArray = heap.getArray();
		Edge temp = null;
		mst = new ArrayList<>();
		UpTree upTree = new UpTree(heapArray.length);
		int v1 = -1;
		int v2 = -1;
		while (heap.size != 0) {
			temp = heap.getMin();
			v1 = temp.getVertex1();
			v2 = temp.getVertex2();
			if(!upTree.isConnected(v1, v2)) {
				upTree.union(v1, v2);
				mst.add(temp);
			}
		}
	}
	
	

}

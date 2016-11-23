import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class EdgeAndVer {
	int edgeLength;
	int vertex;

	EdgeAndVer(int vertex, int edgeLength) {
		this.edgeLength = edgeLength;
		this.vertex = vertex;
	}

	public String toString() {
		return ("[ "+Integer.toString(vertex) + "," + Integer.toString(edgeLength) + " ]");
	}

}

class EdgeAndVerComparator implements Comparator<EdgeAndVer> {
	public int compare(EdgeAndVer first, EdgeAndVer second) {
		return first.edgeLength - second.edgeLength;
	}
}


class Dijkstra {

	//Number of vertices
	int A[] = new int[201];

	HashMap<Integer, ArrayList<EdgeAndVer>> graph = new HashMap<>();
	Set<Integer> allVerts = new HashSet<Integer>();

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/dijkstras/dijkstras.txt";
		List<String> list = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			for (String str : list) {
					String arr[] = str.split("\t");
					
					int first = Integer.parseInt(arr[0]);
					allVerts.add(first);				
					ArrayList<EdgeAndVer> list1 = new ArrayList<EdgeAndVer>();

					for (int i = 1; i < arr.length; i++) {
						String second[] = arr[i].split(",");
						EdgeAndVer ev = new EdgeAndVer(Integer.parseInt(second[0]), Integer.parseInt(second[1]));
						list1.add(ev);
					}

					graph.put(first, list1);
					
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		for (int i = 1; i < A.length; i++) {
			A[i] = 1000000;
		}

		System.out.println(graph);
		System.out.println("DONE");

	}

	public void doDijkstra() {
		Set<Integer> initialVerts = new HashSet<Integer>();
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(allVerts);
		set.remove(1);
		initialVerts.add(1);
		A[1] = 0;
		//PriorityQueue<EdgeAndVer> pq = new PriorityQueue<EdgeAndVer>(new EdgeAndVerComparator()); 

		while (initialVerts.size() != allVerts.size()) {

			Set<Integer> s = new HashSet<Integer>();
			for (int i : initialVerts) {
				s.add(i);
			}

			int vert = -1;
			int min = 7000000;
			for (int i : s) {
				int val = A[i];

				if (graph.get(i) != null) {
					for (EdgeAndVer ev : graph.get(i)) {
						if (!initialVerts.contains(ev.vertex)) {
							if (ev.edgeLength + val < min) {
								min = ev.edgeLength + val;
								vert = ev.vertex;
							}
						}
	
					}
				}

			}
			//System.out.println("baznga : the vert = " + vert);

			if (vert != -1) {
				initialVerts.add(vert);
				set.remove(vert);
				A[vert] = min;
			}

		}
	}
	public static void main(String[] args) {
		Dijkstra dijkstras = new Dijkstra();
		dijkstras.readFile();
		dijkstras.doDijkstra();
		int arr[] = {7,37,59,82,99,115,133,165,188,197};
		//int arr[] = {1,2,3,4,5,6,7,8,9,10};
		for (int i = 0; i < arr.length; i++) {
			System.out.print(dijkstras.A[arr[i]]+",");
		}
		System.out.println();
		//System.out.print(dijkstras.A[99]);
	}
}
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class AdjVerEdge {

	private int ver;
	private int edge;

	AdjVerEdge(int ver, int edge) {
		this.ver = ver;
		this.edge = edge;
	}

	public String toString() {
		return Integer.toString(ver) + " " + Integer.toString(edge);
	}

	public int getVer() {
		return ver;
	}

	public int getEdge() {
		return edge;
	}


}

class AdjVerEdgeComparator implements Comparator<AdjVerEdge> {
	public int compare(AdjVerEdge first, AdjVerEdge second) {
		return first.getEdge() - second.getEdge();
	}
}

class Prims {

	HashMap<Integer, ArrayList<AdjVerEdge>> graph = new HashMap<>();
	int numberOfVerts = 0;
	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/greedy/prims.txt";
		Set<Integer> totalNumberOfVerts = new HashSet<Integer>();
		ArrayList<String> list = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {
					int first = Integer.parseInt(str.substring(0,str.indexOf(" ")));
					int second = Integer.parseInt(str.substring(str.indexOf(" ") + 1, str.lastIndexOf(" ")));
					int third = Integer.parseInt(str.substring(str.lastIndexOf(" ") + 1));
					AdjVerEdge adjVerEdge1 = null;
					AdjVerEdge adjVerEdge2 = null;
					if (!graph.containsKey(first)) {
						ArrayList<AdjVerEdge> newList = new ArrayList<>();
						newList.add(new AdjVerEdge(second, third));
						graph.put(first, newList);
						
					} else {
						graph.get(first).add(new AdjVerEdge(second, third));
					}

					if (!graph.containsKey(second)) {
						ArrayList<AdjVerEdge> newList1 = new ArrayList<>();
						newList1.add(new AdjVerEdge(first, third));
						graph.put(second, newList1);
					} else {
						graph.get(second).add(new AdjVerEdge(first, third));
					}

					totalNumberOfVerts.add(first);
					totalNumberOfVerts.add(second);

				}
				flag = 1;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		numberOfVerts = totalNumberOfVerts.size();

		System.out.println("The number of vertices = " + numberOfVerts);
	}

	public int getMST() {
		//System.out.println(graph);
		Set<Integer> setOfVertices = new HashSet<Integer>();
		//we can start with any random vertice but starting with "1"
		setOfVertices.add(1);
		
		PriorityQueue<AdjVerEdge> queue = new PriorityQueue<AdjVerEdge>(new AdjVerEdgeComparator());
		
		for (AdjVerEdge ave: graph.get(1)) {
			queue.add(ave);
		}
		int totalMST = 0;

		while (setOfVertices.size() != numberOfVerts) {

			while (!queue.isEmpty()) {

				AdjVerEdge adjVerEdge = queue.poll();
				if (setOfVertices.add(adjVerEdge.getVer())) {
					for (AdjVerEdge ave: graph.get(adjVerEdge.getVer())) {
						queue.add(ave);
					}
					totalMST += adjVerEdge.getEdge();
					break;
				}
			}

		}

		return totalMST;

	}
	public static void main(String[] args) {
		Prims prims = new Prims();
		prims.readFile();
		System.out.println("Total MST cost = " + prims.getMST());

	}
}
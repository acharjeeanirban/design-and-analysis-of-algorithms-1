import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Edge {

	String firstVertex;
	String secondVertex;

	Edge(String firstVertex, String secondVertex) {
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
	}

	public String toString() {
		return ("[ "+firstVertex + " " + secondVertex + " ]");
	}
}

class Node {

	String value;
	Node leader;
	int numberOfChildren = 1;
	Set<Node> listOfChilden = new HashSet<Node>();

	Node(String value) {
		this.value = value;
		leader = this;
		//listOfChilden.add(this);
	}

	public void setLeader(Node leader) {
		this.leader = leader;
	}

	public String toString() {
		return this.value;
		//return Integer.toString(numberOfChildren);
	}

	public String getValue() {
		return value;
	}

	public Node getLeader() {
		return leader;
	}

	public boolean equals(Object obj) {
		Node node = (Node) obj;
		return (node.value.equals(this.value));
	}

	public int hashCode() {
		return value.length();
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setnumberOfChildren() {
		//return numberOfChildren;
	}

}

class MinCut {

	ArrayList<Edge> allEdges = new ArrayList<Edge>();
	ArrayList<Edge> allEdgesDuplicate = new ArrayList<Edge>();
	HashMap<String, ArrayList<String>> graphList = new HashMap<String, ArrayList<String>>();
	HashMap<String, Node> nodeGraph = new HashMap<>();

	int min = 0;

	public int getRandom1(int min, int max) {
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}

	public int getRandom(int min, int max) {
		return (int)(Math.random() * ( max - min ));
	}

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/min-cut/min_cut.txt";
		List<String> list = new ArrayList<>();
		Node node = null;

		
		//int count = 1;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			for (String str : list) {
					String arr[] = str.split("\t");
					
					String first = arr[0];
					node = new Node(first);
					nodeGraph.put(first, node);
					ArrayList<String> list1 = new ArrayList<String>();

					for (int i = 1; i < arr.length; i++) {
						String second = arr[i];
						if (first.equals(second)) {
							continue;
						}
						if (graphList.get(second) != null) {
							if (!graphList.get(second).contains(first)) {
								Edge edge1 = new Edge(first, second);
								allEdges.add(edge1);
							}
						} else {
							Edge edge = new Edge(first, second);
							allEdges.add(edge);
						}

						//set.add(first);
						//set.add(second);
						//graph.put(count, edge);
						list1.add(second);
						
					}

					graphList.put(arr[0], list1);
					
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		// System.out.println(graphList);
		// System.out.println(allEdges);
		// System.out.println(nodeGraph);

		for (Edge e : allEdges) {
			allEdgesDuplicate.add(e);
		}

		System.out.println("DONE");

	}

	public void union(Node first, Node second) {

		//System.out.println("in union : " + first + " , " + second);

		if (first.listOfChilden.size() < second.listOfChilden.size()) {
			//System.out.println("3");
			for (Node node : first.listOfChilden) {

				node.setLeader(second);
				second.listOfChilden.add(node);
				node.numberOfChildren = 0;
				//node.listOfChilden.clear();
			}
			first.listOfChilden.clear();
			first.numberOfChildren = 0;
			first.setLeader(second);
			second.listOfChilden.add(first);
			second.listOfChilden.add(second);
			second.numberOfChildren = second.listOfChilden.size();

			//finalClusters.remove(first);
			//finalClusters.add(second);
		} else {
			
			for (Node node : second.listOfChilden) {
				node.setLeader(first);
				first.listOfChilden.add(node);
				//node.listOfChilden.clear();
				node.numberOfChildren = 0;

			}
			second.listOfChilden.clear();
			second.setLeader(first);
			second.numberOfChildren = 0;
			first.listOfChilden.add(first);
			first.listOfChilden.add(second);
			first.numberOfChildren = first.listOfChilden.size();

			//System.out.println("4 : " + second.numberOfChildren + ", " + first.numberOfChildren);
			//finalClusters.remove(second);
			//finalClusters.add(first);
		}
	}


	public int doMinCut() {

		while (!areTwoClustersLeft()) {
			int randomEdge = getRandom(0,allEdges.size());
			//System.out.println("random edge = " + randomEdge);
			//System.out.println("nodeGraph = " + nodeGraph);
			Edge edge = allEdges.remove(randomEdge);
			if (edge != null) {
			//	System.out.println(edge);
				String first = edge.firstVertex;
				String second = edge.secondVertex;
				Node firstNode = nodeGraph.get(first);
				Node secondNode = nodeGraph.get(second);

				if (firstNode.getLeader() != secondNode.getLeader()) {
					union(firstNode.getLeader(), secondNode.getLeader());
				}

			}
		}

		int count = 0;

		for (Edge e : allEdgesDuplicate) {
			if (nodeGraph.get(e.firstVertex).getLeader() != nodeGraph.get(e.secondVertex).getLeader()) {
				count++;
			}
		}

		return count;
	}

	public boolean areTwoClustersLeft() {
		int count = 0;
		for (Node n : nodeGraph.values()) {
			if (n.numberOfChildren >= 1) {
				count++;
			}
		}

		if (count == 2) {
			return true;
		} else {
			return false;
		}
	}


	public static void main(String[] args) {
		int min = 1000000;
		for (int i = 0; i < 50; i++) {
			MinCut minCut = new MinCut();
			minCut.readFile();

			int val = minCut.doMinCut();
			if (val < min) {
				min = val;
			}
		}
		System.out.println("The min cut is = " + min);


	}
}
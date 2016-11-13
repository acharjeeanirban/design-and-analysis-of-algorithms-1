import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class VerVerEdge {

	private int ver1;
	private int ver2;
	private int edge;

	VerVerEdge(int ver1, int ver2, int edge) {
		this.ver1 = ver1;
		this.ver2 = ver2;
		this.edge = edge;
	}

	public String toString() {
		return Integer.toString(ver1) + " " +Integer.toString(ver2)+ " " +Integer.toString(edge);
	}

	public int getVer1() {
		return ver1;
	}

	public int getVer2() {
		return ver2;
	}

	public int getEdge() {
		return edge;
	}

}

class VerVerEdgeComparator implements Comparator<VerVerEdge> {
	public int compare(VerVerEdge first, VerVerEdge second) {
		return first.getEdge() - second.getEdge();
	}
}


class Clusters {

	//HashMap<Integer, ArrayList<AdjVerEdge>> graph = new HashMap<>();
	Set<Integer> totalNumberOfVerts = new HashSet<Integer>();
	PriorityQueue<VerVerEdge> queue = new PriorityQueue<VerVerEdge>(new VerVerEdgeComparator());
	PriorityQueue<VerVerEdge> queue1 = new PriorityQueue<VerVerEdge>(new VerVerEdgeComparator());

	public void doClustering(int k) {

		ArrayList<ArrayList<Integer>> clusters = new ArrayList<ArrayList<Integer>>();
		for (int in : totalNumberOfVerts) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(in);
			clusters.add(list);
		}
						
		ArrayList<Integer> listOfIndexes = new ArrayList<Integer>();

		while (!queue.isEmpty()) {
			VerVerEdge obj = queue.poll();
			int first = obj.getVer1();
			int second = obj.getVer2();
			
			ArrayList<Integer> newCluster = new ArrayList<Integer>();
			for (int i = 0; i < clusters.size(); i++) {
				if (clusters.get(i).contains(first) || clusters.get(i).contains(second)) {
					listOfIndexes.add(i);
				}
			}
			
			for (int in : listOfIndexes) {
				newCluster.addAll(clusters.get(in));
			}

			int k1 = 0;
			int flag = 0;
			for (int in : listOfIndexes) {
				if (flag == 0) {
					clusters.remove(in);
					flag = 1;
					//k = in;	
				} else {
					k1 = in;
					clusters.remove(--k1);
				}
				
			}

			listOfIndexes.clear();

			clusters.add(newCluster);			

			if (clusters.size() == k) {
				break;
			}
		}

		int distance = 0;

		while (!queue1.isEmpty()) {
			VerVerEdge vve = queue1.poll();

			for (ArrayList<Integer> in : clusters) {
				if (in.contains(vve.getVer1()) && !in.contains(vve.getVer2())) {
					distance = vve.getEdge();
					break;
				}
			}

			if (distance != 0) {
				break;
			}

		}
		System.out.println("maximum spacing : " + distance);

	}

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/greedy/clustering1.txt";
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {
					int first = Integer.parseInt(str.substring(0,str.indexOf(" ")));
					int second = Integer.parseInt(str.substring(str.indexOf(" ") + 1, str.lastIndexOf(" ")));
					int third = Integer.parseInt(str.substring(str.lastIndexOf(" ") + 1));
					VerVerEdge obj = new VerVerEdge(first, second, third);
					queue.offer(obj);
					queue1.offer(obj);
					totalNumberOfVerts.add(first);
					totalNumberOfVerts.add(second);

				}
				flag = 1;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Clusters clusters = new Clusters();
		clusters.readFile();
		clusters.doClustering(4);
	}
}

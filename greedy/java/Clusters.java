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

	HashMap<Integer, ArrayList<AdjVerEdge>> graph = new HashMap<>();
	Set<Integer> totalNumberOfVerts = new HashSet<Integer>();
	PriorityQueue<VerVerEdge> queue = new PriorityQueue<VerVerEdge>(new VerVerEdgeComparator());



	public void doClustering(int k) {
		ArrayList<ArrayList<Integer>> clusters = new ArrayList<ArrayList<Integer>>();
		for (int in : totalNumberOfVerts) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(in);
			clusters.add(list);
		}

		System.out.println("bazinga = " + clusters);
		//clusters.remove(0);
		System.out.println("bazinga = " + clusters);
		ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> test1 = new ArrayList<Integer>();
		test1.add(1);
		test.add(test1);
		System.out.println(test);
		test.remove(0);
		System.out.println(test);
		System.out.println("The initial number of clusters = " + clusters.size());
		System.out.println("The initial number of clusters = " + queue.size());
		
		ArrayList<Integer> listOfIndexes = new ArrayList<Integer>();
		int count = 1;

		//while (clusters.size() != k) {
			while (!queue.isEmpty()) {
				VerVerEdge obj = queue.poll();
				int first = obj.getVer1();
				int second = obj.getVer2();
				
				ArrayList<Integer> newCluster = new ArrayList<Integer>();
				for (int i = 0; i < clusters.size(); i++) {
					if (clusters.get(i).contains(first) || clusters.get(i).contains(second)) {
						//System.out.println(count);
						//newCluster.addAll(clusters.get(i));
						listOfIndexes.add(i);
					}
				}
				System.out.println("listOfIndexes = " + listOfIndexes);

				for (int in : listOfIndexes) {
					newCluster.addAll(clusters.get(in));
				}

				System.out.println("clusters1 = " + clusters);

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
					
					//System.out.println("Ttttttttt " + in);
					
				}

				System.out.println("clusters2 = " + clusters);

				listOfIndexes.clear();
				//System.out.println("the size of listOfIndexes = " + listOfIndexes.size());

				clusters.add(newCluster);
				//newCluster.clear();

				System.out.println("clusters3 = " + clusters);

				System.out.println("==================================================");				

				if (clusters.size() == k) {
					break;
				}
				count++;
			}


		//}

		System.out.println("ALL DONE " + clusters);

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

	}

	public static void main(String[] args) {
		Clusters clusters = new Clusters();
		clusters.readFile();
		clusters.doClustering(3);
	}
}

import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class HammingNode {

	String value;
	HammingNode leader;
	int numberOfChildren = 1;
	Set<HammingNode> listOfChilden = new HashSet<HammingNode>();

	HammingNode(String value) {
		this.value = value;
		leader = this;
	}

	public void setLeader(HammingNode leader) {
		this.leader = leader;
	}

	public String toString() {
		return this.value;
	}

	public String getValue() {
		return value;
	}

	public HammingNode getLeader() {
		return leader;
	}

	public boolean equals(Object obj) {
		HammingNode node = (HammingNode) obj;
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


class Hamming {

	HashMap<String, HammingNode> graph = new HashMap<>();

	public int getDistance(String s1, String s2) {
		char arr1[] = s1.toCharArray();
		char arr2[] = s2.toCharArray();
		int distance = 0;
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				distance++;
			}
		}

		return distance;
	}

	public ArrayList<HammingNode> getOneOrTwoAway(String input) {
		//System.out.println("The getOneOrTwoAway : " + input);

		ArrayList<HammingNode> list = new ArrayList<HammingNode>();
		char arr[] = input.toCharArray();
		StringBuffer sb = new StringBuffer();
		char changeI = 'a';
		HammingNode newNode = null;
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == '1') {
				sb.append(input.substring(0,i)).append('0').append(input.substring(i+1));
			} else {
				sb.append(input.substring(0,i)).append('1').append(input.substring(i+1));
			}
			
			if (graph.containsKey(sb.toString())) {
				//newNode = new HammingNode(sb.toString());
				list.add(graph.get(sb.toString()));
			}
			
			sb.setLength(0);
		}
	
		for (int i = 0; i < arr.length-1; i++) {

			if (arr[i] == '1') {
					changeI = '0';
			} else {
					changeI = '1';
			}

			for (int j = i+1; j < arr.length; j++) {
				if (arr[j] == '1') {
					sb.append(input.substring(0,i)).append(changeI).append(input.substring(i+1, j)).append('0').append(input.substring(j+1));
				} else {
					sb.append(input.substring(0,i)).append(changeI).append(input.substring(i+1, j)).append('1').append(input.substring(j+1));
				}
				

				if (graph.containsKey(sb.toString())) {
					//newNode = new HammingNode(sb.toString());
					list.add(graph.get(sb.toString()));
				}
				sb.setLength(0);		
			}
				
		}

		// if (input.equals("1111111111000000")) {
		// 	System.out.println("bazinga");
		// 	System.out.println(list);
		// }
		
		return list;
	}

	Set<HammingNode> listOfHammingNodes = new HashSet<>();
	Set<String> setOfNodesAsStrings = new HashSet<String>();


	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/greedy/clustering_big.txt";
		List<String> list = new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		String origin = "000000000000000000000000";
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {

					String arr[] = str.split(" ");
					for (String s : arr) {
						sb.append(s);
					}

					String newNode = sb.toString();
					setOfNodesAsStrings.add(newNode);
					HammingNode hammingNode = new HammingNode(newNode);
					graph.put(newNode, hammingNode);
					listOfHammingNodes.add(hammingNode);

					sb.setLength(0);

					//System.out.println();

				}
				flag = 1;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		System.out.println("DONE");

	}

	Set<HammingNode> finalClusters = new HashSet<>();

	public void union(HammingNode first, HammingNode second) {
		//System.out.println("inside union");
		//System.out.println("bazinga : " + first.listOfChilden.size() + " , " + second.listOfChilden.size());
		//System.out.println("bazinga : " + first + " , " + second);

		// if (first.getValue().equals("1111111111000000")) {
		// 	System.out.println("bazinga : 1 " + first + " , " + second);
		// 	System.out.println(first.listOfChilden.size());
		// 	System.out.println(second.listOfChilden.size());
		// }

		// if (second.getValue().equals("1111111111000000")) {

		// 	System.out.println("TTTTTTTTTTTTTTTTT");
		// 	System.out.println("bazinga : 2 " + first + " , " + second);
		// 	System.out.println(first.listOfChilden.size());
		// 	System.out.println(second.listOfChilden.size());
		// }

		//if (second.listOfChilden.contains(first)
		if (first.listOfChilden.size() < second.listOfChilden.size()) {
			//System.out.println("3");
			for (HammingNode node : first.listOfChilden) {
				// if (node.getValue().equals("1111111111000000")) {
				// 	System.out.println("BOOM 1");
				// }
				node.setLeader(second);
				second.listOfChilden.add(node);
			}
			first.listOfChilden.clear();
			first.setLeader(second);
			second.listOfChilden.add(first);
			second.listOfChilden.add(second);
			// if (first.getValue().equals("1111111111000000")) {
			// 	System.out.println("BOOM 3");
			// }
			finalClusters.remove(first);
			finalClusters.add(second);
		} else {
			//System.out.println("4");
			for (HammingNode node : second.listOfChilden) {
				node.setLeader(first);
				first.listOfChilden.add(node);
				// if (node.getValue().equals("1111111111000000")) {
				// 	System.out.println("BOOM 2");
				// }
			}
			second.listOfChilden.clear();
			second.setLeader(first);
			first.listOfChilden.add(first);
			first.listOfChilden.add(second);
			finalClusters.remove(second);
			finalClusters.add(first);
		}
	}

	public void doUF() {
		for (HammingNode node : listOfHammingNodes) {
			for (HammingNode neighbour : getOneOrTwoAway(node.getValue())) {
				//System.out.println("1");
				//System.out.println(neighbour);
				if (node.getLeader() != neighbour.getLeader()) {
					// if (node.getValue().equals("1111111111000000")) {
					// 	System.out.println("found");
					// 	System.out.println(node.getLeader());
					// 	System.out.println(neighbour.getLeader());
					// }
					//System.out.println("2");
					union(node.getLeader(), neighbour.getLeader());
				}
			}
		}
	}

	public static void main(String[] args) {
		Hamming hamming = new Hamming();
		hamming.readFile();

		//System.out.println("The size = " + hamming.setOfNodesAsStrings);
		hamming.doUF();
		for (HammingNode node : hamming.finalClusters) {
			//System.out.println(node.listOfChilden);
		}
		System.out.println(hamming.finalClusters.size());
		//System.out.println(hamming.getDistance("001", "111"));

		//System.out.println(hamming.getOneOrTwoAway("1000"));

	}
} 
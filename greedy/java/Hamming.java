import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class HammingNode {

	String value;
	HammingNode leader;

	HammingNode(String value) {
		this.value = value;
		leader = this;
	}

	public void setLeader(HammingNode leader) {
		this.leader = leader;
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

}


class Hamming {

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

	public ArrayList<String> getOneOrTwoAway(String input) {
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
			newNode = new HammingNode(sb.toString());
			if (listOfHammingNodes.contains(newNode) {

			}
			list.add(newNode);
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
				newNode = new HammingNode(sb.toString());

				list.add(newNode);
				sb.setLength(0);		
			}
				
		}
		
		return list;
	}

	List<HammingNode> listOfHammingNodes = new ArrayList<>();
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
					HammingNode hammingNode = new HammingNode(newNode);
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

	public void doUF() {
		for (HammingNode node : listOfHammingNodes) {
			for (HammingNode neighbours : getOneOrTwoAway(node.getValue())) { 

			}
		}
	}

	public static void main(String[] args) {
		Hamming hamming = new Hamming();
		hamming.readFile();
		//System.out.println(hamming.getDistance("001", "111"));

		System.out.println(hamming.getOneOrTwoAway("1000"));

	}
} 
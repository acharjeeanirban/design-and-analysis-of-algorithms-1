import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

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

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/greedy/clustering_big.txt";
		List<String> list = new ArrayList<>();
		List<String> list1 = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {
					// int first = Integer.parseInt(str.substring(0,str.indexOf(" ")));
					// int second = Integer.parseInt(str.substring(str.indexOf(" ") + 1, str.lastIndexOf(" ")));
					// int third = Integer.parseInt(str.substring(str.lastIndexOf(" ") + 1));
					// VerVerEdge obj = new VerVerEdge(first, second, third);
					// queue.offer(obj);
					// queue1.offer(obj);
					// totalNumberOfVerts.add(first);
					// totalNumberOfVerts.add(second);

					String arr[] = str.split(" ");
					for (String s : arr) {
						sb.append(s);
						//System.out.print(s);
					}

					list1.add(sb.toString());

					//System.out.println();

				}
				flag = 1;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		int count = 0;
		Set<String> set = new HashSet<>();
		ArrayList<ArrayList<String>> biglist = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < list1.size(); i++) {
			//count++;
			for (int j = i+1; j < list1.size(); j++) {
				String s1 = list1.get(i);
				String s2 = list1.get(j);
				if (getDistance(s1, s2) <= 2) {
					set.add(s1);
					set.add(s2);
				}
			}


		}

		System.out.println("DONE");



	}

	public static void main(String[] args) {
		Hamming hamming = new Hamming();
		hamming.readFile();
		System.out.println(hamming.getDistance("001", "111"));
	}
} 
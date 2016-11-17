import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


class Median {

	int arr[];


	public int findMedian() {
		PriorityQueue<Integer> pqLower = new PriorityQueue(Collections.reverseOrder());
		PriorityQueue<Integer> pqHigher = new PriorityQueue();
		List<Integer> median = new ArrayList<Integer>();

		// for (int i = 0; i < arr.length; i++) {
		// 	pqLower.offer(arr[i]);
		// 	pqHigher.offer(arr[i]);
		// }

		//System.out.println(pqLower.peek());
		//System.out.println(pqHigher.peek());
		for (int i = 0; i < arr.length; i++) {

			if (i == 0) {
				pqLower.offer(arr[i]);
				median.add(pqLower.peek());
				//continue;
			} else {
				System.out.println("bazinga " + arr[i] + " peak : " + pqLower.peek());
				if (pqLower.size() == pqHigher.size()) {
					if (arr[i] < pqHigher.peek()) {
						pqLower.offer(arr[i]);
					} else {
						pqLower.offer(pqHigher.poll());
						pqHigher.offer(arr[i]);
					}
				} else {

					if (arr[i] < pqLower.peek()) {

						pqHigher.offer(pqLower.poll());
						pqLower.offer(arr[i]);
						
					} else {
						pqHigher.offer(arr[i]);
					}
				}



				median.add(pqLower.peek());
			}



			// if (i % 2 == 0) {
			// 	median.add(pqLower.peek());
			// } else {
			// 	median.add(pqHigher.peek());
			// }
		}

		int sum = 0;
		for (int i : median) {
			//System.out.println("median = " + i);
			sum += i;
		}
		//System.out.println(sum);

		return (sum % 10000);

		//return 0;
	}


	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/data-structures/median.txt";
		List<String> list = new ArrayList<>();
		
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			arr = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arr[i] = Integer.parseInt(list.get(i));
			}				
			
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		for (int i = 0; i < arr.length; i++) {
			//System.out.println(arr[i]);
		}

		System.out.println("DONE");
		//Arrays.sort(arr);

	}
	public static void main(String[] args) {
		Median median = new Median();
		median.readFile();
		System.out.println(median.findMedian());
	}
}
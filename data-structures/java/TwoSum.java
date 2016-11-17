import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class TwoSum {
	long arr[];

	public long doTwoSum(int lowerRange, int higherRange) {

		long count = 0;
		for (int target = lowerRange; target <= higherRange; target++) {
			long t = target;
			int start = 0;
			int end = arr.length-1;
			while (start < end) {
				long sum = (arr[start] + arr[end]);
				if (sum == t) {
					count++;
					break;
					//start++;
					//end--;
				} else if (sum > t) {
					end--;
				} else {
					start++;
				}
			}
		}
		

		return count;
	}
	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/data-structures/2_sum.txt";
		List<String> list = new ArrayList<>();
		
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			arr = new long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arr[i] = Long.parseLong(list.get(i));
			}				
			
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		System.out.println("DONE");
		Arrays.sort(arr);

	}
	public static void main(String[] args) {
		TwoSum twoSum = new TwoSum();
		twoSum.readFile();
		System.out.println("DONE");
		// for (int i = 0; i < twoSum.arr.length; i++) {
		// 	System.out.println(twoSum.arr[i]);
		// }
		System.out.println(twoSum.doTwoSum(-10000,10000));
		
	}
}
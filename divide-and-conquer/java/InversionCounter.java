import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;


class InversionCounter {

	//static int input[];

	int input[];
	int auxillary[];

	public void readFile() {

		//change path according to machine
		String fileName = "/Users/anirbanacharjee/Documents/ass1_algo.txt";

		ArrayList<Integer> list = new ArrayList<>();

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			//stream.forEach(System.out::println);
			stream.forEach(s -> list.add(Integer.parseInt(s)));
			//System.out.println("The list is = " + list);
			input = new int[list.size()];

			int k = 0;
			for (Integer i : list) {
				input[k] = i;
				//System.out.println(input[k]);
				k++;
			}
			auxillary = new int[input.length];

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int numberOfInversionQuadratic() {
		int count = 0;

		for (int i = 0; i < input.length; i++) {
			System.out.print(input[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < input.length-1; i++) {
			for (int j = i+1; j < input.length; j++) {
				if (input[i] > input[j]) {
					//System.out.println("In here,, " + input[i] + " " + input[j]);
					count++;
				}
			}
		}
		System.out.println("The number of inversions are : " + count);
		return count;
	}


	public long doMergeAndCount(int low, int high) {

		if (low < high) {
			int mid = low + ((high - low) / 2);
			long x = doMergeAndCount(low, mid);
			long y = doMergeAndCount(mid+1, high);
			long z = countCrossInversion(low, mid, high);
			return x + y + z;
		} 

		return 0;
	}

	public long countCrossInversion(int low, int mid, int high) {
		int lowerLow = low;
		int lowerHigh = mid;
		int upperLow = mid + 1;
		int upperHigh = high;

		int k = 0;
		int numOfelements = high - low + 1;

		long count = 0;

		//// 0 1 2 3 4 5

		while (lowerLow <= lowerHigh && upperLow <= upperHigh) {
			if (input[lowerLow] < input[upperLow]) {
				auxillary[k++] = input[lowerLow++];
			} else {
				count += lowerHigh - lowerLow + 1;
				auxillary[k++] = input[upperLow++];
			}
		}

		while (lowerLow <= lowerHigh) {
			auxillary[k++] = input[lowerLow++];
		}

		while (upperLow <= upperHigh) {
			auxillary[k++] = input[upperLow++];
		}

		for (int i = 0; i < numOfelements; i++) {
			input[low + i] = auxillary[i];
		}

		return count;

	}

	public static void main(String args[]) {
		InversionCounter inversionCounter = new InversionCounter();
		inversionCounter.readFile();
		//inversionCounter.numberOfInversionQuadratic();
		System.out.println("The number of inversions are " + inversionCounter.doMergeAndCount(0, inversionCounter.input.length -1));
	}
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;


class InversionCounter {

	static int input[];

	public static void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/inversion.txt";

		ArrayList<Integer> list = new ArrayList<>();

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			//stream.forEach(System.out::println);
			stream.forEach(s -> list.add(Integer.parseInt(s)));
			System.out.println("The list is = " + list);
			input = new int[list.size()];

			int k = 0;
			for (Integer i : list) {
				input[k] = i;
				System.out.println(input[k]);
				k++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int numberOfInversionQuadratic() {
		int count = 0;

		for (int i = 0; i < input.length; i++) {
			System.out.print(input[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < input.length-1; i++) {
			for (int j = i+1; j < input.length; j++) {
				if (input[i] > input[j]) {
					System.out.println("In here,, " + input[i] + " " + input[j]);
					count++;
				}
			}
		}
		System.out.println("The number of inversions are : " + count);
		return count;
	}

	public static void main(String args[]) {
		readFile();
		numberOfInversionQuadratic();
	}
}

import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Knapsack {

	int value[];
	int size[];
	int totalCapacity;

	static HashMap<String, Integer> map = new HashMap<>();

	static int temp[][];

	public int doKnapsackRecursive(int capacity, int n) {
		if (n <= 0 || capacity <= 0) {
			return 0;
		}

		if (size[n-1] > capacity) {
			return doKnapsackRecursive(capacity, n-1);
		}

		return Math.max(value[n-1] + doKnapsackRecursive(capacity - size[n-1], n-1), doKnapsackRecursive(capacity, n-1));

	}

	public int doKnapsackRecursiveSmart(int capacity, int n) {
		//System.out.println("in here bazinga");
		if (n <= 0 || capacity <= 0) {
			return 0;
		}

		String str = capacity+"c:n"+n;
		if (map.containsKey(str)) {
			//System.out.println("in here bazinga 2222222");
			//System.out.println(map.get(str));
			return map.get(str);
		}

		if (size[n-1] > capacity) {
			int k = doKnapsackRecursiveSmart(capacity, n-1);
			//String str1 = capacity+"c:n"+n;
			map.put(str,k); 
			return k; 
		}

		int j = Math.max(value[n-1] + doKnapsackRecursiveSmart(capacity - size[n-1], n-1), doKnapsackRecursiveSmart(capacity, n-1)); 
		//String str2 = capacity+"c:n"+n;
		map.put(str,j);
		return j;

	}

	public int doKnapsackRecursiveSmart1(int capacity, int n) {
		if (n <= 0 || capacity <= 0) {
			temp[n][capacity] = 0;
			return temp[n][capacity];
		}

		//String str = capacity+":"+n;
		if (temp[n][capacity] != 0) {
			return temp[n][capacity];
		}

		if (size[n-1] > capacity) {
			temp[n][capacity] = doKnapsackRecursive(capacity, n-1);
			return temp[n][capacity]; 
		}

		temp[n][capacity] = Math.max(value[n-1] + doKnapsackRecursive(capacity - size[n-1], n-1), doKnapsackRecursive(capacity, n-1)); 
		 
		
		return temp[n][capacity];

	}

	public int knapsackIterative() {
		int sizeOfArray = value.length;
		int arr[][] = new int[sizeOfArray+1][totalCapacity+1];

		int i = 0;
		int j = 0;
		for (i = 0; i <= sizeOfArray; i++) {
			for (j = 0; j <= totalCapacity; j++) {
				if (i == 0 || j == 0) {
					arr[i][j] = 0;
				} else {
					if (size[i-1] > j) {
						arr[i][j] = arr[i-1][j];
					} else {
						arr[i][j] = Math.max(value[i-1] + arr[i-1][j-size[i-1]], arr[i-1][j]);
					}
				}
			}
		}

		return arr[sizeOfArray][totalCapacity];
	}


	public void smartDP() {

	}

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/dynamic-programming/knapsack.txt";
		List<String> list = new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		String origin = "000000000000000000000000";
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		int i = 0;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {

					String arr[] = str.split(" ");
					value[i] = Integer.parseInt(arr[0]);
					size[i] = Integer.parseInt(arr[1]);
					i++;



				} else {
					flag = 1;
					String arr1[] = str.split(" ");
					value = new int[Integer.parseInt(arr1[1])];
					size = new int[Integer.parseInt(arr1[1])];
					totalCapacity = Integer.parseInt(arr1[0]);

				}
				
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		System.out.println("DONE");

	}
	public static void main(String[] args) {
		//int arr[] = new int[Integer.MAX_VALUE/3];

		//System.out.println(1+":" +2);
		Knapsack knapsack = new Knapsack();
		knapsack.readFile();
		//knapsack.temp = new int[knapsack.value.length+1][knapsack.totalCapacity+1];
		//System.out.println(knapsack.temp[0][0]);
		//System.out.println(knapsack.knapsackIterative());
		System.out.println(knapsack.doKnapsackRecursiveSmart(knapsack.totalCapacity, knapsack.value.length));
		

	}
}
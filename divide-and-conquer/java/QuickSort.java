import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class QuickSort {

	int arr[];

	public static int totalComp = 0;

	public void doQuickSort(int low, int high) {
		if (low >= high) {
			return;
		}
		

		//int pivot = choosePivot(arr, n);
		int indexOfPivot = partitionAsMedianOfThree(low, high);
		doQuickSort(low,indexOfPivot);
		doQuickSort(indexOfPivot+1,high);


	}

	public int partitionAsFirstElement(int low, int high) {
		totalComp += (high - low -1);
		int pivot = arr[low];
		int i = low + 1;
		for (int j = low+1; j < high; j++) {
			if (arr[j] < pivot) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
			}
		}
		int temp1 = arr[low];
		arr[low] = arr[i-1];
		arr[i-1] = temp1;

		// for (int i1 = 0; i1 < arr.length; i1++) {
		// 	System.out.print(arr[i1] + " ");
		// }
		// System.out.println();

		// System.out.println("------------------------------------------------------------------");
		return i-1;

	}

	public int partitionAsLastElement(int low, int high) {

		totalComp += (high - low -1);

		int temp2 = arr[high-1];
		arr[high-1] = arr[low];
		arr[low] = temp2;
		int pivot = arr[low];
		int i = low + 1;
		for (int j = low+1; j < high; j++) {
			if (arr[j] < pivot) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
			}
		}
		int temp1 = arr[low];
		arr[low] = arr[i-1];
		arr[i-1] = temp1;

		return i-1;

	}

	public int median(int low, int high) {
		int num= (high+low)/2;
		int mid =-1;

		if ((high-low) % 2 == 0) {
			mid = num-1;
		} else {
			mid = num;
		}
		int p = -1;
		if ((arr[low] >= arr[mid] && arr[low] <= arr[high-1]) || (arr[low] >= arr[high-1] && arr[low] <= arr[mid])) {
			p = low;
		} else if ((arr[mid] > arr[low] && arr[mid] <= arr[high-1]) || (arr[mid] >= arr[high-1] && arr[mid] < arr[low])) {
			p = mid;
		} else {
			p = high-1;
		}

		return p;
	}

	public int partitionAsMedianOfThree(int low, int high) {
		totalComp += (high - low -1);



		int p = median(low, high);
		
		if (p != low) {
			int temp2 = arr[p];
			arr[p] = arr[low];
			arr[low] = temp2;	
		}

		int pivot = arr[low];
		int i = low + 1;
		for (int j = low+1; j < high; j++) {
			if (arr[j] < pivot) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
			}
		}
		int temp1 = arr[low];
		arr[low] = arr[i-1];
		arr[i-1] = temp1;

		return i-1;

	}

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/divide-and-conquer/quicksort.txt";
		List<Integer> list = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(Integer.parseInt(s)));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		arr = new int[list.size()];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}

		System.out.println("DONE");

	}

	public static void main(String[] args) {
		QuickSort quickSort = new QuickSort();
		quickSort.readFile();
		// for (int i = 0; i < quickSort.arr.length; i++) {
		// 	//System.out.print(quickSort.arr[i] + " ");
		// }
		quickSort.doQuickSort(0, quickSort.arr.length);
		for (int i = 0; i < quickSort.arr.length; i++) {
			System.out.println(quickSort.arr[i]);
		}
		System.out.println("Total comparison = " + totalComp);
		// int i = 3;
		// int j = 2;
		// int k = i/j;
		// System.out.println(k);

		// System.out.println("---------------------------------------");
		// System.out.print(quickSort.arr.length + " " + quickSort.arr[0] + " " + quickSort.arr[quickSort.arr.length-1] + " " + quickSort.median(0,quickSort.arr.length) + " aaa");


		
	}
}
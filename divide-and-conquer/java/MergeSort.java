class MergeSort {

	int input[];
	int auxillary[];

	MergeSort(int arr[]) {
		input = arr;
		auxillary = new int[arr.length];
	}

	public void doMergeSort(int low, int high) {

		if (low < high) {
			int mid = low + ((high - low) / 2);
			doMergeSort(low, mid);
			doMergeSort(mid+1, high);
			merge(low, mid, high);
		}

	}

	public void merge(int low, int mid, int high) {
		int lowerLow = low;
		int lowerHigh = mid;
		int upperLow = mid + 1;
		int upperHigh = high;

		int k = 0;
		int numOfelements = high - low + 1;

		while (lowerLow <= lowerHigh && upperLow <= upperHigh) {
			if (input[lowerLow] < input[upperLow]) {
				auxillary[k++] = input[lowerLow++];
			} else {
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

	}

	public static void main(String[] args) {
		int arr[] = {9,2,3,78, 90, 122, 1, 34, 23};
		MergeSort ms = new MergeSort(arr);
		ms.doMergeSort(0, arr.length-1);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(ms.input[i] + " ");
		}
		System.out.println();


	}
}

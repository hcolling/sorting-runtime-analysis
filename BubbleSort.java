public class BubbleSort {
	public void sort(int[] array) {
		boolean swapped;

		do {
			swapped = false;
			for (int i = 0; i < array.length - 1; i++) {
				if (array[i] > array[i + 1]) {
					Helpers.swap(array, i, i + 1);
					swapped = true;
				}
			}
		} while (swapped);
	}
}

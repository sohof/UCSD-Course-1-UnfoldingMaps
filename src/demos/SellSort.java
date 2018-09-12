package demos;

public class SellSort {

	
	public static <T> void mSort(Comparable<T>[] array) {
		
	for (int i=0; i< array.length-1; i++) {
		
		int minIndex = i;
		
		for(int j = i+1; j < array.length; j++) {
			
			if (array[j].compareTo((T) array[minIndex]) < 0) {
				minIndex =j;
			}
		}
		swap(array, i, minIndex);

			
	}
	
	}
	
	private static <T> void swap(Comparable<T>[] array, int i, int j) {
		
		Comparable<T> tmp = array[i];
		
		array[i] = array[j];
		array[j] = tmp;
		
	}
	
	
}

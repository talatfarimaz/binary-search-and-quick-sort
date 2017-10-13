

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




public class Main {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		while (true) {
			try {
				Scanner scanner = new Scanner(new File(args[0]));
				ArrayList<String> commands = new ArrayList<String>();

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					commands.add(line);

				}

				System.out.println("enter count : ");
				int count = keyboard.nextInt();
				System.out.println("enter syllable : ");
				String syllable = keyboard.next();
				if(syllable == null)
					throw new NullPointerException("Syllable is null!!!");
				if(count<0)
					throw new IllegalArgumentException("Count is negative!!!");

				String commandsOfArray[] = commands.toArray(new String[commands.size()]);

				String[][] tokenizerArray = new String[commands.size()][2];

				String[] array2 = new String[commands.size()];

				// Delete gaps on the right
				for (int i = 0; i < commands.size(); i++)
					array2[i] = commandsOfArray[i].trim();

				// Add to two dim. array weight and string values.
				for (int i = 0; i < commands.size(); i++)
					tokenizerArray[i] = array2[i].split("\t");

				// This array has only strings.
				String[] strings = new String[commands.size() - 1];
				strings = returnStringArray(tokenizerArray);

				// The strings were sorted.
				callQuick(strings);

				int result = binarySearch(strings, syllable);

				// Syllable preamble strings were taken.
				String array4[] = allSyllable(strings, result, syllable);

				// Weight and strings were taken.
				String array5[][] = twoDimSorted(tokenizerArray, array4);

				int array6[] = new int[array5.length];
				// type casting
				for (int k = 0; k < array5.length; k++)
					array6[k] = Integer.parseInt(array5[k][0]);

				quickSort2(array6, 0, array6.length - 1);

				// Weight and strings were used on two dim. array.
				String finalArray[][] = sortedWeightAndStr(array6, array5);
				String finalArray2[][] = new String[finalArray.length][2];
				
				//Large to small order
				for (int i = 0; i < finalArray.length; i++) {
					finalArray2[i][0] = finalArray[finalArray.length - i - 1][0];
					finalArray2[i][1] = finalArray[finalArray.length - i - 1][1];
				}
				
				//print all sorted values.
				if (count <= finalArray2.length) {
					for (int i = 0; i < count; i++)
						System.out.println(finalArray2[i][0] + " " + finalArray2[i][1]);
				} else
					for (int i = 0; i < finalArray2.length; i++)
						System.out.println(finalArray2[i][0] + " " + finalArray2[i][1]);

				scanner.close();
			} catch (FileNotFoundException ex) {
				System.out.println("No File Found!");
				return;
			}
		}

	}

	public static String[] returnStringArray(String array[][]) {
		String[] returnArray = new String[array.length - 1];
		for (int i = 0; i < array.length - 1; i++)
			returnArray[i] = array[i + 1][1];
		return returnArray;

	}

	public static void callQuick(String[] array) {
		quicksort(array, 0, array.length - 1);
	}

	public static void quicksort(String[] array, int left, int right) {
		if (left < right) {
			int temp = partition(array, left, right);
			if (temp == right) {
				temp--;
			}
			quicksort(array, left, temp);
			quicksort(array, temp + 1, right);
		}
	}

	public static int partition(String[] array, int left, int right) {
		String pivot = array[left];
		int low = left;
		int high = right;

		while (true) {
			while (array[high].compareTo(pivot) >= 0 && low < high) {
				high--;
			}
			while (array[low].compareTo(pivot) < 0 && low < high) {
				low++;
			}
			if (low < high) {
				String temp = array[low];
				array[low] = array[high];
				array[high] = temp;
			} else
				return high;
		}
	}

	public static int binarySearch(String[] a, String x) {
		int low = 0;
		int high = a.length - 1;
		int middle;

		while (low <= high) {
			middle = (low + high) / 2;
			if (a[middle].startsWith(x)) {
				return middle;
			} else if (a[middle].compareTo(x) > 0) {
				high = middle - 1;
			} else if (a[middle].compareTo(x) < 0) {
				low = middle + 1;
			}

		}

		return -1;
	}

	public static String[] allSyllable(String array[], int count, String deneme) {
		ArrayList<String> allSyllArr = new ArrayList<String>();
		int temp1 = count;
		int temp2 = count;

		while (array[temp1].startsWith(deneme)) {
			allSyllArr.add(array[temp1]);
			temp1--;
		}

		while (array[temp2 + 1].startsWith(deneme)) {
			allSyllArr.add(array[temp2 + 1]);
			temp2++;
		}

		String allSyllables[] = allSyllArr.toArray(new String[allSyllArr.size()]);
		return allSyllables;

	}

	public static String[][] twoDimSorted(String array1[][], String array2[]) {
		String[][] weightsAndString = new String[array2.length][2];
		for (int i = 1; i < array1.length; i++) {
			for (int j = 0; j < array2.length; j++) {
				if (array1[i][1] == array2[j]) {
					weightsAndString[j][0] = array1[i][0];
					weightsAndString[j][1] = array1[i][1];
				}
			}
		}

		return weightsAndString;

	}

	public static void quickSort2(int[] array6, int low, int high) {

		if (array6 == null || array6.length == 0) {
			return;
		}

		if (low >= high) {
			return;
		}

		int middle = low + (high - low) / 2;
		int pivot = array6[middle];

		int i = low, j = high;
		while (i <= j) {
			while (array6[i] < pivot) {
				i++;
			}
			while (array6[j] > pivot) {
				j--;
			}

			if (i <= j) {
				swap(array6, i, j);
				i++;
				j--;
			}
		}
		if (low < j) {
			quickSort2(array6, low, j);
		}
		if (high > i) {
			quickSort2(array6, i, high);
		}
	}

	public static void swap(int[] array6, int x, int y) {
		int temp = array6[x];
		array6[x] = array6[y];
		array6[y] = temp;
	}

	public static String[][] sortedWeightAndStr(int array[], String array2[][]) {
		String sorted[][] = new String[array.length][2];

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++)
				if (array[j] == Integer.parseInt(array2[i][0])) {
					sorted[j][0] = array2[i][0];
					sorted[j][1] = array2[i][1];
				}
		}

		return sorted;
	}
}

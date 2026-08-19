
/*******************************************************************************
 * Developed by: Gabriel Pires de Farias & Henrique Colling
 * Semester: 2026/2
 *
 * Discipline: Analysis and Project of Algorithms
 ******************************************************************************/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

	public enum TimeMeasurement {
		ALGORITHM1,
		ALGORITHM2
	}

	public enum ArraySize {
		SIZE_1K(1_000),
		SIZE_5K(5_000),
		SIZE_10K(10_000),
		SIZE_50K(50_000),
		SIZE_100K(100_000),
		SIZE_200K(200_000),
		SIZE_300K(300_000),
		SIZE_400K(400_000),
		SIZE_500K(500_000),
		SIZE_1M(1_000_000);

		private final int size;

		ArraySize(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}
	}

	public enum MeasurementType {
		RANDOM,
		ORDERED
	}

	public static int[] randArray(int size, int min, int max) {
		int[] nums = new int[size];
		Random rand = new Random();

		for (int i = 0; i < nums.length; i++) {
			nums[i] = rand.nextInt(max - min + 1) + min;
		}

		return nums;
	}

	public static String getAlgorithmName(TimeMeasurement algorithm) {
		switch (algorithm) {
			case ALGORITHM1:
				return "BubbleSort";
			case ALGORITHM2:
				return "InsertionSort";
			default:
				throw new IllegalArgumentException("Algorithm not found.");
		}
	}

	public static void runExperiment(TimeMeasurement algorithmType,
									 ArraySize arraySize) throws IOException {
		int size = arraySize.getSize();
		int[] arr = randArray(size, 0, 10_500);
		double randomTime;
		double orderedTime;

		if (algorithmType == TimeMeasurement.ALGORITHM1) {
			BubbleSort algorithm = new BubbleSort();

			long startRandom = System.nanoTime();
			algorithm.sort(arr);
			long endRandom = System.nanoTime();
			randomTime = (endRandom - startRandom) / 1_000_000.0;

			long startOrdered = System.nanoTime();
			algorithm.sort(arr);
			long endOrdered = System.nanoTime();
			orderedTime = (endOrdered - startOrdered) / 1_000_000.0;
		}
		else {
			InsertionSort algorithm = new InsertionSort();

			long startRandom = System.nanoTime();
			algorithm.sort(arr);
			long endRandom = System.nanoTime();
			randomTime = (endRandom - startRandom) / 1_000_000.0;

			long startOrdered = System.nanoTime();
			algorithm.sort(arr);
			long endOrdered = System.nanoTime();
			orderedTime = (endOrdered - startOrdered) / 1_000_000.0;
		}

		String algorithmName = getAlgorithmName(algorithmType);
		String fileName = algorithmName + "_" + size + ".csv";
		File outputDirectory = new File("results");

		if (!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}

		File csvFile = new File(outputDirectory, fileName);

		try (PrintWriter writer = new PrintWriter(
			new FileWriter(csvFile))) {
			writer.println("Random time (ms),Ordered time (ms)");
			writer.printf("%.4f,%.4f%n", randomTime, orderedTime);
		}

		System.out.println("Generated file: " + csvFile.getPath());
	}

	public static void main(String[] args) {
		try {
			runExperiment(TimeMeasurement.ALGORITHM1, ArraySize.SIZE_1K);
		} catch (IOException e) {
			System.err.println("Error generating CSV: " + e.getMessage());
		}
	}
}

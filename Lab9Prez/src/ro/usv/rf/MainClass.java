package ro.usv.rf;

import ro.usv.rf.DistanceUtils;
import ro.usv.rf.FileUtils;
import ro.usv.rf.USVInputFileCustomException;

public class MainClass {

	public static void main(String[] args) {
		String[][] learningSet;
		try {
			learningSet = FileUtils.readLearningSetFromFile("in.txt");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length - 1;

			System.out.println(String.format("The learning set has %s patters and %s features", numberOfPatterns,
					numberOfFeatures));

			double[][] distance_array = new double[learningSet.length][learningSet.length];

			for (int j = 0; j < learningSet.length; j++) {
				for (int i = 0; i < learningSet[0].length; i++) {
					System.out.print(learningSet[j][i] + " ");
				}
				
				System.out.println();
			}

			for (int j = 0; j < learningSet.length; j++) {
				for (int i = 0; i < learningSet.length; i++) {
					if (i == j) {
						distance_array[j][i] = 0;
					} else {
						double distance = DistanceUtils.getEuclidianGeneralizedDistance(
								learningSet[j], learningSet[i]);
						
						distance_array[j][i] = distance;
						distance_array[i][j] = distance;
					}
				}
			}

			DistanceUtils.dynamicKernels(2, learningSet, distance_array);

		} catch (USVInputFileCustomException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}

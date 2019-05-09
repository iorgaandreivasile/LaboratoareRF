package ro.usv.rf;

import java.util.Arrays;

public class DistanceUtils {

	public static double getEuclidianGeneralizedDistance(String[] learningSet1, String[] learningSet2) {
		double result = 0;
		result = Math.sqrt(Math.pow((Double.valueOf(learningSet1[0]) - Double.valueOf(learningSet2[0])), 2)
				+ Math.pow((Double.valueOf(learningSet1[1]) - Double.valueOf(learningSet2[1])), 2));
		return result;
	}

	public static double getEuclidianDistance(double x, double y, String[] learningSet2) {
		double result = 0;
		result = Math.sqrt(
				Math.pow(x - Double.valueOf(learningSet2[0]), 2) + Math.pow(y - Double.valueOf(learningSet2[1]), 2));
		return result;
	}

	public static double getEuclidianGeneralizedDistance(double[] coords, String[] learningSet2) {
		double result = 0;
		for (int i = 0; i < coords.length; i++) {
			result += Math.pow(coords[i] - Double.valueOf(learningSet2[i]), 2);
		}
		result = Math.sqrt(result);
		return result;
	}

	public static void dynamicKernels(int M, String[][] learningSet, double[][] distances) {
		// Initialize iClass array
		int[] iClass = new int[learningSet.length];
		int[] lastIClass = new int[learningSet.length];
		Arrays.fill(iClass, 0);
		
		int[] kernels = new int[M];
		int noOfElements = learningSet.length;
		int[] counteClass = new int[M]; // Counter of elements for each class

		// Initialize kernels
		for (int i = 0; i < M; i++) {
			kernels[i] = i;
		}

		boolean notDone = true;
		// Fist step
		while (notDone) {
			for (int i = 0; i < noOfElements; i++) {
				double min = Double.MAX_VALUE;
				
				for (int j = 0; j < kernels.length; j++) // distance from each kernel
				{
					if (min > distances[i][kernels[j]]) {
						min = distances[i][kernels[j]];
						iClass[i] = j; // The classes will start from 0
					}
				}
			}

			// Reinitialize counter
			for (int i = 0; i < M; i++) {
				counteClass[i] = 0;
			}
			
			// Set number of elements per class
			for (int i = 0; i < iClass.length; i++) {
				counteClass[iClass[i]]++;
			}

			for (int i = 0; i < M; i++) {
				// Center of gravity coordinates
				double x = 0; 
				double y = 0;
				double[] coords = new double[learningSet[0].length]; 
				
				Arrays.fill(coords, 0);

				// Generalizing for multiple features
				for (int j = 0; j < iClass.length; j++) {
					if (iClass[j] == i) {
						for (int k = 0; k < learningSet[0].length; k++) {
							coords[k] += (Double.valueOf(learningSet[j][k]) * 1.0) / counteClass[i];
						}
					}
				}
				
				/*
				 * We now got the coordinates of center of the class i in x 
				 * and y -> get the closest element to the center.
				 */
				int closestElement = 0;
				
				for (int j = 0; j < noOfElements; j++) {
					double min = Double.MAX_VALUE;
					
					if (iClass[j] == i) {
						double distance = getEuclidianDistance(x, y, learningSet[j]);
						if (min > distance) {
							min = distance;
							closestElement = j;
						}
					}
				}
				
				kernels[i] = closestElement;
			}
			
			// Check the new iClass with the old iClass
			notDone = false;
			for (int i = 0; i < iClass.length; i++) {
				if (iClass[i] != lastIClass[i]) {
					notDone = true;
				}
			}

			for (int i = 0; i < iClass.length; i++) {
				lastIClass[i] = iClass[i];
			}
		}

		System.out.print("\nResult: ");
		for (int i = 0; i < iClass.length; i++) {
			System.out.print(iClass[i] + " ");
		}
		
		System.out.println();
	}

	/**
	 * Get generalized Euclidean distance for two patterns.
	 * 
	 * @param learninSet1
	 * @param learninSet2
	 * @return the distance
	 */
	public static double getGeneralizedEuclidianDistancefromDouble(double[] learninSet1, String[] learninSet2) {
		double result = 0;
		for (int i = 0; i < learninSet1.length; i++) {
			result += Math.pow(Double.valueOf(learninSet1[i]) - Double.valueOf(learninSet2[i]), 2);
		}
		
		result = Math.sqrt(result);
		
		return result;
	}

}
package ro.usv.rf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DistanceUtils {

	static public double getEuclidianGeneralizedDistance(String[] learningSet1, String[] learningSet2) {
		double result = 0;
		result = Math.sqrt(Math.pow((Double.valueOf(learningSet1[0]) - Double.valueOf(learningSet2[0])), 2)
				+ Math.pow((Double.valueOf(learningSet1[1]) - Double.valueOf(learningSet2[1])), 2));

		return result;
	}

	static public String getNNClass(int k, double[] distances, String[][] learningSet) {
		ArrayList<Neighbour> Neighbours = new ArrayList<Neighbour>();

		for (int i = 0; i < learningSet.length; i++) {
			Neighbours.add(new Neighbour(i, distances[i], learningSet[i][learningSet[0].length - 1]));
		}

		Collections.sort(Neighbours, (neighbour1, neighbour2) -> {
			if (neighbour1.getValue() < neighbour2.getValue())
				return -1;
			else if (neighbour1.getValue() >= neighbour2.getValue())
				return 1;
			else
				return 0;
		});

		HashMap<String, Integer> occurrencesMap = new HashMap<String, Integer>();

		int max = 0;
		String className = null;

		// Iterate patterns and store the number of occurrences for each class
		for (int i = 0; i < k && i < Neighbours.size(); i++) {
			String currentClass = Neighbours.get(i).getClass_num();
			Integer currentValue = occurrencesMap.get(currentClass);

			if (currentValue != null) {
				occurrencesMap.put(currentClass, currentValue + 1);
			} else {
				occurrencesMap.put(currentClass, 1);
			}

			currentValue = occurrencesMap.get(currentClass);

			if (max < currentValue) {
				max = currentValue;
				className = currentClass;
			}
		}

		return className;
	}

}
package ro.usv.rf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainClass {

	private static final int[] kValues = { 1, 3, 5, 7, 9, 11, 13, 15 };

	public static void main(String[] args) {
		String[][] learningSet;

		try {
			// Read entry data from file
			learningSet = FileUtils.readLearningSetFromFile("iris.csv");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length - 1;

			System.out.println(String.format("The learning set has %s patters " + "and %s features", numberOfPatterns,
					numberOfFeatures));

			Map<String, Integer> classesMap = new HashMap<String, Integer>();
			//String is the key and has type Integer 
			//HashMap necessary
			// Create map with number of occurrences for each class
			for (int i = 0; i < numberOfPatterns; i++) {
				String clazz = learningSet[i][learningSet[i].length - 1]; //matrix
				if (classesMap.containsKey(clazz)) { //if map contains the name of class if not 
					Integer nrOfClassPatterns = classesMap.get(clazz);
					classesMap.put(clazz, nrOfClassPatterns + 1); //find another class(setosa ...
				} else {
					classesMap.put(clazz, 1);
				}
			}

			Random random = new Random();

			// Evaluation set
			Map<String, List<Integer>> classesEvaluationPatterns = new HashMap<String, List<Integer>>();
			Integer evaluationSetSize = 0; //create another map this time value is List integer

			// Map random patterns, for evaluation set.
			for (Map.Entry<String, Integer> entry : classesMap.entrySet()) {
				String className = entry.getKey();
				Integer classMembers = entry.getValue();

				// Evaluation set has only 15% of patterns
				Integer evaluationPatternsNr = Math.round(classMembers * 15 / 100);

				evaluationSetSize += evaluationPatternsNr;

				List<Integer> selectedPatternsForEvaluation = new ArrayList<Integer>();

				// Generate random evaluation values
				for (int i = 0; i < evaluationPatternsNr; i++) {
					Integer patternNr = random.nextInt(classMembers) + 1;

					// No duplicates allowed -> if duplicate, keep generating another until unique one 
				
					while (selectedPatternsForEvaluation.contains(patternNr)) {
						patternNr = random.nextInt(classMembers) + 1;
					}

					selectedPatternsForEvaluation.add(patternNr);
				}

				classesEvaluationPatterns.put(className, selectedPatternsForEvaluation);
			}

			String[][] evaluationSet = new String[evaluationSetSize][numberOfPatterns];
			String[][] trainingSet = new String[numberOfPatterns - evaluationSetSize][numberOfPatterns];

			int evaluationSetIndex = 0;
			int trainingSetIndex = 0;

			Map<String, Integer> classCurrentIndex = new HashMap<String, Integer>();

			// Generate training set
			for (int i = 0; i < numberOfPatterns; i++) {
				String className = learningSet[i][numberOfFeatures];

				if (classCurrentIndex.containsKey(className)) {
					int currentIndex = classCurrentIndex.get(className);
					classCurrentIndex.put(className, currentIndex + 1);
				} else {
					classCurrentIndex.put(className, 1);
				}

				if (classesEvaluationPatterns.get(className).contains(classCurrentIndex.get(className))) {
					evaluationSet[evaluationSetIndex] = learningSet[i];
					evaluationSetIndex++;
				} else {
					trainingSet[trainingSetIndex] = learningSet[i];
					trainingSetIndex++;
				}
			}

			// Write output to files
			FileUtils.writeLearningSetToFile("eval.txt", evaluationSet);
			FileUtils.writeLearningSetToFile("train.txt", trainingSet);

			double[][] distanceArray = new double[evaluationSet.length][trainingSet.length];

			for (int i = 0; i < trainingSet.length; i++) {
				for (int j = 0; j < evaluationSet.length; j++) {
					distanceArray[j][i] = DistanceUtils.getEuclidianGeneralizedDistance(evaluationSet[j],
							trainingSet[i]);
				}
			}

			String outputClass = null;

			/**
			 * Using study and evaluation sets we study the error for K =
			 * (1,3,5,7,9,11,13,15)
			 */
			for (int j = 0; j < evaluationSet.length; j++) {
				System.out.println();
				for (int i = 0; i < kValues.length; i++) {
					outputClass = DistanceUtils.getNNClass(kValues[i], distanceArray[j], trainingSet);

					System.out.println("k= " + kValues[i] + " - " + j + " has class: "
							+ evaluationSet[j][numberOfFeatures] + " : calculated class: " + outputClass + " - "
							+ (outputClass.compareTo(evaluationSet[j][numberOfFeatures]) == 0 ? "corect" : "incorect"));
				}
			}
		} catch (USVInputFileCustomException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}
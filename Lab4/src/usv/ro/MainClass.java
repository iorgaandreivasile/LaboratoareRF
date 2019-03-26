package usv.ro;


public class MainClass {

	
	
	public static void main(String[] args) {
		double[][] learningSet;
		double[][] euclidianSet=new double[5][5];
		try {
			learningSet = FileUtils.readLearningSetFromFile("in.txt");
			int numberOfPatterns = learningSet.length;
			int numberOfFeatures = learningSet[0].length;
			System.out.println(String.format("The learning set has %s patters and %s features", numberOfPatterns, numberOfFeatures));
			for(int i=0;i<learningSet.length;i++)
			{
			double euclidianDistance=DistanceUtils.calculateDistanceEuclidian(learningSet[0], learningSet[i]);
			System.out.println("Euclidia distance "+euclidianDistance);
			}
			for(int i=0;i<numberOfPatterns;i++)
			{
				for(int j=i+1;j<numberOfPatterns;j++)
				{
					    euclidianSet[i][j]=DistanceUtils.calculateDistanceEuclidian(learningSet[i], learningSet[j]);
			
					    euclidianSet[j][i] = euclidianSet[i][j];
				}
			}
			for(int i=0;i<5;i++)
			{
				System.out.println("");
				for(int j=0;j<5;j++) {
					System.out.print(euclidianSet[i][j]);
					System.out.print("  ");
				}
			}
			int searchedPatternIndex = learningSet.length-1;
			double[] distances = euclidianSet[searchedPatternIndex];
			int closestPatternIndex = 0;
			double closestePatternDistance = distances[closestPatternIndex];
			for (int i=1; i<distances.length; i++)
			{
				if (distances[i] < closestePatternDistance && searchedPatternIndex != i)
				{
					closestePatternDistance = distances[i];
					closestPatternIndex = i; 
				}
			}
			
			int classColumnIndex = learningSet[closestPatternIndex].length-1;
			System.out.println(String.format("Searched class is: %s", learningSet[closestPatternIndex][classColumnIndex]));
			
			
		} finally {
			System.out.println("Finished learning set operations");
		}
	}

}


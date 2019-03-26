package usv.ro;

public class DistanceUtils {

	public static double calculateDistanceEuclidian(double[] x, double[] y)
	{
	   double distance = 0.0;
	   for (int i = 0; i<x.length-1; i++)
	   {
		   distance += Math.pow(x[i]-y[i], 2);
	   }
	   return Math.floor(Math.sqrt(distance)*100)/100;
			   
	}

	public static double calculateDistanceCeb(double[] x, double[] y)
	{
	   double distance = 0.0;
	   for (int i = 0; i<x.length; i++)
	   {
		   double newDistance = Math.abs(x[i]-y[i]);
		   if (newDistance > distance)
		   {
			   distance = newDistance;
		   }
		  
	   }
	   return distance;		   
	}
	public static double calculateDistanceCityB(double[] x, double[] y)
	{
	   double distance = 0.0;
	   for (int i = 0; i<2; i++)
	   {
		   distance += x[i]-y[i];
	   }
	   return distance;
			   
	}
	public static double calculateDistanceMahalanobis(double[] x, double[] y, int nrOfPatterns)
	{
	   double distance = 0.0;
	   for (int i = 0; i<2; i++)
	   {
		   distance += Math.pow(x[i]-y[i], nrOfPatterns);
	   }
	   return Math.pow(distance,1/nrOfPatterns);
			   
	}
}


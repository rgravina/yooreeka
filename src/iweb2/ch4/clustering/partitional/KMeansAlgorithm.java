package iweb2.ch4.clustering.partitional;

import iweb2.ch4.model.Cluster;
import iweb2.ch4.model.DataPoint;
import iweb2.ch4.utils.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class KMeansAlgorithm {

	private int k;
	private DataPoint[] allCentroids;
	private Cluster[] allClusters;
	private DataPoint[] allDataPoints;

	/**
	 * 
	 * @param k -
	 *            desired number of clusters.
	 * 
	 */
	public KMeansAlgorithm(int k, DataPoint[] dataPoints) {
		DataPoint[] initialCentroids = KMeansAlgorithm.pickInitialCentroids(k, dataPoints);
		init(initialCentroids, dataPoints);
	}

	/**
	 * @param initialCentroids -
	 *            starting values for the centroids of each cluster.
	 */
	public KMeansAlgorithm(DataPoint[] initialCentroids, DataPoint[] dataPoints) {
		init(initialCentroids, dataPoints);
	}

	private void init(DataPoint[] initialCentroids, DataPoint[] dataPoints) {
		this.k = initialCentroids.length;
		this.allDataPoints = dataPoints;
		this.allCentroids = initialCentroids;
		this.allClusters = new Cluster[k];
	}

	public void cluster() {

		boolean centroidsChanged = true;

		while (centroidsChanged == true) {
			// Create a set points for each cluster
			List<Set<DataPoint>> clusters = new ArrayList<Set<DataPoint>>(k);
			for (int i = 0; i < k; i++) {
				clusters.add(new HashSet<DataPoint>());
			}

			// Assign points to each set based on minimum distance from the centroids
			for (DataPoint p : allDataPoints) {
				int i = findClosestCentroid(allCentroids, p);
				clusters.get(i).add(p);
			}

			for (int i = 0; i < k; i++) {
				allClusters[i] = new Cluster(clusters.get(i));
			}

			// Calculate new cluster centroids, and 
			// check if any of the centroids has changed
			centroidsChanged = false;
			for (int i = 0; i < allClusters.length; i++) {
				if (clusters.get(i).size() > 0) {
					double[] newCentroidValues = findCentroid(allClusters[i]);
					double[] oldCentroidValues = allCentroids[i].getNumericAttrValues();
					if (!Arrays.equals(oldCentroidValues, newCentroidValues)) {
						allCentroids[i] = new DataPoint(allCentroids[i].getLabel(), newCentroidValues);
						centroidsChanged = true;
					}
				} else {
					// keep mean unchanged if cluster has no elements.
				}
			}
		}
	}

	public DataPoint[] getAllCentroids() {
		return this.allCentroids;
	}

	public Cluster[] getAllClusters() {
		return this.allClusters;
	}

	public int getK() {
		return this.k;
	}

	public static DataPoint[] pickInitialCentroids(int k, DataPoint[] data) {

		Random randGen = new Random();
		DataPoint[] centroids = new DataPoint[k];

		// Calculate random mean values for each cluster based on the data
		/**
		 * TODO: 4.2 -- Selecting the means for seeding
		 * 
		 * In large datasets, the selection of the initial centroids can be
		 * important from a computational (time) complexity perspective. 
		 * 
		 * In general, how can we improve the seeding of the initial mean values?
		 * For example, consider the following heuristic:
		 * 
		 *   1. pick randomly one node 
		 *   2. calculate the distance between that node and O (10*k) other nodes 
		 *   3. sort the list of nodes according to their distance from the first node 
		 *   4. pick every 10th node in the sequence
		 *   5. calculate the mean distance between each one of these nodes and the original node
		 * 
		 * This algorithmic choice is as ad hoc as they come, however, it does have
		 * some key principles embedded in it? What are these principles? 
		 * How can you generalize this algorithm? 
		 * 
		 * Discuss advantages/disadvantages of the initial seeding with your friends.
		 * 
		 */
		Set<Integer> previouslyUsedIds = new HashSet<Integer>();
		for (int i = 0; i < k; i++) {
		    // pick point index that we haven't used yet
		    int centroidId;
		    do {
		        centroidId = randGen.nextInt(data.length); 
		    }
		    while( previouslyUsedIds.add(centroidId) == false );
		    
		    // Create DataPoint that will represent the cluster's centroid. 
		    String label = "Mean-" + i + "(" + data[centroidId].getLabel() + ")"; 
		    double[] values = data[centroidId].getNumericAttrValues();
		    String[] attrNames = data[centroidId].getAttributeNames();
			centroids[i] = new DataPoint(label, 
			        Attributes.createAttributes(attrNames, values));
		}

		return centroids;
	}

	/**
	 * This method calculates the closest centroid for a given data point
	 * 
	 * @param centroids
	 * @param x is the <CODE>DataPoint</CODE> for which we seek the closest centroid
	 * @return the index (from the centroids array) of the closest centroid
	 */
	private int findClosestCentroid(DataPoint[] centroids, DataPoint x) {
		double minDistance = Double.POSITIVE_INFINITY;
		int closestCentroid = -1;
		for (int i = 0, n = centroids.length; i < n; i++) {
			double d = distance(centroids[i], x);
			// if the d == minDistance then keep current selection
			if (d < minDistance) {
				minDistance = d;
				closestCentroid = i;
			}

		}
		return closestCentroid;
	}

	private double distance(DataPoint x, DataPoint y) {
		return distance(x.getNumericAttrValues(), y.getNumericAttrValues());
	}

	private double distance(double[] x, double[] y) {
		double sumXY2 = 0.0;
		for (int i = 0, n = x.length; i < n; i++) {
			sumXY2 += Math.pow(x[i] - y[i], 2);
		}
		return Math.sqrt(sumXY2);
	}

	private double[] findCentroid(Cluster c) {

		Set<DataPoint> clusterPoints = c.getElements();
		int n = clusterPoints.size();

		if (n == 0) {
			return new double[0];
		}

		int d = c.getDimensionCount();
		double[] meanAttributes = new double[d];

		for (DataPoint p : clusterPoints) {
			double[] pointAttributes = p.getNumericAttrValues();
			for (int i = 0; i < d; i++) {
				meanAttributes[i] += pointAttributes[i];
			}
		}

		for (int i = 0; i < d; i++) {
			meanAttributes[i] = meanAttributes[i] / n;
		}

		return meanAttributes;
	}

	public static void main(String[] args) {

		DataPoint[] dataPoints = new DataPoint[] {
				new DataPoint("2", new double[] { 2.0 }),
				new DataPoint("4", new double[] { 4.0 }),
				new DataPoint("10", new double[] { 10.0 }),
				new DataPoint("12", new double[] { 12.0 }),
				new DataPoint("3", new double[] { 3.0 }),
				new DataPoint("20", new double[] { 20.0 }),
				new DataPoint("30", new double[] { 30.0 }),
				new DataPoint("11", new double[] { 11.0 }),
				new DataPoint("25", new double[] { 25.0 }) };

		DataPoint[] clusterMeans = new DataPoint[] {
				new DataPoint("Mean-2", new double[] { 2.0 }),
				new DataPoint("Mean-4", new double[] { 4.0 }) };

		KMeansAlgorithm kmeans = new KMeansAlgorithm(clusterMeans, dataPoints);
		kmeans.cluster();

		kmeans.print();

	}

	public void printMeans() {
	    System.out.println("Cluster means:");
	    for(DataPoint mean : this.allCentroids) {
	        System.out.println(mean);
	    }
	}
	
	public void print() {
		// show results
		Cluster[] clusters = this.getAllClusters();

		System.out.println("Clusters:");
		for (Cluster c : clusters) {
			System.out.println(c.getElementsAsString());
		}
	}

	public void printAll() {

		Cluster[] clusters = this.getAllClusters();
		System.out.println("Clusters:");
		for (Cluster c : clusters) {
			System.out.println(c.getElementsAsString());
		}
		System.out.println("___________________________________________________");
		DataPoint[] means = this.getAllCentroids();
		System.out.println("Cluster means:");
		for (DataPoint p : means) {
			System.out.println(p.toString());
		}
	}
}

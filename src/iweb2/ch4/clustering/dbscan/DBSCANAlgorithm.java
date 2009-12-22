package iweb2.ch4.clustering.dbscan;

import iweb2.ch4.model.Cluster;
import iweb2.ch4.model.DataPoint;
import iweb2.ch4.similarity.Distance;
import iweb2.ch4.utils.ObjectToIndexMapping;
import iweb2.ch4.utils.TermFrequencyBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of DBSCAN clustering algorithm. 
 * <p>
 * Algorithm parameters:
 * <ol>
 * <li>Eps - threshold value to determine point neighbors. Two points are 
 * neighbors if the distance between them does not exceed this threshold value. 
 * </li>
 * <li>MinPts - minimum number of points in any cluster.</li>
 * </ol>
 * Choice of parameter values depends on the data.
 * </p>
 * <p>
 * Point types:
 * <ol>
 * <li>Core point - point that belongs to the core of the cluster. It has at least
 * MinPts neighboring points.</li>
 * <li>Border point - is a neighbor to at least one core point but it doesn't 
 * have enough neighbors to be a core point.</li>
 * <li>Noise point - is a point that doesn't belong to any cluster because it is
 * not close to any of the core points.</li>
 * </ol>
 */
public class DBSCANAlgorithm {

    /*
     * Data points for clustering.
     */
    private DataPoint[] points;
    
    /*
     * Adjacency matrix. Contains distances between points.
     */
    private double[][] adjacencyMatrix;
    
    /*
     * Threshold value. Determines which points will be considered as
     * neighbors. Two points are neighbors if the distance between them does 
     * not exceed threshold value.
     */
    private double eps;
    
    /*
     * Identifies a set of Noise points.
     */
    private int CLUSTER_ID_NOISE = -1;
    
    /*
     * Identifies a set of Unclassified points.
     */
    private int CLUSTER_ID_UNCLASSIFIED = 0;
    
    /*
     * Sequence that is used to generate next cluster id.
     */
    private int nextClusterId = 1;
    
    /*
     * Sets of points. Initially all points will be assigned into 
     * Unclassified points set.
     */
    private Map<Integer, Set<DataPoint>> clusters = 
        new LinkedHashMap<Integer, Set<DataPoint>>();
    
    
    /*
     * Number of points that should exist in the neighborhood for a point
     * to be a core point.
     * 
     * Best value for this parameter depends on the data set.
     */
    private int minPoints;
    

    private ObjectToIndexMapping<DataPoint> idxMapping = 
        new ObjectToIndexMapping<DataPoint>();
    
    
    private boolean verbose = true;
    
    /**
     * Initializes algorithm with all data that it needs.
     * 
     * @param points points to cluster
     * @param adjacencyMatrix adjacency matrix with distances
     * @param eps distance threshold value
     * @param minPoints number of neighbors for point to be considered a core point.
     */
    public DBSCANAlgorithm(DataPoint[] points, double[][] adjacencyMatrix, double eps, int minPoints) {
        init(points, eps, minPoints);
        this.adjacencyMatrix = adjacencyMatrix;
    }

    /**
     * Initializes algorithm with all data that it needs.
     * 
     * @param points all points to cluster
     * @param distance metric distance function 
     * @param eps threshold value used to calculate point neighborhood.  
     * @param minPoints number of neighbors for point to be considered a core point.
     */
    public DBSCANAlgorithm(DataPoint[] points, 
    		Distance distance, 
    		double eps, 
    		int minPoints, boolean useTermFrequencies) {

        init(points, eps, minPoints);
        this.adjacencyMatrix = 
            calculateAdjacencyMatrix(distance, points, useTermFrequencies);
    }
    
    
    private static double[][] calculateAdjacencyMatrix(Distance distance, 
            DataPoint[] points, 
            boolean useTermFrequencies) {
        int n = points.length;
        double[][] a = new double[n][n];
        for(int i = 0; i < n; i++) {
            double[] x = points[i].getNumericAttrValues();
            for(int j = i + 1; j < n; j++) {
                double[] y; 
                if( useTermFrequencies ) {
                    double[][] tfVectors = 
                        TermFrequencyBuilder.buildTermFrequencyVectors(
                                points[i].getTextAttrValues(), 
                                points[j].getTextAttrValues());
                    x = tfVectors[0];
                    y = tfVectors[1];
                }
                else {
                    y = points[j].getNumericAttrValues();
                }
                a[i][j] = distance.getDistance(x, y);
                a[j][i] = a[i][j];
            }
            a[i][i] = 0.0;
        }
        return a;
    }
        
    private void init(DataPoint[] points, double neighborThreshold, int minPoints) {

        this.points = points;
        this.eps = neighborThreshold;
        this.minPoints = minPoints;

        for(DataPoint p : points) {
            // Creating a Point <-> Index mappping for all points            
            idxMapping.getIndex(p);
            // Assign all points into "Unclassified" group
            assignPointToCluster(p, CLUSTER_ID_UNCLASSIFIED);
        }
    }
    
    public List<Cluster> cluster() {
        int clusterId = getNextClusterId();
        
        for(DataPoint p : points) {
            if( isUnclassified(p) ) {
                
                boolean isClusterCreated = createCluster(p, clusterId);

                if( isClusterCreated ) {
                    // Generate id for the next cluster                    
                    clusterId = getNextClusterId();
                }
            }
        }
        
        // Convert sets of points into clusters...
        List<Cluster> allClusters = new ArrayList<Cluster>();
        
        for(Map.Entry<Integer, Set<DataPoint>> e : clusters.entrySet()) {
        
        	String label = String.valueOf(e.getKey());
            
        	Set<DataPoint> points = e.getValue();
            
        	if( points != null && !points.isEmpty() ) {
            
        		Cluster cluster = new Cluster(label, e.getValue());
                
        		allClusters.add(cluster);
            }
        }
        
        if( verbose ) {
            printResults(allClusters);
        }
        
        // Group with Noise elements returned as well
        return allClusters;
    }

    private boolean createCluster(DataPoint p, Integer clusterId) {

        boolean isClusterCreated = false;
        
        Set<DataPoint> nPoints = findNeighbors(p, eps);

        if( nPoints.size() < minPoints ) {
            // Assign point into "Noise" group. 
            // It will have a chance to become a border point later on.
            assignPointToCluster(p, CLUSTER_ID_NOISE);

            // return false to indicate that we didn't create any cluster
            isClusterCreated = false;
        
        } else {
        	
             // All points are reachable from the core point...
            assignPointToCluster(nPoints, clusterId);
            
            // Remove point itself.
            nPoints.remove(p);
            
            // Process the rest of the neighbors...
            while( nPoints.size() > 0 ) {
            	
                // pick the first neighbor
                DataPoint nPoint = nPoints.iterator().next();
                
                // process neighbor
                Set<DataPoint> nnPoints = findNeighbors(nPoint, eps);
                
                if( nnPoints.size() >= minPoints ) {
                	
                    // nPoint is another core point.  
                    for(DataPoint nnPoint : nnPoints ) {
                    	
                        if( isNoise(nnPoint) ) {
                        
                        	/* It's a border point. We know that it doesn't have 
                             * enough neighbors to be a core point. Just add it 
                             * to the cluster.
                             */
                            assignPointToCluster(nnPoint, clusterId);
                        
                        } else if( isUnclassified(nnPoint) ){
                        	
                            /*
                             * We don't know if this point has enough neighbors
                             * to be a core point... add it to the list of points
                             * to be checked.
                             */
                            nPoints.add(nnPoint);
                            
                            /*
                             * And assign it to the cluster
                             */
                            assignPointToCluster(nnPoint, clusterId);
                        }
                    }
                } else {
                    // do nothing. The neighbor is just a border point.
                }
                
                nPoints.remove(nPoint);
            }
            
            // return true to indicate that we did create a cluster
            isClusterCreated = true;
        }

        return isClusterCreated;
    }
    
    private Set<DataPoint> findNeighbors(DataPoint p, double threshold) {
        Set<DataPoint> neighbors = new HashSet<DataPoint>();
        int i = idxMapping.getIndex(p);
        for(int j = 0, n = idxMapping.getSize(); j < n; j++) {
            if( adjacencyMatrix[i][j] <= threshold ) {
                neighbors.add(idxMapping.getObject(j));
            }
        }
        return neighbors;
    }
    
    private boolean isUnclassified(DataPoint p) {
        return isPointInCluster(p, CLUSTER_ID_UNCLASSIFIED);
        
    }
    
    private boolean isNoise(DataPoint p) {
        return isPointInCluster(p, CLUSTER_ID_NOISE);
    }
    
    private boolean isPointInCluster(DataPoint p, int clusterId) {
        boolean inCluster = false;
        Set<DataPoint> points = clusters.get(clusterId);
        if( points != null ) {
            inCluster = points.contains(p);
        }
        return inCluster;
    }
    
    private void assignPointToCluster(Set<DataPoint> points, int clusterId) {
        for(DataPoint p : points) {
            assignPointToCluster(p, clusterId);
        }
    }
    
    private void assignPointToCluster(DataPoint p, int clusterId) {

        // Remove point from the group that it currently belongs to...
        if( isNoise(p) ) {
            removePointFromCluster(p, CLUSTER_ID_NOISE);
        }
        else if( isUnclassified(p) ) {
            removePointFromCluster(p, CLUSTER_ID_UNCLASSIFIED);
        }
        else {
            if( clusterId != CLUSTER_ID_UNCLASSIFIED ) {
                throw new RuntimeException(
                        "Trying to move point that has already been" + 
                        "assigned to some other cluster. Point: " + p + ", clusterId=" +clusterId);
            }
            else {
                // do nothing. we are registering a brand new point in UNCLASSIFIED set.
            }
        }
        
        Set<DataPoint> points = clusters.get(clusterId);
        if( points == null ) {
            points = new HashSet<DataPoint>();
            clusters.put(clusterId, points);
        }
        points.add(p);
    }
    
    private boolean removePointFromCluster(DataPoint p, int clusterId) {
        boolean removed = false;
        Set<DataPoint> points = clusters.get(clusterId);
        if( points != null ) {
            removed = points.remove(p);
        }
        return removed;
    }
    
    private int getNextClusterId() {
        return nextClusterId++;
    }

    
    public void printDistances() {
        System.out.println("Point Similarity matrix:");
        for(int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.println(Arrays.toString(adjacencyMatrix[i]));
        }
      }
    
    private void printResults(List<Cluster> allClusters) {
        System.out.println("DBSCAN Clustering with NeighborThreshold=" + 
                this.eps + " minPoints=" + this.minPoints);
        System.out.println("Clusters:");
        String noiseElements = "no noise elements";
        for(Cluster c : allClusters) {
            if( String.valueOf(CLUSTER_ID_NOISE).equals(c.getLabel()) ) {
                // print noise data at the end
                noiseElements = c.getElementsAsString();
            }
            else {
            	System.out.println("____________________________________________________________\n");
                System.out.println(c.getLabel() + ": \n" + c.getElementsAsString());
            	System.out.println("____________________________________________________________\n\n");
            }
        }
        System.out.println("Noise Elements:\n " + noiseElements);
    }
    
    public static void main(String[] args) {

        DataPoint[] elements = new DataPoint[5];
        elements[0] = new DataPoint("A", new double[] {});
        elements[1] = new DataPoint("B", new double[] {});
        elements[2] = new DataPoint("C", new double[] {});
        elements[3] = new DataPoint("D", new double[] {});
        elements[4] = new DataPoint("E", new double[] {});

        double[][] a = new double[][] {
            {0,1,2,2,3},
            {1,0,2,4,3},
            {2,2,0,1,5},
            {2,4,1,0,3},
            {3,3,5,3,0}
        };
        
        double threshold = 2.0;
        int minPoints = 2;
        
        DBSCANAlgorithm dbscan = new DBSCANAlgorithm(elements, a, threshold, minPoints);
        dbscan.cluster();
    }
    
}

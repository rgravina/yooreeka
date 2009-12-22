package iweb2.ch4.clustering.partitional;

import iweb2.ch4.model.Cluster;
import iweb2.ch4.model.DataPoint;
import iweb2.ch4.similarity.Distance;
import iweb2.ch4.similarity.EuclideanDistance;
import iweb2.ch4.utils.ObjectToIndexMapping;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborAlgorithm {

    /*
     * All elements for clustering.
     */
    private DataPoint[] allDataPoints;
    
    /*
     * Matrix with distances between elements.
     */
    private double[][] a;
    
    /* 
     * Threshold value that is used to determine if elements will be 
     * added to one of the existing clusters or if a new cluster will 
     * be created.
     */
    private double t = 0.5;

    /*
     * List of clusters.
     */
    private List<Cluster> allClusters;
    
    /*
     * Distance metric that will be used to calculate distance between elements.
     */
    private Distance dist = new EuclideanDistance();
    
    /* 
     * DataPoint -> Index mapping. Used to access data in distance matrix. 
     */
    ObjectToIndexMapping<DataPoint> idxMapping = null;
    
    private boolean verbose = true;
    
    /**
     * 
     * @param dataPoints elements to cluster. Element order should correspond to 
     *                   elements in distance matrix.
     * @param a matrix showing distance between elements. Can be null.  
     * @param t threshold value for new cluster creation.
     */
    public NearestNeighborAlgorithm(DataPoint[] dataPoints, double[][] a, double t) {
        this.t = t;
        this.allDataPoints = dataPoints;
        this.a = a;
        this.allClusters = new ArrayList<Cluster>();
        
        /* 
         * Create DataPoint -> Index mapping for all data points.
         */
        idxMapping = new ObjectToIndexMapping<DataPoint>();
        
        for(int i = 0, n = dataPoints.length; i < n; i++) {
            idxMapping.getIndex(dataPoints[i]);
        }
        
    }
    
    public NearestNeighborAlgorithm(DataPoint[] dataPoints, double t) {
        this(dataPoints, null, t);
    }
    
    public void setDistance(Distance dist) {
        this.dist = dist;
    }
    
    /**
     * Calculates distance between cluster and element using 
     * Nearest Neighbor approach.   
     */
    private double getNNDistance(Cluster c, DataPoint x) {

        double nnDist = Double.POSITIVE_INFINITY;
            
        if( c.contains(x) ) {
            nnDist = 0.0;
        }
        else {
            int i = idxMapping.getIndex(x);
            for(DataPoint y : c.getElements()) {
                int j = idxMapping.getIndex(y);
                double xyDist = a[i][j];
                nnDist = Math.min(nnDist, xyDist);
            }
        }
        
        return nnDist;
    }
    
    public void run() {

        if( allDataPoints == null || allDataPoints.length == 0) {
            return;
        }
        
        if( a == null ) {
            calculateDistanceMatrix();
        }
        
        for(int i = 0, n = allDataPoints.length; i < n; i++) {
            assignPointToCluster(allDataPoints[i]);
        }
        
        if( verbose ) {
            printResults();
        }
    }

    private void assignPointToCluster(DataPoint x) {

        /* find min distance between current point and all clusters */
        double minNNDist = Double.POSITIVE_INFINITY;
        Cluster closestCluster = null;
        for(Cluster c : allClusters) {
            double nnDist = getNNDistance(c, x); 
            if( nnDist < minNNDist ) {
                minNNDist = nnDist;
                closestCluster = c;
            }
        }
        
        /* Assign point to cluster based on calculated distance and threshold */ 
        if( minNNDist <= t ) {
            closestCluster.add(x);
        }
        else {
            /* Best distance exceeds the threshold - create a new cluster. */
            Cluster newCluster = new Cluster();
            newCluster.add(x);
            allClusters.add(newCluster);
        }
    }

    
    private void calculateDistanceMatrix() {
        a = new double[allDataPoints.length][allDataPoints.length];
        for(int i = 0, n = allDataPoints.length; i < n; i++) {
            DataPoint x = allDataPoints[i];
            for(int j = i + 1; j < n; j++) {
                DataPoint y = allDataPoints[j];
                a[i][j] = dist.getDistance(
                        x.getNumericAttrValues(), y.getNumericAttrValues());
                a[j][i] = a[i][j];
            }
            a[i][i] = 0.0;
        }
    }
    
    public List<Cluster> getAllClusters() {
        return allClusters;
    }
 
    private void printResults() {
        System.out.println("Nearest Neighbor Clustering with t = " + t);
        System.out.println("Clusters:");
        for(Cluster c : allClusters) {
            System.out.println(c.getElementsAsString());
        }
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
        
        double threshold = 2;
        
        NearestNeighborAlgorithm nn = 
            new NearestNeighborAlgorithm(elements, a, threshold);
        
        nn.run();
    }
}

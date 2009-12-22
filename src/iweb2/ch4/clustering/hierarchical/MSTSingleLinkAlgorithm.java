package iweb2.ch4.clustering.hierarchical;

import iweb2.ch4.model.Cluster;
import iweb2.ch4.model.DataPoint;

public class MSTSingleLinkAlgorithm {

    private DataPoint[] elements;
    private double[][] a;
    private double[][] m;
    private ClusterSet allClusters;
    
    public MSTSingleLinkAlgorithm(DataPoint[] elements, double[][] adjacencyMatrix) {
        this.elements = elements;
        this.a = adjacencyMatrix;
        this.allClusters = new ClusterSet();
    }
    
    public Dendrogram cluster() {
        
        m = (new MST()).buildMST(a);
        
        Dendrogram dnd = new Dendrogram("Distance");
        double d = 0.0;

        // initially load all elements as individual clusters
        for(DataPoint e : elements) {
            Cluster c = new Cluster(e);
            allClusters.add(c);
        }

        int lastDndLevel = dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
        
        double previousD = d;
        
        while( allClusters.size() > 1 ) {
            d = mergeTwoClosestClusters();
            if( previousD == d ) {
                dnd.setLevel(lastDndLevel, String.valueOf(d), allClusters.getAllClusters());
            }
            else {
                lastDndLevel = dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
            }
            previousD = d;
        }
        
        return dnd;
    }
    
    private double mergeTwoClosestClusters() {
        int minI = -1;
        int minJ = -1;
        double minWeight = Double.POSITIVE_INFINITY;
        
        for(int i = 0, n = m.length; i < n; i++) {
            for(int j = 0, k = m.length; j < k; j++) {
                if( m[i][j] >= 0 && minWeight > m[i][j] ) {
                    minI = i;
                    minJ = j;
                    minWeight = m[i][j];
                }
            }
        }


        double d = Double.NaN;
        if( minI > -1 ) {
            DataPoint e1 = elements[minI];
            Cluster c1 = allClusters.findClusterByElement(e1);
            DataPoint e2 = elements[minJ];
            Cluster c2 = allClusters.findClusterByElement(e2);
            allClusters.remove(c1);
            allClusters.remove(c2);
            d = minWeight;
            Cluster mergedCluster = 
                new Cluster(c1, c2);
            allClusters.add(mergedCluster);
            m[minI][minJ] = -1; // remove link. Using -1 because 0 is a valid distance.
            m[minJ][minI] = -1; // remove link. Using -1 because 0 is a valid distance.
        }

        return d;
    }
    
    public static void main(String[] args) {
        //Define data
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
        
        MSTSingleLinkAlgorithm ca = new MSTSingleLinkAlgorithm(elements, a);
        Dendrogram dnd = ca.cluster();
        dnd.printAll();
    }
}

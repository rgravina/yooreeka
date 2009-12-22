package iweb2.ch4.clustering.hierarchical;

import iweb2.ch4.model.Cluster;
import iweb2.ch4.model.DataPoint;
import iweb2.ch4.utils.ObjectToIndexMapping;

/** A hierarchical agglomerative clustering algorithm based on the average link */
public class AverageLinkAlgorithm {

    private DataPoint[] elements;
    private double[][] a;
    private ClusterSet allClusters;
    
    public AverageLinkAlgorithm(DataPoint[] elements, double[][] adjacencyMatrix) {
        this.elements = elements;
        this.a = adjacencyMatrix;
        this.allClusters = new ClusterSet();
    }
    
    public Dendrogram cluster() {
        
        Dendrogram dnd = new Dendrogram("Distance");
        double d = 0.0;

        // initially load all elements as individual clusters
        for(DataPoint e : elements) {
            Cluster c = new Cluster(e);
            allClusters.add(c);
        }
        
        dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());
        
        d = 1.0;
        
        while( allClusters.size() > 1 ) {
            int K = allClusters.size();
            mergeClusters(d);
            // it is possible that there were no clusters to merge for current d.
            if( K > allClusters.size() ) {
                dnd.addLevel(String.valueOf(d), allClusters.getAllClusters());                
                K = allClusters.size();                
            }
            
            d = d + 0.5;
        }
        return dnd;
    }
    
    private void mergeClusters(double distanceThreshold) {
        int nClusters = allClusters.size();

        ObjectToIndexMapping<Cluster> idxMapping = 
            new ObjectToIndexMapping<Cluster>();
        
        double[][] clusterDistances = new double[nClusters][nClusters];
        
        for(int i = 0, n = a.length; i < n; i++) {
            for(int j = i + 1, k = a.length; j < k; j++) {
                double d = a[i][j];
                if( d > 0 ) {
                    DataPoint e1 = elements[i];
                    DataPoint e2 = elements[j];
                    Cluster c1 = allClusters.findClusterByElement(e1);
                    Cluster c2 = allClusters.findClusterByElement(e2);
                    if( !c1.equals(c2) ) {
                        int ci = idxMapping.getIndex(c1);
                        int cj = idxMapping.getIndex(c2);
                        clusterDistances[ci][cj] += d;
                        clusterDistances[cj][ci] += d;                        
                    }
                }
            }
        }

        boolean[] merged = new boolean[clusterDistances.length];
        for(int i = 0, n = clusterDistances.length; i < n; i++) {
            for(int j = i + 1, k = clusterDistances.length; j < k; j++) {
                Cluster ci = idxMapping.getObject(i);
                Cluster cj = idxMapping.getObject(j);
                int ni = ci.size();
                int nj = cj.size();
                clusterDistances[i][j] = clusterDistances[i][j] / (ni * nj);
                clusterDistances[j][i] = clusterDistances[i][j];
                // merge clusters if distance is below the threshold
                if( merged[i] == false && merged[j] == false ) {
                    if( clusterDistances[i][j] <= distanceThreshold) {
                        allClusters.remove(ci);
                        allClusters.remove(cj);
                        Cluster mergedCluster = new Cluster(ci, cj);
                        allClusters.add(mergedCluster);
                        merged[i] = true;
                        merged[j] = true;
                    }
                }
            }
        }
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
        
        AverageLinkAlgorithm ca = new AverageLinkAlgorithm(elements, a);
        Dendrogram dnd = ca.cluster();
        dnd.printAll();
    }
}

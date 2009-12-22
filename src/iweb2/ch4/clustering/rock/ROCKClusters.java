package iweb2.ch4.clustering.rock;

import iweb2.ch4.model.Cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Set of clusters and link data for ROCK implementation.
 */
public class ROCKClusters {

    /*
     * Used to assign unique IDs to clusters.
     */
    private int nextKey;
    
    /*
     * Provides ID -> Cluster mapping.
     */
    private Map<Integer, Cluster> clusterMap;
    
    /*
     * Provides ID -> Similar Clusters mapping.
     */
    private Map<Integer, List<SimilarCluster>> similarClustersMap;
    
    /*
     * Goodness measure between two clusters. It is used to determine cluster
     * eligibility for merge.
     */
    private MergeGoodnessMeasure goodnessMeasure;
    
    /*
     * Links between data points and clusters.
     */
    private LinkMatrix linkMatrix;
    
    public ROCKClusters(
            List<Cluster> initialClusters, 
            LinkMatrix linkMatrix, 
            MergeGoodnessMeasure goodnessMeasure) {
        
        this.linkMatrix = linkMatrix;
        clusterMap = new HashMap<Integer, Cluster>();
        nextKey = 0;    
        this.goodnessMeasure = goodnessMeasure;
        
        for(Cluster c : initialClusters) {
            addCluster(c);
        }
        calculateClusterSimilarities();
    }
    
    public int size() {
        return clusterMap.size();
    }
    
    public int addCluster(Cluster c) {
        int key = nextKey;
        clusterMap.put(key, c);
        nextKey++;
        return key;
    }
    
    public void calculateClusterSimilarities() {
        similarClustersMap = new HashMap<Integer, List<SimilarCluster>>();
        for(Integer clusterKey : getAllKeys()) {
            List<SimilarCluster> similarClusters = new LinkedList<SimilarCluster>();
            Cluster cluster = getCluster(clusterKey);
            for(Integer similarClusterKey : getAllKeys()) {
                if( clusterKey != similarClusterKey ) {
                    Cluster similarCluster = getCluster(similarClusterKey);
                    int nLinks = linkMatrix.getLinks(cluster, similarCluster);
                    if( nLinks > 0 ) {
                        double goodness = goodnessMeasure.g(nLinks, 
                                cluster.size(), similarCluster.size());
                        similarClusters.add(
                                new SimilarCluster(similarClusterKey, goodness));
                    }
                }
            }
            setSimilarClusters(clusterKey, similarClusters);
        }
    }
    
    private void setSimilarClusters(Integer key, List<SimilarCluster> list) {
        SimilarCluster.sortByGoodness(list);
        similarClustersMap.put(key, list);
    }
    
    public double mergeBestCandidates() {
        List<Integer> mergeCandidates = findBestMergeCandidates();
        
        double goodness = Double.NaN; 
                    
        
        if( mergeCandidates.size() > 1 ) {
            
            Integer key1 = mergeCandidates.get(0);
            Integer key2 = mergeCandidates.get(1);
            goodness = similarClustersMap.get(key1).get(0).getGoodness();
            
            mergeClusters(key1, key2);
        }
        
        return goodness;
    }
    
    /**
     * Finds a pair of cluster indexes with the best goodness measure.
     */
    public List<Integer> findBestMergeCandidates() {
        Integer bestKey = null;
        SimilarCluster bestSimilarCluster = null;
        Double bestGoodness = Double.NEGATIVE_INFINITY;   
        for(Map.Entry<Integer, List<SimilarCluster>> e : similarClustersMap.entrySet()) {
            List<SimilarCluster> similarClusters = e.getValue();
            if( similarClusters != null && similarClusters.size() > 0 ) {
                SimilarCluster topSimilarCluster = similarClusters.get(0);
                if( topSimilarCluster.getGoodness() > bestGoodness ) {
                    bestGoodness = topSimilarCluster.getGoodness();
                    bestKey = e.getKey();
                    bestSimilarCluster = topSimilarCluster;
                }
            }
        }
        List<Integer> bestMergeCandidates = new ArrayList<Integer>();
        if( bestKey != null ) {
            bestMergeCandidates.add(bestKey);
            bestMergeCandidates.add(bestSimilarCluster.getClusterKey());
        }
        return bestMergeCandidates;
    }
    
    public Integer mergeClusters(Integer key1, Integer key2) {
        
        Cluster cluster1 = getCluster(key1);
        Cluster cluster2 = getCluster(key2);
        Cluster cluster3 = new Cluster(cluster1, cluster2);
        removeCluster(key1);
        removeCluster(key2);
        Integer key3 = addCluster(cluster3);        

        calculateClusterSimilarities();
        
        return key3;
    }
    
    public Cluster removeCluster(Integer key) {
        return clusterMap.remove(key);
    }
    
    public Cluster getCluster(Integer key) {
        return clusterMap.get(key);
    }
    
    public Set<Integer> getAllKeys() {
        return new HashSet<Integer>(clusterMap.keySet());
    }
    
    public Collection<Cluster> getAllClusters() {
        return clusterMap.values();
    }
}

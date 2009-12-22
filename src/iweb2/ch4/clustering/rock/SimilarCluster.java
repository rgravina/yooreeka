package iweb2.ch4.clustering.rock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimilarCluster {
    private Integer clusterKey;
    private Double goodness;
    
    public SimilarCluster(Integer clusterKey, Double goodness) {
        this.clusterKey = clusterKey;
        this.goodness = goodness;
    }

    public Integer getClusterKey() {
        return clusterKey;
    }

    public Double getGoodness() {
        return goodness;
    }
    
    /**
     * Sorts list by goodness value in descending order. Higher 
     * goodness values will be in the head of the list.
     * 
     * @param values list to sort.
     */
    public static void sortByGoodness(List<SimilarCluster> values) {
        Collections.sort(values, new Comparator<SimilarCluster>() {
            
            public int compare(SimilarCluster f1, SimilarCluster f2) {
                
                int result = 0;
                if( f1.getGoodness() < f2.getGoodness() ) {
                    result = 1; // order in the decreasing order of goodness value 
                }
                else if( f1.getGoodness() > f2.getGoodness() ) {
                    result = -1;
                }
                else {
                    result = 0;
                }
                return result;
            }
        });        
    }
    
    @Override
	public String toString() {
        return "[clusterKey=" + this.clusterKey + ",goodness=" + this.goodness+ "]"; 
    }
}

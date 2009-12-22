package iweb2.ch2.ranking;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class that acts as a holder for double value and id of the object that
 * this value corresponds.
 */
public class RelevanceScore {
    private String id;
    private double score;
    
    public RelevanceScore(String id, double rank) {
        this.id = id;
        this.score = rank;
    }
    
    public String getId() {
        return id;
    }
    
    public double getScore() {
        return score;
    }
    
    /**
     * Sorts list in descending order of score value.
     */
    public static void sort(List<RelevanceScore> values) {
        Collections.sort(values, new Comparator<RelevanceScore>() {
            public int compare(RelevanceScore r1, RelevanceScore r2) { 
                int result = 0;
                // sort based on score value
                if( r1.getScore() < r2.getScore() ) {
                    result = 1; // sorting in descending order
                }
                else if( r1.getScore() > r2.getScore() ) {
                    result = -1;
                }
                else {
                    result = 0;
                }
                return result;
            }
        });
    }
    
}

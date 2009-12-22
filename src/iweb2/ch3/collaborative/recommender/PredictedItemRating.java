package iweb2.ch3.collaborative.recommender;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents predicted user rating of an item. Used to return recommendations
 * for the user.
 */
public class PredictedItemRating {
    private int userId;
    private int itemId;
    private double rating;

    public PredictedItemRating(int userId, int itemId, double rating) {
        this.userId = userId;
        this.itemId = itemId;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public double getRating() {
        return rating;
    }
    
    public void setRating(double val) {
        this.rating = val;
    }

    /**
     * Returns rounded rating value with number of digits after decimal point 
     * specified by <code>scale</code> parameter. 
     * 
     * @param scale number of digits to keep after decimal point.
     * @return rounded value.
     */
    public double getRating(int scale) {
        BigDecimal bd = new BigDecimal(rating);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
	public String toString() {
        return this.getClass().getSimpleName() + "[userId: " + userId
                + ", itemId: " + itemId + ", rating: " + rating + "]";
    }
    
    /**
     * Sorts list by rating value in descending order. Items with higher ratings
     * will be in the head of the list.
     * 
     * @param values list to sort.
     */
    public static void sort(List<PredictedItemRating> values) {
        Collections.sort(values, new Comparator<PredictedItemRating>() {
            
            public int compare(PredictedItemRating f1, PredictedItemRating f2) {
                
                int result = 0;
                if( f1.getRating() < f2.getRating() ) {
                    result = 1; // reverse order
                }
                else if( f1.getRating() > f2.getRating() ) {
                    result = -1;
                }
                else {
                    result = 0;
                }
                return result;
            }
        });        
    }
    
    /**
     * Sorts list of recommendations in descending order and return topN elements.
     * 
     * @param recommendations 
     * @param topN
     * @return
     */
    public static List<PredictedItemRating> getTopNRecommendations(
            List<PredictedItemRating> recommendations, int topN) {
        
        PredictedItemRating.sort(recommendations);
        
        List<PredictedItemRating> topRecommendations = new ArrayList<PredictedItemRating>();
        for(PredictedItemRating r : recommendations) {
            if( topRecommendations.size() >= topN ) { 
                // have enough recommendations.
                break;
            }
            topRecommendations.add(r);
        }
        
        return topRecommendations;
    }
    
    public static void printUserRecommendations(
            User user, Dataset ds, List<PredictedItemRating> recommendedItems) {
        System.out.println("\nRecommendations for user " + user.getName() + ":\n");
        for(PredictedItemRating r : recommendedItems) {
            System.out.printf("Item: %-36s, predicted rating: %f\n",
                    ds.getItem(r.getItemId()).getName(), r.getRating(4));
        }
    }
}

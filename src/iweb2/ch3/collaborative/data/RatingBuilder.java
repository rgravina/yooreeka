package iweb2.ch3.collaborative.data;

import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class to generate random ratings. 
 */
class RatingBuilder {

    private Random rand = null;
 
    public RatingBuilder() {
        rand = new java.util.Random();
    }
    
    /**
     * Creates biased ratings for all items.
     *  
     * @param userId rating user.
     * @param items to create ratings for. 
     * @param lowerBias low range for rating value
     * @param upperBias high range for rating value
     * @return
     */
    public List<Rating> createBiasedRatings(
            int userId, Item[] items, int lowerBias, int upperBias) {
        List<Rating> ratings = new ArrayList<Rating>();
        for(Item item : items) {
            int biasedRandomRating = getRandomRating(lowerBias, upperBias);
            Rating rating = new Rating(
                    userId, item.getId(), biasedRandomRating);
            rating.setItem(item);
            ratings.add(rating);
        }
        return ratings;
    }
    
    
    public int getRandomRating() {
        //No bias
        return getRandomRating(5);
    }
    
    public int getRandomRating(int upperBias) {
                
        // Lower bias is 1
        return getRandomRating(1,upperBias);
    }
    
    public int getRandomRating(int lowerBias, int upperBias) {
        
        //We add 1 at the end because the nextInt(n) call excludes n
        int n = (upperBias - lowerBias) + 1;
        return (lowerBias + rand.nextInt(n));       
    }

}

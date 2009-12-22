package iweb2.ch3.collaborative.recommender;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.SimilarItem;
import iweb2.ch3.collaborative.model.SimilarUser;
import iweb2.ch3.collaborative.model.User;

import java.util.List;

public interface Recommender {

    /**
     * Returns top 5 recommendations for the user.
     * 
     * @param user
     * @return recommended items with predicted ratings.
     */
    public List<PredictedItemRating> recommend(User user);
    
    /**
     * Returns top N recommendations for the user.
     * 
     * @param user 
     * @param topN number of top recommendations to return.
     * @return recommended items with predicted ratings.
     */
    public List<PredictedItemRating> recommend(User user, int topN);    
    
    public double predictRating(User user, Item item);
    
    public SimilarUser[] findSimilarUsers(User user);
    
    public SimilarUser[] findSimilarUsers(User user, int topN);
    
    public SimilarItem[] findSimilarItems(Item item);    

    public SimilarItem[] findSimilarItems(Item item, int topN);    
    
    public Dataset getDataset();
    
    public double getSimilarityThreshold();
    
    public void setSimilarityThreshold(double similarityThreshold);    
}

package iweb2.ch3.collaborative.recommender;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;
import iweb2.ch3.collaborative.model.SimilarItem;
import iweb2.ch3.collaborative.model.SimilarUser;
import iweb2.ch3.collaborative.model.User;
import iweb2.ch3.collaborative.similarity.MovieLensItemSimilarity;

import java.util.ArrayList;
import java.util.List;

/**
 * Recommender. Has to be initialized with similarity function and dataset.
 */
public class MovieLensDelphi implements Recommender {

    private static final double DEFAULT_SIMILARITY_THRESHOLD = 0.50;

    private Dataset dataSet;
    private boolean verbose = true;
    private double similarityThreshold = DEFAULT_SIMILARITY_THRESHOLD;
        
    private MovieLensItemSimilarity itemSimilarityMatrix;
    
    public MovieLensDelphi(Dataset ds) {
    	System.out.println("Entering MovieLensDelphi(Dataset) constructor ...");
        
        this.dataSet = ds;

        //------------------------------------------------------------------------
        System.out.println("Calculating item based similarities...");
        long start = System.currentTimeMillis();
        
        itemSimilarityMatrix = new MovieLensItemSimilarity(ds);
        
        System.out.println("Item based similarities calculated in " + 
                (System.currentTimeMillis() - start) /1000 + "(sec).");
        System.out.println("Similarities ready.");
        //------------------------------------------------------------------------
            	
        System.out.println("Leaving MovieLensDelpi(Dataset) constructor ...");
    }

    //--------------------------------------------------------------------
    // USER BASED SIMILARITY
    //--------------------------------------------------------------------

//    public SimilarUser[] findSimilarUsers(User user) {
//        SimilarUser[] topFriends = findSimilarUsers(user, 5);
//
//        if( verbose ) {
//            SimilarUser.print(topFriends, "Top Friends for user " + user.getName() + ":");
//        }
//        
//        return topFriends;
//    }
//        
//    public SimilarUser[] findSimilarUsers(User user, int topN) {
//    	
//        List<SimilarUser> similarUsers = new ArrayList<SimilarUser>();
//        
//        similarUsers = findFriendsBasedOnUserSimilarity(user);
//            
//        System.out.println("Finding friends based on Item similarity is not supported!");
//
//        return SimilarUser.getTopNFriends(similarUsers, topN);
//    }
//    
//    
//    private List<SimilarUser> findFriendsBasedOnUserSimilarity(User user) {
//    	
//        List<SimilarUser> similarUsers = new ArrayList<SimilarUser>(); 
//        
//        for(User friend : dataSet.getUsers()) {
//        
//        	if( user.getId() != friend.getId() ) {
//            
//        		double similarity = 
//        			userSimilarityMatrix.getValue(user.getId(), friend.getId());
//                similarUsers.add(new SimilarUser(friend, similarity));
//            }
//        }
//        
//        return similarUsers;
//    }

    //--------------------------------------------------------------------
    // ITEM BASED SIMILARITY
    //--------------------------------------------------------------------
    
    public SimilarItem[] findSimilarItems(Item item) {
        SimilarItem[] topFriends = findSimilarItems(item, 5);
        
        if( verbose ) {
            SimilarItem.printItems(topFriends, "Items like item " + item.getName() + ":");
        }
        return topFriends;
    }
        
    public SimilarItem[] findSimilarItems(Item item, int topN) {
    	
        List<SimilarItem> similarItems = new ArrayList<SimilarItem>();
        
        similarItems = findItemsBasedOnItemSimilarity(item);
            
        return SimilarItem.getTopSimilarItems(similarItems, topN);
    }
    
    
    private List<SimilarItem> findItemsBasedOnItemSimilarity(Item item) {
    	
        List<SimilarItem> similarItems = new ArrayList<SimilarItem>(); 
        
        int itemId = item.getId();
        
        for(Item sItem : dataSet.getItems()) {
        
        	if( itemId != sItem.getId() ) {
            
        		double similarity = itemSimilarityMatrix.getValue(itemId, sItem.getId());
        		if( similarity > 0.5 ) {
        		    similarItems.add(new SimilarItem(sItem, similarity));
        		}
            }
        }
        
        return similarItems;
    }


	//--------------------------------------------------------------------
    // RECOMMENDATIONS
    //--------------------------------------------------------------------
    public List<PredictedItemRating> recommend(User user) {
        List<PredictedItemRating> recommendedItems = recommend(user, 5);
        if( verbose ) {
            PredictedItemRating.printUserRecommendations(user, dataSet, recommendedItems);
        }
        return recommendedItems;
    }
    
    public List<PredictedItemRating> recommend(User user, int topN) {

        List<PredictedItemRating> recommendations = new ArrayList<PredictedItemRating>();

        for (Item item : dataSet.getItems()) {

        	// only consider items that user hasn't rated yet
            if (user.getItemRating(item.getId()) == null) {
            	
                double predictedRating = estimateItemBasedRating(user,item);
                
                if (!Double.isNaN(predictedRating)) {
                    recommendations.add(new PredictedItemRating(user.getId(),
                            item.getId(), predictedRating));
                }
            }
        }
        
        return getTopNRecommendations(recommendations, topN);
    }
    
    //--------------------------------------------------------------------
    // AUXILIARY METHODS
    //--------------------------------------------------------------------
    public double getSimilarityThreshold() {
        return similarityThreshold;
    }

    public void setSimilarityThreshold(double similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }
    
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    // -----------------------------------------------------------
    // PRIVATE (AUXILIARY) METHODS
    // -----------------------------------------------------------
    private double estimateItemBasedRating(User user, Item item) {
        
        double itemRating = item.getAverageRating();

        int itemId = item.getId();
        int userId = user.getId();
        
        double itemAvgRating = item.getAverageRating();
        
        double weightedDeltaSum = 0.0;
        int sumN=0;
        
        // check if the user has already rated the item
        Rating existingRatingByUser = user.getItemRating(item.getId());
        
        if (existingRatingByUser != null) {
        
        	itemRating = existingRatingByUser.getRating();
        
        } else {
            
        	double similarityBetweenItems = 0;
        	double weightedDelta = 0;
        	double delta = 0;
        	
        	for (Item anotherItem : dataSet.getItems()) {
            
        		// only consider items that were rated by the user
                Rating anotherItemRating = anotherItem.getUserRating(userId);
                
                if (anotherItemRating != null) {
                
                	delta = itemAvgRating - anotherItemRating.getRating();
                	
                	similarityBetweenItems = itemSimilarityMatrix.getValue(itemId, anotherItem.getId());
                    
                	if (Math.abs(similarityBetweenItems) > similarityThreshold) {
                		
                		weightedDelta = similarityBetweenItems * delta;
                    
                		weightedDeltaSum += weightedDelta;
                		
                		sumN++;
                	}
                }
            }
            	
        	if (sumN > 0) {
        		itemRating = itemAvgRating - (weightedDeltaSum/sumN);
        	}
        }

        return itemRating;
    }
    
    public List<PredictedItemRating> getTopNRecommendations(
            List<PredictedItemRating> recommendations, int topN) {
        
        PredictedItemRating.sort(recommendations);
        
        double maxR = recommendations.get(0).getRating();
        double scaledR;
        
        List<PredictedItemRating> topRecommendations = new ArrayList<PredictedItemRating>();
        for(PredictedItemRating r : recommendations) {
            if( topRecommendations.size() >= topN ) { 
                // have enough recommendations.
                break;
            }
            if( maxR > 5 ) {
                scaledR = r.getRating() * (5/maxR);
                r.setRating(scaledR);
            }
            
            topRecommendations.add(r);
        }
        
        return topRecommendations;
    }

    public Dataset getDataset() {
        return dataSet;
    }
    
    public double predictRating(User user, Item item) {
        return estimateItemBasedRating(user, item);
    }

    public SimilarUser[] findSimilarUsers(User user) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public SimilarUser[] findSimilarUsers(User user, int topN) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
}

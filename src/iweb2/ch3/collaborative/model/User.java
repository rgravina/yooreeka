package iweb2.ch3.collaborative.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic representation of user which rates items.
 */
public class User implements java.io.Serializable {

    /**
	 * Unique identifier for serialization
	 */
	private static final long serialVersionUID = -1884424246968533858L;
	
	int id;
    String name;
    
    protected Map<Integer, Rating> ratingsByItemId;

    public User(int id) {
        this(id, String.valueOf(id), new ArrayList<Rating>(3));
    }

    public User(int id, String name) {
        this(id, name, new ArrayList<Rating>(3));
    }
    
    public User(int id, List<Rating> ratings) {
        this(id, String.valueOf(id), ratings);
    }

    public User(int id, String name, List<Rating> ratings) {
        this.id = id;
        this.name = name;
        ratingsByItemId = new HashMap<Integer, Rating>(ratings.size());
        for (Rating r : ratings) {
            ratingsByItemId.put(r.getItemId(), r);
        }
    }
    
    public double getAverageRating() {
        double allRatingsSum = 0.0;
        Collection<Rating> allUserRatings = getAllRatings(); 
        for(Rating rating : allUserRatings ) {
            allRatingsSum += rating.getRating();
        }
        return allUserRatings.size() > 0 ? allRatingsSum / allUserRatings.size() : 2.5;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public void setRatings(List<Rating> ratings) {
    	// Initialize or clean up
    	if (ratingsByItemId == null) {
    		ratingsByItemId = new HashMap<Integer, Rating>(ratings.size());
    	} else {
    		ratingsByItemId.clear();
    	}
    	
    	//Load the ratings
        for (Rating r : ratings) {
            ratingsByItemId.put(r.getItemId(), r);
        }
    }
    
    public void addRating(Rating rating) {
        ratingsByItemId.put(rating.getItemId(), rating);
    }

    public Collection<Rating> getAllRatings() {
        return ratingsByItemId.values();
    }

    public Rating getItemRating(Integer itemId) {
        return ratingsByItemId.get(itemId);
    }
    
    private List<Content> userContent = new ArrayList<Content>();
    
    public List<Content> getUserContent() {
        return userContent;
    }
    
    public Content getUserContent(String contentId) {
        Content matchedContent = null;
        for(Content c : userContent) {
            if( c.getId().equals(contentId)) {
                matchedContent = c;
                break;
            }
        }
        return matchedContent;
    }
    
    public void setUserContent(List<Content> content) {
        this.userContent = content;
    }
    
    public void addUserContent(Content content) {
        userContent.add(content);
    }
    
    /**
     * Utility method to extract item ids that are shared between user A and user B.
     */
    public static Integer[] getSharedItems(User x, User y) {
        List<Integer> sharedItems = new ArrayList<Integer>();
        for(Rating r :x.getAllRatings()) {
            if( y.getItemRating(r.getItemId()) != null ) {
                sharedItems.add(r.getItemId());
            }
        }
        return sharedItems.toArray(new Integer[sharedItems.size()]);
    }
    
    /*
     * Utility method to extract array of ratings based on array of item ids.
     */
    public double[] getRatingsForItemList(Integer[] itemIds) {
        double[] ratings = new double[itemIds.length];
        for(int i = 0, n = itemIds.length; i < n; i++) {
            Rating r = getItemRating(itemIds[i]);
            if( r == null ) {
                throw new IllegalArgumentException(
                        "User doesn't have specified item id (" +
                        "userId=" + getId() + ", itemId="+itemIds[i]);
            }
            ratings[i] = r.getRating();
        }
        return ratings;
    }
    
}

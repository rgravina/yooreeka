package iweb2.ch3.collaborative.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic representation of product or service that users can rate.
 */
public class Item implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6119040388138010186L;

	/*
     * Unique id in the dataset.
     */
    private int id;
    
    /*
     * Name. 
     */
    private String name;
    
    /*
     * All ratings for this item. Supports only one rating per item for a user.
     * Mapping: userId -> rating 
     */
    private Map<Integer, Rating> ratingsByUserId;
    
    public Item(Integer id, List<Rating> ratings) {
        this(id, String.valueOf(id), ratings);
    }

    public Item(Integer id, String name) {
        this(id, name, new ArrayList<Rating>(3));
    }
    
    public Item(Integer id, String name, List<Rating> ratings) {
        this.id = id;
        this.name = name;
        // load ratings into userId -> rating map.
        ratingsByUserId = new HashMap<Integer, Rating>(ratings.size());
        for (Rating r : ratings) {
            ratingsByUserId.put(r.getUserId(), r);
        }
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public double getAverageRating() {
        double allRatingsSum = 0.0;
        Collection<Rating> allItemRatings = ratingsByUserId.values();
        for(Rating rating : allItemRatings ) {
            allRatingsSum += rating.getRating();
        }
        // use 2.5 if there are no ratings.
        return allItemRatings.size() > 0 ? 
                allRatingsSum / allItemRatings.size() : 2.5;
    }

    /**
     * Returns all ratings that we have for this item.
     * @return 
     */
    public Collection<Rating> getAllRatings() {
        return ratingsByUserId.values();
    }

    /**
     * Returns rating that specified user gave to the item.
     * 
     *  @param userId user
     *  @return user rating or null if user hasn't rated this item.
     */
    public Rating getUserRating(Integer userId) {
        return ratingsByUserId.get(userId);
    }
    
    /**
     * Updates existing user rating or adds a new user rating for this item.  
     * 
     * @param r rating to add.
     */
    public void addUserRating(Rating r) {
        ratingsByUserId.put(r.getUserId(), r);
    }

    private Content itemContent;
    
    public Content getItemContent() {
        return itemContent;
    }
    
    public void setItemContent(Content content) {
        this.itemContent = content;
    }
    
    public static Integer[] getSharedUserIds(Item x, Item y) {
        List<Integer> sharedUsers = new ArrayList<Integer>();
        for(Rating r :x.getAllRatings()) {
            // same user rated the item
            if( y.getUserRating(r.getUserId()) != null ) {
                sharedUsers.add(r.getUserId());
            }
        }
        return sharedUsers.toArray(new Integer[sharedUsers.size()]);
    }
    
    /*
     * Utility method to extract array of ratings based on array of user ids.
     */
    public double[] getRatingsForItemList(Integer[] userIds) {
        double[] ratings = new double[userIds.length];
        for(int i = 0, n = userIds.length; i < n; i++) {
            Rating r = getUserRating(userIds[i]);
            if( r == null ) {
                throw new IllegalArgumentException(
                        "Item doesn't have rating by specified user id (" +
                        "userId=" + userIds[i] + ", itemId="+getId());
            }
            ratings[i] = r.getRating();
        }
        return ratings;
    }
    
}

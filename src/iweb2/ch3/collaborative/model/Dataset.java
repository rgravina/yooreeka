package iweb2.ch3.collaborative.model;


import java.util.Collection;

/**
 * Defines service that provides access to all users, items, and ratings. 
 * Recommender and similarity implementations rely on this service to access
 * data. 
 */
public interface Dataset {
    
    /**
     * Total number of all available item ratings.
     * 
     * @return number of item ratings by users.
     */
    public int getRatingsCount();

    /**
     * Total number of all available users.
     * 
     * @return number of users.
     */
    public int getUserCount();

    /**
     * Total number of all available items.
     * 
     * @return number of items.
     */
    public int getItemCount();

    /**
     * Retrieves all users.
     * 
     * @return collection of users. 
     */
    public Collection<User> getUsers();

    /**
     * Retrieves all items.
     *  
     * @return collection of all items.
     */
    public Collection<Item> getItems();

    /**
     * Retrieves a specific user.
     * 
     * @param userId user id.
     * @return user.
     */
    public User getUser(Integer userId);

    /**
     * Retrieves a specific item.
     * 
     * @param itemId item id.
     * @return item.
     */
    public Item getItem(Integer itemId);
    
    /**
     * Provides access to all ratings. 
     *  
     * @return collection of ratings.
     */
    public Collection<Rating> getRatings();
    
    /**
     * Provides the average rating for this item
     * @param itemId
     * @return
     */
    public double getAverageItemRating(int itemId);
    
    /**
     * Provides the average rating for this user
     * @param userId
     * @return
     */
    public double getAverageUserRating(int userId);
    
    /**
     * Logical name for the dataset instance.
     * 
     * @return name
     */
    public String getName();
    
    /**
     * Provides information about user and item ids returned by this dataset.
     * 
     * @return true if ids aren't in sequence and can't be used as array indexes.
     * false if user or items ids can be treated as sequences that start with 1. 
     * In this case index will be derived from id: index = id - 1.
     */
    public boolean isIdMappingRequired();
    
    /**
     * For content-based dataset returns array of terms that represent document
     * space.
     *   
     * @return
     */
    public String[] getAllTerms();
}

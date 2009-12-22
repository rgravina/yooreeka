package iweb2.ch3.collaborative.cache;

/**
 * A <code>Store</code> provides service for persisting pre-calculated data.
 */
public interface Store {
    /**
     * Persists object. Overwrites previously stored data with the same id.
     * 
     * @param key id to identify the object.
     * @param o object to be stored.
     */
    public void put(String key, Object o);
    
    
    /**
     * Retrieves object by key.
     * 
     * @param key identifies data to retrieve.
     * @return 
     */
    public Object get(String key);
    
    /**
     * Deletes object.
     * 
     * @param key identifies object to retrieve.
     */
    public void remove(String key);
    
    /** 
     * Checks if key already exists.
     * 
     * @param key object id.
     * @return true if the key already exists.
     */
    public boolean exists(String key);
}

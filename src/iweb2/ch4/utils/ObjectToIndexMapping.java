package iweb2.ch4.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps object values to an index. Index is zero-based.  
 */
public class ObjectToIndexMapping<T> implements java.io.Serializable {

    
    private static final long serialVersionUID = 2031098306406708902L;

    /*
     * Index value that will be returned for the next new value.
     */
    private int nextIndex = 0;
    
    /*
     * Maintains mapping from object to index.
     */
    private Map<T, Integer> objMapping = new HashMap<T, Integer>();
    
    /*
     * Maintains mapping from index to value.
     */
    private Map<Integer, T> indexMapping = new HashMap<Integer, T>();

    public ObjectToIndexMapping() {
        // empty
    }
    
    /**
     * Returns value mapped to the index or null if mapping doesn't exist. 
     */
    public T getObject(int index) {
        return indexMapping.get(index);
    }
    
    /**
     * Returns index assigned to the value. For new values new index will be assigned 
     * and returned.
     */
    public int getIndex(T value) {
        Integer index = objMapping.get(value); 
        if( index == null ) {
            index = nextIndex;
            objMapping.put(value, index);
            indexMapping.put(index, value);
            nextIndex++;
        }
        return index;
    }

    /**
     * Current number of elements. 
     */
    public int getSize() {
        return objMapping.size();
    }
}

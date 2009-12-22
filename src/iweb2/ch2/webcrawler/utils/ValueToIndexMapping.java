package iweb2.ch2.webcrawler.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps string values to an index. This class is used for mapping strings to 
 * arrays or matrices. Index is zero-based.   
 */
public class ValueToIndexMapping implements java.io.Serializable {
    /**
	 * Unique identifier for serialization
	 */
	private static final long serialVersionUID = -2077767183898369580L;

	/*
     * Index value that will be returned for the next new string value.
     */
    private int nextIndex = 0;
    
    /*
     * Maintains mapping from value to index.
     */
    private Map<String, Integer> valueMapping = new HashMap<String, Integer>();
    
    /*
     * Maintains mapping from index to value.
     */
    private Map<Integer, String> indexMapping = new HashMap<Integer, String>();

    public ValueToIndexMapping() {
        // empty
    }
    
    /**
     * Returns value mapped to the index or null if mapping doesn't exist. 
     */
    public String getValue(int index) {
        return indexMapping.get(index);
    }
    
    /**
     * Returns index assigned to the value. For new values new index will be assigned 
     * and returned.
     */
    public int getIndex(String value) {
        Integer index = valueMapping.get(value); 
        if( index == null ) {
            index = nextIndex;
            valueMapping.put(value, index);
            indexMapping.put(index, value);
            nextIndex++;
        }
        return index;
    }

    /**
     * Current number of elements. 
     */
    public int getSize() {
        return valueMapping.size();
    }
}

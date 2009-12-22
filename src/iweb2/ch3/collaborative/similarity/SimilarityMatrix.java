package iweb2.ch3.collaborative.similarity;

/**
 * Defines similarity matrix. For user-oriented methods it represents
 * similarities between users and for item-oriented methods this matrix
 * represents similarities between items.
 * 
 */
public interface SimilarityMatrix extends java.io.Serializable {

    /**
     * Returns matrix of similarities. For user-oriented methods it represents
     * similarities between users and for item-oriented methods the matrix
     * represents similarities between items.
     * 
     * @return similarity matrix
     */
    public abstract double[][] getSimilarityMatrix();

    /**
     * Returns similarity value between two objects identified by their IDs.
     * 
     * @param idX
     * @param idY
     * @return
     */
    public abstract double getValue(Integer idX, Integer idY);
    
    /**
     * Similarity matrix id. 
     * @return
     */
    public abstract String getId();
    
    public abstract RatingCountMatrix getRatingCountMatrix(Integer idX, Integer idY);
    
    public abstract boolean isRatingCountMatrixAvailable();
    
    public void print();
}
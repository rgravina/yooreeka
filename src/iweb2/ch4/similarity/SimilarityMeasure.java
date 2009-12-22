package iweb2.ch4.similarity;

/**
 * Interface for similarity measures.
 */
public interface SimilarityMeasure extends java.io.Serializable {

    /**
     * Calculates similarity value between two sets. Each set is represented by
     * array of strings. Arrays can have different length. 
     */
    public double similarity(String[] x, String[] y);
}

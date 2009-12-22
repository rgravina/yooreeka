package iweb2.ch4.similarity;

import iweb2.ch4.utils.TermFrequencyBuilder;

public class CosineSimilarity implements SimilarityMeasure {
    
	private static final long serialVersionUID = -3470234210362615980L;

	/**
     * Calculates cosine similarity between two sets of terms by converting them
     * into term frequency vectors.
     */
    public double similarity(String[] x, String[] y) {

        double[][] termFrequencyVectors = 
            TermFrequencyBuilder.buildTermFrequencyVectors(x, y);
        
        double[] termFrequencyForX = termFrequencyVectors[0];
        double[] termFrequencyForY = termFrequencyVectors[1];
        
        return sim(termFrequencyForX, termFrequencyForY);
    }

    public double sim(double[] v1, double[] v2) {
        double a = getDotProduct(v1, v2);
        double b = getNorm(v1) * getNorm(v2);
        return a / b;
    }
    
    
    private double getDotProduct(double[] v1, double[] v2) {
        double sum = 0.0;
        for(int i = 0, n = v1.length; i < n; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }
    
    private double getNorm(double[] v) {
        double sum = 0.0;
        for( int i = 0, n = v.length; i < n; i++) {
            sum += v[i] * v[i];
        }
        return Math.sqrt(sum);
    }
    
    
}

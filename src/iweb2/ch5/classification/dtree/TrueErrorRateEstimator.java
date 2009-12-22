package iweb2.ch5.classification.dtree;

/*
 * Estimates true error rate for tree pruning. Based on 
 * heuristic for C4.5. 
 */
public class TrueErrorRateEstimator {

    /*
     * Default value.
     */
    private double z = 0.69; // for confidence: 0.25 or 25%
    
    /**
     * Calculates true error rate for a node using error observed on
     * training data. C4.5 uses upper confidence limit for error rate to 
     * represent true error rate.
     *   
     * @param n total number of training samples at the node
     * @param e number of misclassified samples at the node
     * @return
     */
    public double errorRate(double n, double e) {
        /*
         * Observed error rate based on our training data.
         */
        double oe = e / n;
        
        /*
         * Calculating upper confidence limit to use an estimate of the error rate
         */ 
        double tmp1 = oe / n - (oe * oe) / n + (z * z) / ( 4 * n * n);
        double numerator = oe + (z * z) / (2 * n) + z * Math.sqrt(tmp1);
        double  denominator = 1 + (z * z) / n;
        
        return numerator /  denominator;
    }
}

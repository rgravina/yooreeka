package iweb2.ch6.boosting;

import java.util.Random;

public class WeightBasedRandom {

    private double[] w;

    private Random rnd;
    
    /**
     * Creates a new pseudorandom number generator. Distribution and range of 
     * numbers is defined by array of weights. 
     * 
     * @param w weights that define distribution. All weights should add up to 1.
     */
    public WeightBasedRandom(double[] w) {
        this.w = w;
        this.rnd = new Random();
    }
    
    /*
     * Returns next pseudorandom integer between 0 and w.length distributed
     * according to weights. 
     */
    public int nextInt() {

        /*
         * Pseudorandom, uniformly distributed double value between 0.0 and 1.0 
         */
        double x = rnd.nextDouble();
        
        double cdf = 0.0;
        
        int y = 0;
        
        for(int i = 0, n = w.length; i < n; i++) {
            cdf = cdf + w[i];
            y = i;
            if( cdf >= x ) {
                break;
            }
        }
        
        return y;
    }
    
}

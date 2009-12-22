package iweb2.ch4.clustering.rock;


/**
 * Goodness measure for merging two clusters.
 */
public class MergeGoodnessMeasure {

    /*
     * Threshold value that was used to identify neighbors among points. 
     */
    private double linkThreshold;
    
    /*
     * Intermediate value that is used in calculation of goodness measure 
     * and stays the same for different clusters.
     */
    private double p;
    
    public MergeGoodnessMeasure(double th) {
        this.linkThreshold = th;
        this.p = 1.0 + 2.0 * f(th);
    }

    public double g(int nLinks, int nX, int nY) {
        double a = Math.pow(nX + nY, p);
        double b = Math.pow(nX, p);
        double c = Math.pow(nY, p);
        
        return nLinks / (a - b - c); 
    }
    
    
    /**
     * This is just one of the possible implementations.
     * 
     * @param linkThreshold threshold value that was used to identify neighbors among points.    
     */
    private double f(double th) {

        /*
         * This implementation assumes that linkThreshold was a threshold for
         * similarity measure (as opposed to dissimilarity/distance). 
         */
        return (1.0 - th) / (1.0 + th);
    }

	/**
	 * @return the linkThreshold
	 */
	public double getTh() {
		return linkThreshold;
	}

	/**
	 * @param linkThreshold the linkThreshold to set
	 */
	public void setTh(double th) {
		this.linkThreshold = th;
	}
}

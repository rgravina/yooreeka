package iweb2.ch4.similarity;


public class EuclideanDistance implements Distance {

    public EuclideanDistance() {
        // empty
    }

    public double getDistance(double[] x, double[] y) {
        double sumXY2 = 0.0;
        for(int i = 0, n = x.length; i < n; i++) {
            sumXY2 += Math.pow(x[i] - y[i], 2);
        }
        return Math.sqrt(sumXY2);
    }
    
    public double getDistance(Double[] x, Double[] y) {
        double sumXY2 = 0.0;
        for(int i = 0, n = x.length; i < n; i++) {
            sumXY2 += Math.pow(x[i] - y[i], 2);
        }
        return Math.sqrt(sumXY2);
    }
    
}

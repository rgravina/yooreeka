package iweb2.ch4.similarity;

import java.util.Arrays;

public class CosineDistance implements Distance {

    private CosineSimilarity cosin = new CosineSimilarity();

    public double getDistance(double[] x, double[] y) {

        double sim = cosin.sim(x, y);

        if( sim < 0.0 ) {
            throw new RuntimeException(
                    "Can't use this value to calculate distance." +
                    "x[]=" + Arrays.toString(x) + 
                    ", y[]=" + Arrays.toString(y) +
                    ", cosin.sim(x,y)=" + sim);
        }

        return 1.0 - sim;
    }

}

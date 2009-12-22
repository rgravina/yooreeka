package iweb2.ch3.shell;

import iweb2.ch3.collaborative.evaluation.MovieLensRMSE;

/*
 * 
 */
public class MovieLensRMSESample {

    MovieLensRMSE movieLensRMSE;
    
    public MovieLensRMSESample(String datasetName) {
        movieLensRMSE = createMovieLensRMSE();
    }
        
    private MovieLensRMSE createMovieLensRMSE() {
        return new MovieLensRMSE();        
    }
    
    public void compareErrors() {
        
        // User based similarity will produce OutOfMemory because currently 
        // they don't discard rating count matrix after the calculation.

        double[] errors = movieLensRMSE.calculate();
        System.out.println("RMSE Errors:");
        
        for(int i=0; i<errors.length; i++) {
        	System.out.println("RMSE ["+i+"] = "+errors[i]);
        }
    }
    
    public static void main(String[] args) throws Exception {
        MovieLensRMSESample m = new MovieLensRMSESample("MovieLens");
        m.compareErrors();
    }
    
}

package iweb2.ch3.collaborative.evaluation;

import iweb2.ch3.collaborative.data.MovieLensDataset;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;
import iweb2.ch3.collaborative.model.User;
import iweb2.ch3.collaborative.recommender.Recommender;

import java.util.Collection;

/**
 * Calculates Root Mean Squared Error for the recommender. 
 */
public class RMSEEstimator {

    private boolean verbose = true;
    
    public RMSEEstimator() {
        // empty
    }
    
    /**
     * Calculates Root Mean Squared Error for the recommender. Uses 
     * test rating values returned by recommender's dataset.
     * 
     * @param delphi recommender.
     * @return root mean squared error value.
     */
    public double calculateRMSE(Recommender delphi) {
        
        MovieLensDataset ds = (MovieLensDataset)delphi.getDataset(); 
        Collection<Rating> testRatings = ds.getTestRatings();
        
        return calculateRMSE(delphi, testRatings);
    }

    /**
     * Calculates Root Mean Squared Error for the recommender. 
     * 
     * @param delphi recommender to evaluate.
     * @param testRatings ratings that will be used to calculate the error.  
     * @return root mean squared error.
     */
    public double calculateRMSE(Recommender delphi, Collection<Rating> testRatings) {
        
        double sum = 0.0;
        
        Dataset ds = delphi.getDataset();

        int totalSamples = testRatings.size();
        
        if( verbose ) {
            System.out.println("Calculating RMSE ...");
            System.out.println("Training ratings count: " + ds.getRatingsCount());
            System.out.println("Test ratings count: " + testRatings.size());
        }
        
        for(Rating r : testRatings ) {
            User user = ds.getUser(r.getUserId());
            Item item = ds.getItem(r.getItemId());
            double predictedItemRating = delphi.predictRating(user, item);

            if( predictedItemRating > 5.0 ) {
                predictedItemRating = 5.0;
                //System.out.println("Predicted item rating: " + predictedItemRating);
            }
//            if( verbose ) {
//                System.out.println(
//                        "user: " + r.getUserId() + 
//                        ", item: " + r.getItemId() +
//                        ", actual rating: " + r.getRating() + 
//                        ", predicted: " + String.valueOf(predictedItemRating));
//            }

            sum += Math.pow((predictedItemRating - r.getRating()), 2);
            
        }
        double rmse = Math.sqrt(sum / totalSamples); 

        if( verbose ) {
            System.out.println("RMSE:" + rmse);
        }
        return rmse;
        
    }
    
    public boolean isVerbose() {
        return verbose;
    }


    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}

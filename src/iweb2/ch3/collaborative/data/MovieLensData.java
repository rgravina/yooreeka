package iweb2.ch3.collaborative.data;

import iweb2.util.config.IWeb2Config;

import java.io.File;

/**
 * Utility class to create MovieLens dataset.
 */
public class MovieLensData {

    /**
     * Loads MovieLens dataset from default directory.
     */
    public static MovieLensDataset createDataset() {
        int numOfTestRatings = 0;
        return createDataset(numOfTestRatings);
    }

    public static MovieLensDataset createDataset(int numOfTestRatings) {
        return createDataset(IWeb2Config.getHome()+"/data/ch03/MovieLens", numOfTestRatings);
    }
    
    /**
     * Loads MovieLens dataset from specified directory.
     * 
     * @param dataDir directory that contains MovieLens files.
     * @return
     */
    public static MovieLensDataset createDataset(String dataDir, int numOfTestRatings) {
        File users = new File(dataDir, MovieLensDataset.USERS_FILENAME);
        File items = new File(dataDir, MovieLensDataset.ITEMS_FILENAME);
        File ratings = new File(dataDir, MovieLensDataset.RATINGS_FILENAME);
        
        System.out.println("*** Loading MovieLens dataset...");
        System.out.println("make sure that you are using at least: -Xmx1024m");
        
        MovieLensDataset dataSet = new MovieLensDataset(users, items, ratings, numOfTestRatings);

        System.out.println("\n*** Loaded MovieLens dataset.");
        System.out.println("users: " + dataSet.getUserCount());
        System.out.println("movies: " + dataSet.getItemCount());
        System.out.println("ratings: " + dataSet.getRatingsCount());
        System.out.println("test ratings: " + dataSet.getTestRatings().size());
        
        return dataSet;
    }
}

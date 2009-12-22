package iweb2.ch3.shell;

import iweb2.ch3.collaborative.data.MovieLensDataset;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;
import iweb2.ch3.collaborative.model.User;
import iweb2.util.config.IWeb2Config;
import iweb2.util.gui.XyGui;

import java.io.File;
import java.util.Collection;

public class RatingGrapher {
    
    /**
     * Plots average item rating for MovieLens dataset.
     */
    public static void plotAverageItemRating() {
        Dataset ds = getMovieLensData();
        Collection<Item> items = ds.getItems();
        double[] x = new double[items.size()];
        double[] y = new double[items.size()];
        int i = 0;
        for(Item item : items) {
            x[i] = item.getId();
            y[i] = item.getAverageRating();
            i++;
        }
        
        XyGui gui = new XyGui(ds.getName(), x, y);
        gui.plot();
    }
    
    /**
     * Plots average user rating for MovieLens dataset.
     */
    public static void plotAverageUserRating() {
        Dataset ds = getMovieLensData();
        Collection<User> users = ds.getUsers();
        double[] x = new double[users.size()];
        double[] y = new double[users.size()];
        int i = 0;
        for(User user : users) {
            x[i] = user.getId();
            y[i] = user.getAverageRating();
            i++;
        }
        
        XyGui gui = new XyGui(ds.getName(), x, y);
        gui.plot();
    }
    
    public static void plotNumberOfRatingsPerUser() {
        Dataset ds = getMovieLensData();
        Collection<User> users = ds.getUsers();
        double[] x = new double[users.size()];
        double[] y = new double[users.size()];
        int i = 0;
        for(User user : users) {
            x[i] = user.getId();
            y[i] = user.getAllRatings().size();
            i++;
        }
        
        XyGui gui = new XyGui(ds.getName(), x, y);
        gui.plot();
    }

    public static void plotNumberOfRatingsPerItem() {
        Dataset ds = getMovieLensData();        
        Collection<Item> items = ds.getItems();
        double[] x = new double[items.size()];
        double[] y = new double[items.size()];
        int i = 0;
        for(Item item : items) {
            x[i] = item.getId();
            y[i] = item.getAllRatings().size();
            i++;
        }
        
        XyGui gui = new XyGui(ds.getName(), x, y);
        gui.plot();
    }
    

    public static void plotRatingsDistribution() {
        Dataset ds = getMovieLensData();        
        plotRatingsDistribution("Ratings for all items by all users, n=" + ds.getRatingsCount(), ds.getRatings());
    }

    public static void plotRatingsDistributionForUser(int userId) {
        Dataset ds = getMovieLensData();        
        Collection<Rating> ratings = ds.getUser(userId).getAllRatings();
        plotRatingsDistribution("Ratings distribution for user: " + userId + ", n=" + ratings.size(), ratings);
    }

    public static void plotRatingsDistributionForItem(int itemId) {
        Dataset ds = getMovieLensData();        
        Collection<Rating> ratings = ds.getItem(itemId).getAllRatings();
        plotRatingsDistribution("Ratings distribution for item: " + itemId + ", n="+ ratings.size(), ratings);
    }
    
    private static void plotRatingsDistribution(String plotName, Collection<Rating> ratings) {
        double[] x = {1, 2, 3, 4, 5};
        double[] y = { 0.0, 0.0, 0.0, 0.0, 0.0 };
        
        if( ratings != null && ratings.size() > 0 ) {
            for(Rating r : ratings ) {
                y[r.getRating()-1]++;
            }
            
            int nRatings = ratings.size();
            for(int i = 0, n = x.length; i < n; i++) {
                y[i] = y[i] / nRatings;
            }
        }
        XyGui gui = new XyGui(plotName, x, y);
        gui.plot();
    }
    
    private static Dataset getMovieLensData() {
        String dataDir = IWeb2Config.getProperty(IWeb2Config.MOVIELENS_DATA_DIR);
        File users = new File(dataDir, MovieLensDataset.USERS_FILENAME);
        File items = new File(dataDir, MovieLensDataset.ITEMS_FILENAME);
        File ratings = new File(dataDir, MovieLensDataset.RATINGS_FILENAME);
        Dataset ds = new MovieLensDataset("MovieLensDataset", 
                users, items, ratings);
        return ds;
    }
    
    public static void main(String[] args) {
        // RatingGrapher.plotAverageItemRating();
        // RatingGrapher.plotAverageUserRating();
        RatingGrapher.plotRatingsDistribution();
        
    }
    
}

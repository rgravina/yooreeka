package iweb2.ch3.shell;

import iweb2.ch3.collaborative.data.MovieLensDataset;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.recommender.Delphi;
import iweb2.ch3.collaborative.recommender.PredictedItemRating;
import iweb2.ch3.collaborative.similarity.RecommendationType;

import java.io.File;
import java.util.List;

/**
 * @deprecated not used at the moment.
 */
@Deprecated
class Recommender {

//    private static final Logger logger = Logger.getLogger(Recommender.class);
    
    private Dataset dataset;
    
    private Recommender(String dataDir) {
        // Load MovieLens dataset
        File users = new File(dataDir, MovieLensDataset.USERS_FILENAME);
        File items = new File(dataDir, MovieLensDataset.ITEMS_FILENAME);
        File ratings = new File(dataDir, MovieLensDataset.RATINGS_FILENAME);
        this.dataset = new MovieLensDataset("MovieLensDataset", 
                users, items, ratings);
    }


    private void recommendOnMovieLens(boolean useSimilarityCache) throws Exception {
     
        long start = System.currentTimeMillis();
        Delphi delphi = new Delphi(dataset, 
                RecommendationType.ITEM_PENALTY_BASED, 
                useSimilarityCache);
        System.out.println("Time:" + (System.currentTimeMillis() - start)/1000 + "(sec)");
        List<PredictedItemRating> r = delphi.recommend(4);
        System.out.println("4: size: " + r.size() );
        printMinMax(r);
        printFirstN(r, 3);
        r = delphi.recommend(3);
        System.out.println("3: size: " + r.size() );
        printMinMax(r);
        printFirstN(r, 3);        
        r = delphi.recommend(100);
        System.out.println("100: size: " + r.size() );
        printMinMax(r);
        printFirstN(r, 3);        
        r = delphi.recommend(50);
        System.out.println("50: size: " + r.size() );
        printMinMax(r);
        printFirstN(r, 3);        
    }

    private void printFirstN(List<PredictedItemRating> sortedRecommendations, int printNum) {
        for(int i = 0, n = sortedRecommendations.size(); i < n && i < printNum; i++) {
            System.out.println(sortedRecommendations.get(i));
        }
    }
    
    private void printMinMax(List<PredictedItemRating> c) {
        int minId = 0;
        double minIdRating = 6.0;
        int maxId = 0;
        double maxIdRating = 0.0;
        for(PredictedItemRating r : c) {
            if( r.getRating() < minIdRating ) {
                minId = r.getItemId();
                minIdRating = r.getRating();
            }
            if( r.getRating() > maxIdRating) {
                maxId = r.getItemId();
                maxIdRating = r.getRating();
            }
        }
        System.out.println("minId="+minId + ",minIdRating="+maxIdRating +                
                ",maxId="+maxId+ ",maxIdRating="+maxIdRating);
    }
    
    public static void main(String[] args) throws Exception {
        Recommender m = new Recommender(args[0]);
        boolean useSimilarityCacheWhenAvailable = true;
        m.recommendOnMovieLens(useSimilarityCacheWhenAvailable);
    }
    
}

package iweb2.ch7.recommendation;

import iweb2.ch3.collaborative.model.User;
import iweb2.ch3.collaborative.recommender.Delphi;
import iweb2.ch3.collaborative.recommender.PredictedItemRating;
import iweb2.ch3.collaborative.recommender.Recommender;
import iweb2.ch3.collaborative.similarity.RecommendationType;
import iweb2.ch7.core.NewsDataset;
import iweb2.ch7.core.NewsStory;

import java.util.ArrayList;
import java.util.List;

public class StoryRecommender {

    private DatasetAdapter rDs;
    private Recommender delphi;
    
    public StoryRecommender(NewsDataset ds) {
        this.rDs = new DatasetAdapter(ds);
    }
    
    public void calculateRecommendations() {
        Delphi d = new Delphi(rDs, RecommendationType.ITEM_PENALTY_BASED, false);
        d.setVerbose(true);
        
        this.delphi = d;
    }
    
    public List<PredictedNewsStoryRating> recommendStories(String newsUserId) {

        if( delphi == null ) {
            String msg = "Recommender not initialized. You need calculate recommnedations first.";
            throw new RuntimeException(msg);
        }
        // Mapping news user to User used by recommender
        User user = rDs.getUserForNewsUserId(newsUserId);
        
        List<PredictedItemRating> predictedRatings = delphi.recommend(user);

        List<PredictedNewsStoryRating> ratings = 
            new ArrayList<PredictedNewsStoryRating>();
        
        for(PredictedItemRating iR : predictedRatings) {
            PredictedNewsStoryRating r = new PredictedNewsStoryRating();
            r.setUserId(newsUserId);
            r.setRating(iR.getRating());
            // map Item into NewsStory
            NewsStory newsStory = rDs.getNewsStoryForItemId(iR.getItemId());
            r.setStoryId(newsStory.getId());
            
            ratings.add(r);
        }
        
        return ratings;
    }
    
}

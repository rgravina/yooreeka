package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.User;

public class MovieLensUserSimilarity extends BaseSimilarityMatrix  {

    /**
     * SVUID
     */
    private static final long serialVersionUID = 8510536889333771002L;

    public MovieLensUserSimilarity(Dataset ds) {
        this(MovieLensUserSimilarity.class.getSimpleName(), ds);
    }
    public MovieLensUserSimilarity(String id, Dataset ds) {
        this.id = id;
        this.useObjIdToIndexMapping = ds.isIdMappingRequired();
        calculate(ds);
    }

    @Override
	protected void calculate(Dataset dataSet) {

        int nUsers = dataSet.getUserCount();
        
        similarityValues = new double[nUsers][nUsers];

        // if we want to use mapping from userId to index then generate 
        // index for every userId
        if( useObjIdToIndexMapping ) {
            for(User u : dataSet.getUsers() ) {
                idMapping.getIndex(String.valueOf(u.getId()));
            }
        }
        
        for (int u = 0; u < nUsers; u++ ) {

            int userAId = getObjIdFromIndex(u);
            User userA = dataSet.getUser(userAId);
            

            for (int v = u; v < nUsers; v++) {            
                int userBId = getObjIdFromIndex(v);
                User userB = dataSet.getUser(userBId);

                /* Collect shared ratings */
                Integer[] sharedItemIds = User.getSharedItems(userA, userB);

                if( sharedItemIds.length > 0 ) {
                    double[] ratingsA = userA.getRatingsForItemList(sharedItemIds);
                    double[] ratingsB = userB.getRatingsForItemList(sharedItemIds);
                    
                    /* Center ratings by subtracting average */
                    double avgA = userA.getAverageRating();
                    double avgB = userB.getAverageRating();
                    for(int i = 0; i < sharedItemIds.length; i++) {
                        ratingsA[i] = ratingsA[i] - avgA;
                        ratingsB[i] = ratingsB[i] - avgB;
                    }
                    
                    /* Calculate similarity - Pearson Correlation */
                    PearsonCorrelation pr = new PearsonCorrelation(ratingsA, ratingsB);
                    
                    similarityValues[u][v] = pr.calculate();
                }
                else {
                    similarityValues[u][v] = 0.0;
                }
            }
        }
    }
}

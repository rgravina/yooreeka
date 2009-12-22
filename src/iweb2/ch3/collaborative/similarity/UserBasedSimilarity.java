package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.User;

public class UserBasedSimilarity extends BaseSimilarityMatrix {

    /**
	 * Unique identifier for serialization
	 */
	private static final long serialVersionUID = 5741616253320567238L;
	
	public UserBasedSimilarity(Dataset dataSet) {
		
		this(UserBasedSimilarity.class.getSimpleName(), dataSet,true);
	}
	
    public UserBasedSimilarity(String id, Dataset dataSet, boolean keepRatingCountMatrix) {
        this.id = id;
        this.keepRatingCountMatrix = keepRatingCountMatrix;
        this.useObjIdToIndexMapping = dataSet.isIdMappingRequired();
        calculate(dataSet);
    }

    // here we assume that userId and bookId are:
    // - integers,
    // - start with 1
    // - have no gaps in sequence.
    // Otherwise we would have to have a mapping from userId/bookId into index
    @Override
	protected void calculate(Dataset dataSet) {

        int nUsers = dataSet.getUserCount();
        int nRatingValues = 5;
        
        similarityValues = new double[nUsers][nUsers];

        if( keepRatingCountMatrix ) {
            ratingCountMatrix = new RatingCountMatrix[nUsers][nUsers];
        }

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
            
            for (int v = u + 1; v < nUsers; v++) {

                int userBId = getObjIdFromIndex(v);
                User userB = dataSet.getUser(userBId);

                RatingCountMatrix rcm = new RatingCountMatrix(userA, userB, nRatingValues);
                
                int totalCount = rcm.getTotalCount();
                int agreementCount = rcm.getAgreementCount();
                
                if (agreementCount > 0) {

                    similarityValues[u][v] = (double) agreementCount / (double) totalCount;
                } else {
                    similarityValues[u][v] = 0.0;
                }
                
                // For large datasets
                if( keepRatingCountMatrix ) {
                    ratingCountMatrix[u][v] = rcm;
                }
            }

            // for u == v assign 1. 
            // RatingCountMatrix wasn't created for this case
            similarityValues[u][u] = 1.0;
        }
    }
}

package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Content;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.User;

/**
 * Similarity between users based on the content associated with users. 
 */
public class UserContentBasedSimilarity 
    extends BaseSimilarityMatrix {

    /**
	 * SVUID
	 */
	private static final long serialVersionUID = 5809078434246172835L;

	public UserContentBasedSimilarity(String id, Dataset ds) {
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

        CosineSimilarityMeasure cosineMeasure = new CosineSimilarityMeasure();
        String[] allTerms = dataSet.getAllTerms();
        
        for (int u = 0; u < nUsers; u++ ) {
            int userAId = getObjIdFromIndex(u);
            User userA = dataSet.getUser(userAId);
           
            for (int v = u + 1; v < nUsers; v++) {

                int userBId = getObjIdFromIndex(v);
                User userB = dataSet.getUser(userBId);
                
                double similarity = 0.0;
                
                for(Content userAContent : userA.getUserContent() ) {

                    double bestCosineSimValue = 0.0;
              
                    for(Content userBContent : userB.getUserContent() ) {
                        double cosineSimValue = cosineMeasure.calculate(
                                userAContent.getTermVector(allTerms), 
                                userBContent.getTermVector(allTerms));
                        bestCosineSimValue = Math.max(bestCosineSimValue, 
                                cosineSimValue);
                    }
                    
                    similarity += bestCosineSimValue;
                }
                //System.out.println("Similarity user[" + u + "][" + v + "]=" + similarity);
                similarityValues[u][v] = similarity / userA.getUserContent().size();
            }

            // for u == v assign 1. 
            similarityValues[u][u] = 1.0;
        }
    }
}

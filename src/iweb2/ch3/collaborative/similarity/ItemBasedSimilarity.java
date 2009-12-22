package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;

public class ItemBasedSimilarity extends BaseSimilarityMatrix {

    /**
     * 
     */
    private static final long serialVersionUID = 3062035062791168163L;
    
    public ItemBasedSimilarity(String id, Dataset dataSet, boolean keepRatingCountMatrix) {
        this.id = id;
        this.keepRatingCountMatrix = keepRatingCountMatrix;
        this.useObjIdToIndexMapping = dataSet.isIdMappingRequired();
        calculate(dataSet);
    }

    @Override
	protected void calculate(Dataset dataSet) {
  
        int nItems = dataSet.getItemCount();
        int nRatingValues = 5;
        
        similarityValues = new double[nItems][nItems];
        
        if( keepRatingCountMatrix ) {
            ratingCountMatrix = new RatingCountMatrix[nItems][nItems];
        }
        
        // if we want to use mapping from itemId to index then generate 
        // index for every itemId
        if( useObjIdToIndexMapping ) {
            for(Item item : dataSet.getItems() ) {
                idMapping.getIndex(String.valueOf(item.getId()));
            }
        }
        
        // By using these variables we reduce the number of method calls
        // inside the double loop.
        int totalCount=0;
        int agreementCount=0;
        
        for (int u = 0; u < nItems; u++) {
            
	    int itemAId = getObjIdFromIndex(u);
            Item itemA = dataSet.getItem(itemAId);
            
            // we only need to calculate elements above the main diagonal.
            for (int v = u + 1; v < nItems; v++) {
           
            	int itemBId = getObjIdFromIndex(v);
            	
                Item itemB = dataSet.getItem(itemBId);
                
                RatingCountMatrix rcm = new RatingCountMatrix(itemA, itemB, nRatingValues);
                
                totalCount     = rcm.getTotalCount();
                agreementCount = rcm.getAgreementCount();
                
                if (agreementCount > 0) {
                    similarityValues[u][v] = (double) agreementCount / (double) totalCount;
                } else {
                    similarityValues[u][v] = 0.0;
                }
                                
                if( keepRatingCountMatrix ) {
                    ratingCountMatrix[u][v] = rcm;
                }
            }

            // for u == v assign 1
            similarityValues[u][u] = 1.0;
            
        }
    }
}

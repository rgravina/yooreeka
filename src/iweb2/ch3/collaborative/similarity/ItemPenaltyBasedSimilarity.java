package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;

public class ItemPenaltyBasedSimilarity extends BaseSimilarityMatrix {

    /**
	 * Unique identifier for serialization
	 */
	private static final long serialVersionUID = -6137735175034641281L;

	public ItemPenaltyBasedSimilarity(Dataset dataSet) {
		
		this(ItemPenaltyBasedSimilarity.class.getSimpleName(), dataSet,true);
	}
	
	public ItemPenaltyBasedSimilarity(String id, Dataset dataSet, boolean keepRatingCountMatrix) {
        this.id = id;
        this.keepRatingCountMatrix = keepRatingCountMatrix;   
        this.useObjIdToIndexMapping = dataSet.isIdMappingRequired();        
        calculate(dataSet);
    }
    
    @Override
	protected void calculate(Dataset dataSet) {

        int nItems = dataSet.getItemCount();
        int nRatingValues = 5;
        
        /*
         * The penalties distort the scale that we use for similarities
         * maxBoundWeight is an auxiliary variable for scaling back to [0,1]
         */
        double scaleFactor = 0.0;
        
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
                
                if ( agreementCount > 0) {
                	
                    /*
                     * See ImprovedUserBasedSimilarity class for detailed
                     * explanation.
                     */
                    double weightedDisagreements = 0.0;
                    
                    int maxBandId = rcm.getMatrix().length - 1;
                    
                    for (int matrixBandId = 1; matrixBandId <= maxBandId; matrixBandId++) {
                    
                    	/*
                    	 * The following is a heuristic. Can you figure out what characteristics 
                    	 * are captured in such an expression? The numbers 1.8 and 0.4 are arbitrary,
                    	 * however, we could define them by solving an optimization problem.
                    	 * How would you formulate the problem? How would you solve it?
                    	 */
                    	double bandWeight = 1.8 - Math.exp(1-matrixBandId);
                        bandWeight = Math.pow(bandWeight,0.4);
                        
                        if (bandWeight > scaleFactor) {
                        	scaleFactor = bandWeight;
                        }
                        
                    	weightedDisagreements += bandWeight * rcm.getBandCount(matrixBandId);
                    }

                    double similarityValue = 1.0 - (weightedDisagreements / totalCount);

                    // w is the upper (negative) bound of the weighted similarity scale
                    double w = scaleFactor * (totalCount - agreementCount);
                                        
                    similarityValues[u][v] = (w + similarityValue) / (w + 1);
                    
                } else {
                    similarityValues[u][v] = 0.0;
                }
                
                if( keepRatingCountMatrix ) {
                    ratingCountMatrix[u][v] = rcm;
                }
            }

            // for u == v assign 1
            // ratingCountMatrix wasn't created for this case
            similarityValues[u][u] = 1.0;
            
        }
    }

}

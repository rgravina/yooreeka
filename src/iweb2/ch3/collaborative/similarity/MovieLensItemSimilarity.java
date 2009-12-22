package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;

public class MovieLensItemSimilarity extends BaseSimilarityMatrix  {

    /**
     * SVUID
     */
    private static final long serialVersionUID = 2571216412528879244L;

    public MovieLensItemSimilarity(Dataset ds) {
        this(MovieLensItemSimilarity.class.getSimpleName(), ds);
    }
    
    
    public MovieLensItemSimilarity(String id, Dataset ds) {
        this.id = id;
        this.useObjIdToIndexMapping = ds.isIdMappingRequired();
        calculate(ds);
    }

    @Override
	protected void calculate(Dataset dataSet) {
    	
        int nItems = dataSet.getItemCount();
        
        similarityValues = new double[nItems][nItems];

        // if we want to use mapping from itemId to index then generate 
        // index for every itemId
        if( useObjIdToIndexMapping ) {
            for(Item item : dataSet.getItems() ) {
                idMapping.getIndex(String.valueOf(item.getId()));
            }
        }
        
        PearsonCorrelation pC = null;
        
        for (int u = 0; u < nItems; u++) {
            
    	    int itemAId = getObjIdFromIndex(u);
            Item itemA = dataSet.getItem(itemAId);
                
            // we only need to calculate elements above the main diagonal.
            for (int v = u + 1; v < nItems; v++) {
               
            	int itemBId = getObjIdFromIndex(v);
            	Item itemB = dataSet.getItem(itemBId);
                
            	pC = new PearsonCorrelation(dataSet,itemA,itemB);
            	
            	similarityValues[u][v] = pC.calculate();
            }
            
            // for u == v assign 1
            similarityValues[u][u] = 1.0;
        }
    }
}

package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;

/**
 * Similarity between items based on the content associated with items. 
 */
public class ItemContentBasedSimilarity 
    extends BaseSimilarityMatrix {
    
    /**
	 * SVUID
	 */
	private static final long serialVersionUID = -2807190886025734879L;


	public ItemContentBasedSimilarity(String id, Dataset ds) {
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
        
        CosineSimilarityMeasure cosineMeasure = new CosineSimilarityMeasure();
        String[] allTerms = dataSet.getAllTerms();
        
        for (int u = 0; u < nItems; u++) {
            
            int itemAId = getObjIdFromIndex(u);
            Item itemA = dataSet.getItem(itemAId);
            
            // we only need to calculate elements above the main diagonal.
            for (int v = u + 1; v < nItems; v++) {
           
                int itemBId = getObjIdFromIndex(v);
                Item itemB = dataSet.getItem(itemBId);

                similarityValues[u][v] = cosineMeasure.calculate(
                        itemA.getItemContent().getTermVector(allTerms), 
                        itemB.getItemContent().getTermVector(allTerms));
            }

            // for u == v assign 1
            similarityValues[u][u] = 1.0;
            
        }
    }

}

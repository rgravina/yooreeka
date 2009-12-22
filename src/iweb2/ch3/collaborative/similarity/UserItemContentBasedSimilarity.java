package iweb2.ch3.collaborative.similarity;

import iweb2.ch2.webcrawler.utils.ValueToIndexMapping;
import iweb2.ch3.collaborative.model.Content;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.User;

/**
 * Similarity between users based on the content associated with users. 
 */
public class UserItemContentBasedSimilarity 
    extends BaseSimilarityMatrix {

	/**
     * SVUID
     */
    private static final long serialVersionUID = -372816966539384847L;

    private ValueToIndexMapping idMappingForUser = new ValueToIndexMapping();
    private ValueToIndexMapping idMappingForItem = new ValueToIndexMapping();
    
    public UserItemContentBasedSimilarity(String id, Dataset ds) {
        this.id = id;
        this.useObjIdToIndexMapping = ds.isIdMappingRequired();
        calculate(ds);        
    }
    
    @Override
    protected void calculate(Dataset dataSet) {

        int nUsers = dataSet.getUserCount();
        int nItems = dataSet.getItemCount();
        
        similarityValues = new double[nUsers][nItems];

        // if we want to use mapping from userId/itemId to matrix index 
        // then we need to generate index for every userId and itemId 
        if( useObjIdToIndexMapping ) {
            for(User u : dataSet.getUsers() ) {
                idMappingForUser.getIndex(String.valueOf(u.getId()));
            }
            
            for(Item i : dataSet.getItems() ) {
                idMappingForItem .getIndex(String.valueOf(i.getId()));
            }
        }

        CosineSimilarityMeasure cosineMeasure = new CosineSimilarityMeasure();
        String[] allTerms = dataSet.getAllTerms();
        
        for (int u = 0; u < nUsers; u++ ) {
            int userId = getUserIdForIndex(u);
            User user = dataSet.getUser(userId);
           
            for (int v = 0; v < nItems; v++) {

                int itemId = getItemIdFromIndex(v);
                Item item = dataSet.getItem(itemId);
                
                double simValue = 0.0;
                double bestCosineSimValue = 0.0;
                
                for(Content userContent : user.getUserContent() ) {
                	
                    simValue = cosineMeasure.calculate(
                            userContent.getTermVector(allTerms), 
                            item.getItemContent().getTermVector(allTerms));
                    bestCosineSimValue = Math.max(bestCosineSimValue, simValue);
                }
                
                similarityValues[u][v] = bestCosineSimValue;
            }
        }
    }

    /*
     * Utility method to convert userId into matrix index.
     */
    private int getIndexForUserId(Integer userId) {
        int index = 0;
        if( useObjIdToIndexMapping ) {
            index = idMappingForUser.getIndex(String.valueOf(userId));
        }
        else {
            index = userId - 1;
        }
        return index;
    }

    /*
     * Utility method to convert matrix index into userId
     */
    private Integer getUserIdForIndex(int index) {
        Integer objId;
        if( useObjIdToIndexMapping ) {
            objId = Integer.parseInt(idMappingForUser.getValue(index));
        }
        else {
            objId = index + 1;
        }
        return objId;
    }
    
    /*
     * Utility method to convert itemId into matrix index
     */
    private int getIndexForItemId(Integer itemId) {
        int index = 0;
        if( useObjIdToIndexMapping ) {
            index = idMappingForItem.getIndex(String.valueOf(itemId));
        }
        else {
            index = itemId - 1;
        }
        return index;
    }

    /*
     * Utility method to convert matrix index into itemId.
     */
    private Integer getItemIdFromIndex(int index) {
        Integer objId;
        if( useObjIdToIndexMapping ) {
            objId = Integer.parseInt(idMappingForItem.getValue(index));
        }
        else {
            objId = index + 1;
        }
        return objId;
    }
    
    @Override
    public double getValue(Integer userId, Integer itemId) {
        if (similarityValues == null) {
            throw new IllegalStateException(
                    "You have to calculate similarities first.");
        }

        int x = getIndexForUserId(userId);
        int y = getIndexForItemId(itemId);
        
        return similarityValues[x][y];
    }
    
    @Override
    protected int getIndexFromObjId(Integer objId) {
        throw new UnsupportedOperationException(
                "Should not be used. Use user or item specific method istead.");        
    }
    
    @Override
    protected Integer getObjIdFromIndex(int index) {
        throw new UnsupportedOperationException(
                "Should not be used.  Use user or item specific method istead.");
    }
    
}

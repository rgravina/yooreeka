package iweb2.ch3.collaborative.similarity;

import java.util.Arrays;

import iweb2.ch2.webcrawler.utils.ValueToIndexMapping;
import iweb2.ch3.collaborative.model.Dataset;

public abstract class BaseSimilarityMatrix implements SimilarityMatrix {

    protected String id;
    protected double similarityValues[][] = null;
    protected RatingCountMatrix ratingCountMatrix[][] = null;    
    protected boolean keepRatingCountMatrix = false; 
    
    protected boolean useObjIdToIndexMapping = true;
    protected ValueToIndexMapping idMapping = new ValueToIndexMapping();
    
    protected BaseSimilarityMatrix() {
    }
    
    public void setUseObjIdToIndexMapping(boolean value) {
        this.useObjIdToIndexMapping = value;
    }
    
    public boolean getUseObjIdToIndexMapping() {
        return useObjIdToIndexMapping;
    }

    public String getId() {
        return this.id;
    }
    
    public double[][] getSimilarityMatrix() {
        return similarityValues;
    }
    
    public RatingCountMatrix getRatingCountMatrix(Integer idX, Integer idY) {
        int x = getIndexFromObjId(idX);
        int y = getIndexFromObjId(idY);
        
        return ratingCountMatrix[x][y];
    }
    
    public boolean isRatingCountMatrixAvailable() {
        return keepRatingCountMatrix; 
    }
    protected abstract void calculate(Dataset dataSet);
    
    public double getValue(Integer idX, Integer idY) {
        if (similarityValues == null) {
            throw new IllegalStateException(
                    "You have to calculate similarities first.");
        }

        int x = getIndexFromObjId(idX);
        int y = getIndexFromObjId(idY);
        
        int i, j;
        if (x <= y) {
            i = x;
            j = y;
        } else {
            i = y;
            j = x;
        }
        return similarityValues[i][j];
    }
    
    /**
     * 
     * @param objId user or item id.
     * @return index that can be used to access the object in the matrix.
     */
    protected int getIndexFromObjId(Integer objId) {
        int index = 0;
        if( useObjIdToIndexMapping ) {
            index = idMapping.getIndex(String.valueOf(objId));
        }
        else {
            index = objId - 1;
        }
        return index;
    }
    
    protected Integer getObjIdFromIndex(int index) {
        Integer objId;
        if( useObjIdToIndexMapping ) {
            objId = Integer.parseInt(idMapping.getValue(index));
        }
        else {
            objId = index + 1;
        }
        return objId;
    }
    
    public void print() {
        if( similarityValues != null ) {
            for(double[] row : this.similarityValues) {
                System.out.println(Arrays.toString(row));
            }
        }
    }
}

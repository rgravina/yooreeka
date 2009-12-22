package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.util.config.IWeb2Config;

import java.io.File;

public class SimilarityMatrixRepository {

    SimilarityMatrixCache cache;
    
    public SimilarityMatrixRepository(boolean useCache) {
        if( useCache ) {
            String appTempDir = IWeb2Config.getProperty(IWeb2Config.TEMP_DIR);
            File cacheDir = 
                new File(appTempDir, "ch3/collaborative/SimilarityCache");
            cache = new SimilarityMatrixCache(cacheDir);
        }
        else {
            cache = null;
        }
    }
    
    public SimilarityMatrixRepository(SimilarityMatrixCache cache) {
        this.cache = cache;
    }
    
    
    public SimilarityMatrix load(RecommendationType type, Dataset data) {
        boolean keepRatingCountMatrix = true;
        return load(type, data, keepRatingCountMatrix);
    }

    
    public SimilarityMatrix load(RecommendationType type, Dataset data, boolean keepRatingCountMatrix) {
        SimilarityMatrix m = null;
        
        String id = getId(type, data.getName());
        // if cache is available then try to load from cache first
        if( cache != null ) {
            m = cache.get(id);
            if( m == null ) {
            	System.out.println("similarity matrix instance doesn't exist in cache: " +
                    "id: " + id + ", cache: '" + cache.getLocation() + "'.");
            }
            else {
            	System.out.println("similarity matrix instance was loaded from cache: " +
                        "id: " + id + ", cache: '" + cache.getLocation() + "'.");
            }
        }
        
        // create a new instance
        if( m == null ) {
            switch( type ) {
                case ITEM_BASED: 
                    m = new ItemBasedSimilarity(id, data, keepRatingCountMatrix);
                    break;
                case ITEM_PENALTY_BASED:
                    m = new ItemPenaltyBasedSimilarity(id, data, keepRatingCountMatrix);
                    break;
                case USER_BASED:
                    m = new UserBasedSimilarity(id, data, keepRatingCountMatrix);
                    break;
                case IMPROVED_USER_BASED:
                    m = new ImprovedUserBasedSimilarity(id, data, keepRatingCountMatrix);
                    break;
                case USER_CONTENT_BASED:
                    m = new UserContentBasedSimilarity(id, data);
                    break;
                case ITEM_CONTENT_BASED:
                    m = new ItemContentBasedSimilarity(id, data);
                    break;
                case USER_ITEM_CONTENT_BASED:
                    m = new UserItemContentBasedSimilarity(id, data);
                    break;
                default:
                    throw new IllegalArgumentException(
                         "Unsupported recommendation type: " + type.toString());
            }
            // store new instance in cache
            if( cache != null ) {
                cache.put(id, m);
            }
        }
        
        return m;
    }
    
    /**
     * Generates id for similarity matrix based on type and dataset name.
     * 
     * @param type
     * @param datasetName
     * @return
     */
    public static String getId(RecommendationType type, String datasetName) {
        String classname = null;
        switch( type ) {
            case ITEM_BASED: 
                classname =  ItemBasedSimilarity.class.getSimpleName();
                break;
            case ITEM_PENALTY_BASED:
                classname = ItemPenaltyBasedSimilarity.class.getSimpleName();
                break;
            case USER_BASED:
                classname = UserBasedSimilarity.class.getSimpleName();
                break;
            case IMPROVED_USER_BASED:
                classname = ImprovedUserBasedSimilarity.class.getSimpleName();
                break;
            case USER_CONTENT_BASED:
                classname = UserContentBasedSimilarity.class.getSimpleName();
                break;
            case ITEM_CONTENT_BASED:
                classname = ItemContentBasedSimilarity.class.getSimpleName();
                break;
            case USER_ITEM_CONTENT_BASED:
                classname = UserItemContentBasedSimilarity.class.getSimpleName();
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
        return classname + "-" + datasetName;
    }
    
}

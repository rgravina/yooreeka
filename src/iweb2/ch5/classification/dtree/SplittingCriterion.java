package iweb2.ch5.classification.dtree;

/**
 * Represents information about the split.
 */
public class SplittingCriterion {
    
    /* 
     * Attribute name to split on
     */
    private String splitAttributeName;
    
    /*
     * Only relevant for continuous attributes. Indicates value that will be 
     * used to decide true/false branch.
     */
    private Double splitPoint;
    
    /*
     * Data by branch. Each branch will have a subset of instances from the 
     * initial set that reached the node. We return it to avoid
     * calculating this data for every branch again.
     */
    private BranchGroup splitData;

    public String getSplitAttributeName() {
        return splitAttributeName;
    }

    public void setSplitAttributeName(String splitAttributeName) {
        this.splitAttributeName = splitAttributeName;
    }

    public Double getSplitPoint() {
        return splitPoint;
    }

    public void setSplitPoint(Double splitPoint) {
        this.splitPoint = splitPoint;
    }

    public BranchGroup getSplitData() {
        return splitData;
    }

    public void setSplitData(BranchGroup splitData) {
        this.splitData = splitData;
    }
    
    public boolean isDiscreteValueSplit() {
        return splitPoint == null;
    }
    
    public boolean isContinuousValueSplit() {
        return splitPoint != null;
    }
    
    /**
     * Returns branch name for continuous attributes.
     * 
     * @param attrValue attribute value that should be evaluated.
     * @param splitValue split point for continuous attributes.
     * 
     * @return name of the branch.
     */
    public static String getBranchName(Double attrValue, Double splitValue) {
        String branchName = null;
        
        if( attrValue <= splitValue ) {
            branchName = BranchGroup.BinaryBranchNames.TRUE_BRANCH;
        }
        else {
            branchName = BranchGroup.BinaryBranchNames.FALSE_BRANCH;
        }
        
        return branchName;
    }
    
    /**
     * Returns branch name for discrete attributes. Currently we always create
     * a separate branch for every discrete attribute. 
     * 
     * @param attrValue attribute value that should be evaluated.
     * 
     * @return name of the branch.
     */
    public static String getBranchName(String attrValue) {
        // Using attribute value as a branch name.
        return attrValue;
    }
    
}

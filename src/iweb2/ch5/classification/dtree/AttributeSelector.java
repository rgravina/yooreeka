package iweb2.ch5.classification.dtree;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Instance;

import java.util.List;

public class AttributeSelector implements java.io.Serializable {
    
    private static final long serialVersionUID = 1722498208605607524L;
    

    public AttributeSelector() {
        
    }
    
    /**
     * Evaluates all candidate attributes and chooses one that provides the
     * best split of the data.
     * 
     * @param data data that will be used to evaluate split quality.
     * @param candidateAttributes attributes to chose from.
     * 
     * @return information about selected attribute along with the data for 
     * every branch produced by this split. 
     */
    public SplittingCriterion apply(List<Instance> data, 
            List<AttributeDefinition> candidateAttributes) {

        
        int n = candidateAttributes.size();
        
        double bestGainRatio = Double.MIN_VALUE;
        
        SplittingCriterion splitCriterion = new SplittingCriterion();

        
        /* Calculate Gain Ratio for every available attribute. */
        for(int i = 0; i < n; i++) {
            AttributeDefinition attrDef = candidateAttributes.get(i);
            String attrName = attrDef.getName();
            Double splitPoint = null;

            BranchGroup branches = null;            
            
            if(attrDef.isDiscrete()) {
                /* 
                 * For discrete attribute we split all data into subsets 
                 * based on attribute values.
                 */
                branches = BranchGroup.createBranchesFromDiscreteAttr(data, attrName);
            }
            else {
                /*
                 * For continuous attribute we pick a value that is in the middle
                 * of min and max attribute values that are present in the data.
                 */
                splitPoint = pickSplitPoint(data, attrName);

                /*
                 * All data will be split into two groups: 
                 *  group with values x <= splitPoint and
                 *  group with values x > splitPoint  
                 */
                branches = BranchGroup.createBranchesFromContiniuousAttr(data, attrName, splitPoint);
            }
        
            // Only consider attributes that split the data into more than one
            // branch
            if( branches.getBranches().size() > 1 ) {
                Double gainRatio = calculateGainRatio(data, branches);
                
                if( gainRatio > bestGainRatio ) {
                    bestGainRatio = gainRatio;
                    splitCriterion.setSplitAttributeName(attrName);
                    splitCriterion.setSplitPoint(splitPoint);
                    splitCriterion.setSplitData(branches);
                }
            }
        }
        
        return splitCriterion;
    }
    
  private Double calculateGainRatio(List<Instance> allData, BranchGroup branches) {
      
      List<List<Instance>> dataByBranch = branches.getData(); 

      InfoGain infoGain = new InfoGain();      

      return infoGain.gainRatio(allData, dataByBranch);
  }
    
  /*
   * Calculates a value to split on for continuous valued attributes.
   */  
  private Double pickSplitPoint(List<Instance> data, String attrName) {
      Double minValue = Double.MAX_VALUE;
      Double maxValue = Double.MIN_VALUE;
      
      for(Instance i : data) {
          Attribute a = i.getAttributeByName(attrName); 
          Double value = AttributeUtils.toDouble(a.getValue());
          if( value != null && value < minValue ) {
              minValue = value;
          }
          if( value != null && value > maxValue ) {
              maxValue = value;
          }
      }
      
      return (maxValue - minValue) / 2.0;
  }
}

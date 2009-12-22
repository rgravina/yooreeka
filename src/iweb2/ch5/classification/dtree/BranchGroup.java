package iweb2.ch5.classification.dtree;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchGroup {
    private String name;
    private Map<String, Branch> branches;
    

    public BranchGroup(String name) {
        this.name = name;
        branches = new HashMap<String, Branch>();
    }

    
    public String getName() {
        return name;
    }

    public List<List<Instance>> getData() {
        List<List<Instance>> allData = new ArrayList<List<Instance>>();
        
        for(Branch b : branches.values()) {
            List<Instance> branchData = b.getData();
            allData.add(branchData);
        }
        
        
        return allData;
    }
    public void add(String branchName, Instance i) {
        
        Branch branch = branches.get(branchName);          
        if( branch == null ) {
            branch = new Branch(branchName);
            branches.put(branchName, branch);
        }
        
        branch.add(i);          
    }
    
    public Branch getBranch(String branchName) {
        return branches.get(branchName);
    }
    
    public List<Branch> getBranches() {
        return new ArrayList<Branch>(branches.values());
    }
 
    /**
     * Value that is used to identify data subset when the split is done
     * on continuous value.
     */
    public static class BinaryBranchNames {
        public static final String TRUE_BRANCH = "true";
        public static final String FALSE_BRANCH = "false";
        private BinaryBranchNames() {}
    };
    
    public static BranchGroup createBranchesFromDiscreteAttr(
            List<Instance> data, String attrName) {
        
        // Separate branch for each attribute value
        BranchGroup branches = new BranchGroup(attrName);
        
        for(Instance i : data) {
            Attribute a = i.getAttributeByName(attrName);
            String attrValue = AttributeUtils.toString(a.getValue());
            String branchName = SplittingCriterion.getBranchName(attrValue); 

            branches.add(branchName, i);          
        }
        
        return branches;
    }
    
    
    public static BranchGroup createBranchesFromContiniuousAttr(
            List<Instance> data, String attrName, Double splitPoint) {

        BranchGroup branches = new BranchGroup(attrName);

        for(Instance i : data) {
            Attribute a = i.getAttributeByName(attrName);
            Double value = AttributeUtils.toDouble(a.getValue());
            String branchName = SplittingCriterion.getBranchName(value, splitPoint);

            branches.add(branchName, i);          
        }
        
        return branches;        
    }
    
    
}

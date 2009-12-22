package iweb2.ch5.classification.dtree;

import iweb2.ch5.ontology.intf.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Branch {
    private String branchName;
    private List<Instance> data;
    
    public Branch() {
        init(null);
    }

    public Branch(String name) {
        init(name);
    }
    
    private void init(String name) {
        branchName = name;
        data = new ArrayList<Instance>();
    }
    
    public String getName() {
        return branchName;
    }

    public void setName(String name) {
        this.branchName = name;
    }

    public List<Instance> getData() {
        return data;
    }

    public void setData(List<Instance> data) {
        this.data = data;
    }
    
    public void add(Instance instance) {
        this.data.add(instance);
    }
    
    public void add(List<Instance> multipleInstances) {
        this.data.addAll(multipleInstances);
    }
    
    public static void addInstance(
            Map<String, Branch> branches, String branchName, Instance i) {
        
        Branch branch = branches.get(branchName);          
        if( branch == null ) {
            branch = new Branch(branchName);
            branches.put(branchName, branch);
        }
        
        branch.add(i);          
    }
    
    
}

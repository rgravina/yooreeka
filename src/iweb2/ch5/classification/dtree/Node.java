package iweb2.ch5.classification.dtree;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Decision tree node.
 */
class Node implements java.io.Serializable {
    
    private static final long serialVersionUID = -4282027910521283908L;

    /**
     * Auxiliary variable for printing
     */
    private boolean isVerbose=false;
    
    /*
     * Instance attribute name that this node will use to choose the branch. 
     */
    private String attributeName;
    
    /*
     * Map of child nodes keyed by branch name. Branch name depends on type
     * of attribute. For discrete attribute actual attribute value will be used
     * and for continuous-valued attributes we only have two branches that split
     * all values into two subsets.
     */
    private Map<String, Node> childNodesByBranchName;
    
    /*
     * Indicates that this node is a leaf node.
     */
    private boolean isLeaf;
    
    /*
     * Predicted class (concept) name.
     */
    private String conceptName;

    /*
     * Split value that will be used to determine child node when 
     * evaluating continuous-valued attribute.
     */
    private Double splitValue;
    
    /*
     * This value will be used as a result of classification whenever 
     * non-leaf node can not choose next tree node. This can happen when
     * there are no available attributes anymore but the node is not a leaf node.
     */
    private String mostFrequentConceptName;
    
    /*
     * Number of training instances that reached this node. Will only be used
     * by pruning during the training phase.
     */
    private transient List<Instance> nodeTrainingData;
    
    public Node() {
        childNodesByBranchName = new HashMap<String, Node>();
    }

    
    public String getMostFrequentConceptName() {
        return mostFrequentConceptName;
    }


    public void setMostFrequentConceptName(String mostFrequentConceptName) {
        this.mostFrequentConceptName = mostFrequentConceptName;
    }


    public String getAttributeName() {
        return attributeName;
    }


    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }


    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    
    /**
     * Returns next node from the tree that fits provided instance.
     * 
     * @param t instance that we are trying to classify.
     * 
     * @return next tree node or null.
     */
    public Node selectChild(Instance t) {
       
        Node child = null;

        Attribute a = t.getAttributeByName(attributeName);
        
        if( a != null ) {
        
            String branchName = null;
            
            if( splitValue != null ) {
                Double attrValue = AttributeUtils.toDouble(a.getValue());
                branchName = SplittingCriterion.getBranchName(attrValue, splitValue);
            }
            else {
                String attrValue = AttributeUtils.toString(a.getValue());
                branchName = SplittingCriterion.getBranchName(attrValue);                
            }
            child = childNodesByBranchName.get(branchName);            
        }  
        
        // can be null if instance attribute is missing or has value that we
        // haven't seen during training (for discrete attributes)
        
        return child; 
    }
    
    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }
    
    public void addChild(String value, Node node) {
        this.childNodesByBranchName.put(value, node);
    }
    
    public Double getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(Double splitValue) {
        this.splitValue = splitValue;
    }
    
    
    
    public List<Instance> getNodeTrainingData() {
        return nodeTrainingData;
    }


    public void setNodeTrainingData(List<Instance> nodeTrainingData) {
        this.nodeTrainingData = nodeTrainingData;
    }


    public void print(int level) {
        
        String padding = StringUtils.leftPad("", level * 5);
        
        String nodeInfo = "Node:" + 
        "attrName=" + this.attributeName + 
        ",isLeaf=" + this.isLeaf +
        ",concept=" + this.conceptName;

        System.out.println(padding + nodeInfo);
        for( Map.Entry<String, Node> e : childNodesByBranchName.entrySet() ) {
            if( splitValue == null ) {
                System.out.println(padding + 
                        "-> Branch: [" + attributeName + "=" + e.getKey() + "]" );
            }
            else {
                String condition;
                if( BranchGroup.BinaryBranchNames.TRUE_BRANCH.equalsIgnoreCase(e.getKey()) ) {
                    condition = "<=";
                }
                else {
                    condition = ">";
                }
                System.out.println(padding + 
                        "-> Branch: " + e.getKey() + 
                        " [" + attributeName + condition + 
                        this.splitValue + "]" );
                
            }
            e.getValue().print(level + 1);
        }
    }
    
    public String classify(Instance i) {
        Node subtree = this;
        
        while( subtree.isLeaf() == false ) {
            Node childNode = subtree.selectChild(i);
    
            if( childNode == null ) {
                // Decision tree couldn't choose next child
                break;                
            }
            
            subtree = childNode;
        }
        
        String category = null;
        
        if( subtree.isLeaf() ) {
            category = subtree.getConceptName();
        }
        else {
            category = subtree.getMostFrequentConceptName();
        }
        
        return category; 
    }
    
    public void prune() {

        if( isLeaf ) {
            return;
        }
        
        /*
         * First prune all child nodes (child subtrees).
         */
        for( Node childNode : childNodesByBranchName.values() ) {
            childNode.prune();
        }

        // find most popular subtree
        Node mostPopularSubtree = selectMostFrequentSubtree();
        
        /*
         * Evaluate current node (subtree)
         */

        double leafErrorRate = 0.0;
        double nodeErrorRate = 0.0;
        double mostPopularSubtreeErrorRate = 0.0;
        
        /*
         * Estimate error rate for the case when we use the most frequent  
         * concept from the node training set. 
         */  
        leafErrorRate = estimateLeafErrorRate();

        /*
         * Estimate error rate using current tree
         */         
        nodeErrorRate = estimateTreeErrorRate(this, nodeTrainingData);
        
        /*
         * Estimate error rate for most popular subtree
         */
        mostPopularSubtreeErrorRate = 
                estimateTreeErrorRate(mostPopularSubtree, nodeTrainingData);

        if ( isVerbose ) {
	        System.out.printf("Pruning: " + this.attributeName + 
	                ", tree error rate: %.5f" +
	                ", subtree error rate: %.5f" + 
	                ", leaf error rate: %.5f\n", 
	                nodeErrorRate, 
	                mostPopularSubtreeErrorRate, 
	                leafErrorRate);
        }
        
        if( nodeErrorRate >= leafErrorRate || 
            nodeErrorRate >= mostPopularSubtreeErrorRate    ) {

            // We can get better error rate after pruning
            
            if( leafErrorRate <= mostPopularSubtreeErrorRate) {

            	if ( isVerbose ) {
            		System.out.println("Replacing current node with leaf node");
            	}

                // replace current node with leaf node.
                this.setLeaf(true);
                this.childNodesByBranchName.clear();
                this.conceptName = this.mostFrequentConceptName;
                this.splitValue = null;

            } else {

            	if ( isVerbose ) {
            		System.out.println("Replacing current node with subtree");
            	}
                
                // replace current node with subtree
                this.childNodesByBranchName.clear();
                this.attributeName = mostPopularSubtree.getAttributeName();
                this.isLeaf = mostPopularSubtree.isLeaf();
                this.childNodesByBranchName = mostPopularSubtree.childNodesByBranchName;
                this.conceptName = mostPopularSubtree.conceptName;
                this.splitValue = mostPopularSubtree.splitValue;
                // Note: we are keeping current training data of the node and 
                // most frequent concept name that is based on training data.
            }
        }
        
    }
    
    private double estimateTreeErrorRate(Node subtree, List<Instance> data) {

        /*
         * Calculate observed error rate (error rate based on our training data)
         * if we use most frequent category as classification result of this node.
         */
        int n = data.size();
        int e = 0;
        for(Instance i : data) {
            String category = subtree.classify(i);
            if( !category.equals(i.getConcept().getName() ) ) {
                e++;
            }
        }
        
        return estimateErrorRate(n, e);
    }
    
    private double estimateLeafErrorRate() {

        /*
         * Calculate observed error rate (error rate based on our training data)
         * if we use most frequent category as classification result of this node.
         */
        int n = nodeTrainingData.size();
        int e = 0;
        for(Instance i : nodeTrainingData) {
            if( !mostFrequentConceptName.equalsIgnoreCase(
                    i.getConcept().getName()) ) {
                e++;
            }
        }
        
        return estimateErrorRate(n, e);
    }
    
    private double estimateErrorRate(int n, int e) {
        TrueErrorRateEstimator ter = new TrueErrorRateEstimator();
        /* Using default confidence range: 25% (z = 0.69) */
        return ter.errorRate(n, e);
    }
    
    /**
     * Selects child node (subtree) that is most frequent outcome of the 
     * current node (has the most training samples).
     */
    private Node selectMostFrequentSubtree() {
        Node selectedNode = null;
        int maxTrainingSamples = 0;
        for( Node childNode : childNodesByBranchName.values() ) {
            if( childNode.getNodeTrainingData() != null ) {
                int n = childNode.getNodeTrainingData().size();
                if( n > maxTrainingSamples) {
                    selectedNode = childNode;
                    maxTrainingSamples = n;
                }
            }
        }

        return selectedNode;
    }


	/**
	 * @return the isVerbose
	 */
	public boolean isVerbose() {
		return isVerbose;
	}


	/**
	 * @param isVerbose the isVerbose to set
	 */
	public void setVerbose(boolean isVerbose) {
		this.isVerbose = isVerbose;
	}
    
}


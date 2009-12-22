package iweb2.ch5.classification.dtree;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.classification.core.intf.Classifier;
import iweb2.ch5.ontology.core.BaseConcept;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DecisionTreeClassifier implements Classifier, java.io.Serializable {

    private static final long serialVersionUID = -3360341002492465102L;

    private String name;
    
    protected boolean verbose = false;
        
    /*
     * Selects best attribute to split on.
     */
    private AttributeSelector attributeSelector;

    protected Node rootTreeNode;

    /*
     * No need to keep training data for serialization.
     */
    private transient TrainingSet trainingData;

    /*
     * Attributes that should be considered for tree training.
     */
    private List<AttributeDefinition> availableAttributes;
    
    public DecisionTreeClassifier(TrainingSet trainingData) {
        this(DecisionTreeClassifier.class.getSimpleName(), trainingData);
    }
    
    public DecisionTreeClassifier(String name, TrainingSet trainingData) {
        this.name = name;
        rootTreeNode = null;
        attributeSelector = new AttributeSelector();
        this.trainingData = trainingData;
        this.availableAttributes = new ArrayList<AttributeDefinition>();
    }
    
    public void trainOnAttribute(String name, boolean isDiscrete) {
        AttributeDefinition attrDef = new AttributeDefinition(name, isDiscrete);
        availableAttributes.add(attrDef);
    }
    
    public boolean train() {

    	long t0 = System.currentTimeMillis();
    	
        HashMap<Integer, Instance> instances = trainingData.getInstances();
        ArrayList<Instance> trainingInstances = 
            new ArrayList<Instance>(instances.values());
        
        rootTreeNode = buildTree(trainingInstances, availableAttributes);
        
        if (verbose) {
        	System.out.print("       Decision tree training completed in ");
            System.out.println((System.currentTimeMillis()-t0)+" (ms)");
        }
        
        return true;
    }
    
    public Concept classify(Instance i) {
        
        String category = rootTreeNode.classify(i);
        return createConcept(category); 
        
    }
    
    /*
     * Allows suclasses to provide specific implementation of the concept.
     */
    protected Concept createConcept(String category)  {
        return new BaseConcept(category);
    }
    
    /**
     * Builds subtree using provided data and attributes.
     * 
     * @param data training instances that should be considered for subtree.
     * @param candidateAttributes available attributes.
     */
    private Node buildTree(
            List<Instance> data, 
            List<AttributeDefinition> candidateAttributes) {
        
        /*
         * Node that will represent the subtree.
         */
        Node node = new Node();

        String[] concepts = ConceptUtils.getUniqueConcepts(data);
        String mostFrequentConcept = ConceptUtils.findMostFrequentConcept(data);
        node.setMostFrequentConceptName(mostFrequentConcept);
        node.setNodeTrainingData(data);
        
        /*
         * No need to split if there is only on concept left. 
         */
        if( concepts.length == 1 ) {
            node.setLeaf(true);
            node.setConceptName(concepts[0]);
            node.setAttributeName(null);
            return node;
        }
        
        /*
         * We've run out of attributes to split on. Just use the most frequent
         * concept.
         */
        if( candidateAttributes == null || candidateAttributes.size() == 0 ) {
            node.setLeaf(true);
            node.setConceptName(mostFrequentConcept);
            node.setAttributeName(null);
            return node;
        }

        /*
         * Determines the best attribute to split on. 
         */
        SplittingCriterion bestSplitCriterion = 
            attributeSelector.apply(data, candidateAttributes);

        if( bestSplitCriterion == null || bestSplitCriterion.getSplitAttributeName() == null) {
            node.setLeaf(true);
            node.setConceptName(concepts[0]); // pick first concept from the list
            node.setAttributeName(null);
            return node;
        }
        
        /*
         * For non-leaf nodes we don't have the class label.
         */ 
        node.setConceptName(null);
        node.setAttributeName(bestSplitCriterion.getSplitAttributeName());
        node.setSplitValue(bestSplitCriterion.getSplitPoint());
        

        if( bestSplitCriterion.isDiscreteValueSplit() ) {
            // Split on discrete attribute value            
            BranchGroup branches = bestSplitCriterion.getSplitData();
            for( Branch branch : branches.getBranches() ) {
                
                List<Instance> selectedData = branch.getData();

                // build a list of attributes for child node
                List<AttributeDefinition> childNodeAttrs = 
                    AttributeDefinition.copyAttributeDefs(candidateAttributes);
                // remove current attribute from consideration
                AttributeDefinition.removeAttributeDef(
                        bestSplitCriterion.getSplitAttributeName(), childNodeAttrs);
                
                Node childNode = buildTree(selectedData, childNodeAttrs);
                
                node.addChild(branch.getName(), childNode);
            }
        }
        else {
            // split on continuous-valued attribute
            BranchGroup branches = bestSplitCriterion.getSplitData();
            for(Branch branch : branches.getBranches()) {

                List<AttributeDefinition> childNodeAttrs = 
                   AttributeDefinition.copyAttributeDefs(candidateAttributes);
                
                Node childNode = buildTree(branch.getData(), childNodeAttrs);
                node.addChild(branch.getName(), childNode);
            }
        }
        
        return node;
    }
    
    /**
     * Prints information about tree.
     */
    public void printTree() {
        System.out.println("--- Tree ---");
        rootTreeNode.print(0);
        System.out.println("------------");
    }
    
    
    public void pruneTree() {
        this.rootTreeNode.prune();
    }
    
    public String getName() {
        return name;
    }

	/**
	 * @return the verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * @param verbose the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
    
}

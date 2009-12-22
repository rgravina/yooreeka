package iweb2.ch5.classification.core;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class TrainingSet implements Serializable {

	/**
	 * A unique ID, just in case that we want to serialize our training instanceSet.
	 */
	private static final long serialVersionUID = 4754213130190809633L;

	private boolean verbose=false;
	
/** 
 * TODO: 5.x -- Training set management (Book Section 2.4.1 and 5.7)
 * 
 * For large training sets, it may be beneficial to serialize them and store them
 * because loading a large training instanceSet is computationally expensive.
 * 
 * How would you go about merging two training sets? What problems do you foresee?
 */	
	private HashMap<Integer,Instance> instanceSet;
	private HashSet<Concept> conceptSet;
	private HashSet<String> attributeNameSet;
	
	public TrainingSet() {
		
		instanceSet = new HashMap<Integer,Instance>();
	}
	
	public TrainingSet(Instance[] instances) {
		
		int instanceId =0;
		
		instanceSet = new HashMap<Integer,Instance>();
		conceptSet  = new HashSet<Concept>();
		attributeNameSet = new HashSet<String>();
		
		Concept c;
		for (Instance i : instances) {

//			System.out.println("Instance Added: ");
//			i.print();
			
			instanceSet.put(instanceId, i);
			
			c = i.getConcept();
			if ( !conceptSet.contains(c) ) {
				
				conceptSet.add(c);
			}
			
			for(Attribute a : i.getAtrributes()) {
			    if( a != null ) {
			        attributeNameSet.add(a.getName());
			    }
			}
			
			instanceId++;
		}
		
		if (verbose) {
			System.out.println("-------------------------------------------------------------");
			System.out.print("Loaded "+getSize()+" instances that belong into ");
			System.out.println(this.getNumberOfConcepts()+" concepts");
			System.out.println("-------------------------------------------------------------");			
		}
	}
	
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return the instanceSet
	 */
	public HashMap<Integer,Instance> getInstances() {
		return instanceSet;
	}

	public Instance getInstance(int index) {
	    return instanceSet.get(index);
	}
	
	/**
	 * @return the size of the instanceSet
	 */
	public int getSize() {
		return instanceSet.size();
	}

	public int getNumberOfConcepts() {
		return conceptSet.size();
	}
	
	public void print() {
		
		for (Instance i : instanceSet.values()) {
			i.print();
		}
	}

	/**
	 * @return the conceptSet
	 */
	public HashSet<Concept> getConceptSet() {
		return conceptSet;
	}
	
	public HashSet<String> getAttributeNameSet() {
	    return attributeNameSet;
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

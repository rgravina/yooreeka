package iweb2.ch5.classification.bayes;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.classification.core.intf.Classifier;
import iweb2.ch5.ontology.core.AttributeValue;
import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of the Naive Bayes algorithm.
 * 
 * The emphasis is on teaching the algorithm, not optimizing its performance.
 * 
 * @author babis
 */
public class NaiveBayes implements Classifier {

	/**
	 * You can use the NaiveBayes classifier in many occasions
	 * So, let's give it a name to identify the instance of the Classifier.
	 */
	private String name;
	
	/**
	 * Every classifier needs a training set. Notice that both the name 
	 * of the classifier and its training set are intentionally set during 
	 * the Construction phase.
	 * 
	 * Once you created an instance of the NaiveBayes classifier you cannot
	 * set its TrainingSet but you can always get the reference to it and 
	 * add instances.
	 */
	protected TrainingSet tSet;
	
	/**
	 * These are the probabilities for each concept
	 */
	protected Map<Concept,Double> conceptPriors;
	
	/**
	 * This structure contains the fundamental calculation elements of 
	 * the Naive Bayes method, i.e. the conditional probabilities.
	 */
	protected Map<Concept,Map<Attribute, AttributeValue>> p;
	
	/**
	 * These are the attribute indices that we should consider for training
	 */
	protected ArrayList<String> attributeList;
	
	/** An auxiliary variable */
	protected boolean verbose = false;
	
	/**
	 * The only constructor for this classifier takes a name and 
	 * a training set as arguments.
	 * 
	 * @param name the name of the classifier
	 * @param set the training set for this classifier
	 */
	public NaiveBayes(String name, TrainingSet set) {
		
		this.name = name;
		tSet = set;
		
		conceptPriors = new HashMap<Concept,Double>(tSet.getNumberOfConcepts());
		verbose = false;
	}

	public Concept classify(Instance instance) {
	    
	    Concept bestConcept = null;
	    double bestP = 0.0;
	    
	    if( tSet == null || tSet.getConceptSet().size() == 0) {
	        throw new RuntimeException("You have to train classifier first.");
	    }
	    if( verbose ) {
	        System.out.println("\n*** Classifying instance: " + instance.toString() + "\n");
	    }
	    for (Concept c : tSet.getConceptSet()) {
	        double p = getProbability(c, instance);
	        if( verbose ) {
	            System.out.printf("P(%s|%s) = %.15f\n", c.getName(), instance.toString(), p);
	        }
	        if( p >= bestP ) {
	            bestConcept = c;
	            bestP = p;
	        }
	    }
	    return bestConcept;
	}

	/**
	 * Training simply sets the probability for each concept
	 * 
	 */
	public boolean train() {
		
		long t0 = System.currentTimeMillis();
		
		boolean hasTrained = false;
		
		if ( attributeList == null || attributeList.size() == 0) {

			System.out.print("Can't train the classifier without specifying the attributes for training!");
			System.out.print("Use the method --> trainOnAttribute(Attribute a)");
			
		} else {
			
			calculateConceptPriors();
			
			calculateConditionalProbabilities();
			
			hasTrained = true;
		}

		if (verbose) {
			System.out.print("       Naive Bayes training completed in ");
	        System.out.println((System.currentTimeMillis()-t0)+" (ms)");
		}

		return hasTrained;
	}

	public void trainOnAttribute(String aName) {
		
		if (attributeList ==null) {
			attributeList = new ArrayList<String>();
		}
		
		attributeList.add(aName);
	}
	
	/**
	 * Strictly speaking these are not the prior probabilities but just the counts. 
	 * However, we want to reuse these counts and the priors can be obtained by a simple division.
	 */
	private void calculateConceptPriors() {
		
		for (Concept c : tSet.getConceptSet()) {

	        //Calculate the priors for the concepts
	        int totalConceptCount=0;
		    
			for (Instance i : tSet.getInstances().values()) {
				
				if (i.getConcept().equals(c)) {
					totalConceptCount++;
				}
			}
			
			conceptPriors.put(c, new Double(totalConceptCount));
		}
	}
	
	protected void calculateConditionalProbabilities() {

		p = new HashMap<Concept, Map<Attribute, AttributeValue>>();
		
		for (Instance i : tSet.getInstances().values()) {
			
			for (Attribute a: i.getAtrributes()) {
				
				if (a != null && attributeList.contains(a.getName())) {

					if ( p.get(i.getConcept())== null ) {
						
						p.put(i.getConcept(), new HashMap<Attribute, AttributeValue>());
						
					}
					
					Map<Attribute, AttributeValue> aMap = p.get(i.getConcept());
					AttributeValue aV = aMap.get(a);
					if ( aV == null ) {

					    aV = new AttributeValue(a.getValue()); 
						aMap.put(a, aV);

					} else {
						aV.count();
					}
				}
			}
		}		
	}
	
	/**
	 * This method calculates the <I>posterior probability</I> that we deal with 
	 * concept <CODE>c</CODE> provided that we observed instance <CODE>i</CODE>.
	 * This is the application of Bayes theorem.
	 * 
	 * @param c is a probable concept for instance <CODE>i</CODE>
	 * @param i is the observed instance
	 * @return posterior probability of <CODE>c</CODE> given instance <CODE>i</CODE>
	 */
	public double getProbability(Concept c, Instance i) {
		
		double cP=0;

		if (tSet.getConceptSet().contains(c)) {
	
			cP = (getProbability(i,c)*getProbability(c))/getProbability(i);

		} else {
			// We have never seen this concept before
			// assign to it a "reasonable" value
			cP = 1/(tSet.getNumberOfConcepts()+1.0);
		}

		return cP;
	}

	/**
	 * This method calculates the denumerator of Bayes theorem
	 * 
	 * @param <CODE>Instance</CODE> i
	 * @return the probability of observing <CODE>Instance</CODE> i
	 */
	public double getProbability(Instance i) {
		
		double cP=0;

		for (Concept c : getTset().getConceptSet()) {

			cP += getProbability(i,c)*getProbability(c);
		}
		return (cP == 0) ? (double)1/tSet.getSize() : cP;
	}
	
	public double getProbability(Concept c) {
	    Double trInstanceCount = conceptPriors.get(c);
	    if( trInstanceCount == null ) {
	        trInstanceCount = 0.0;
	    }
		return trInstanceCount/tSet.getSize();
	}
	
	public double getProbability(Instance i, Concept c) {
		
		double cP=1;

		for (Attribute a : i.getAtrributes()) {
			
			if ( a != null && attributeList.contains(a.getName()) ) {

				Map<Attribute, AttributeValue> aMap = p.get(c);
				AttributeValue aV = aMap.get(a); 
				if ( aV == null) {
				    // the specific attribute value is not present for the current concept.
					// Can you justify the following estimate?
					// Can you think of a better choice?
					cP *= ((double) 1 / (tSet.getSize()+1));
				} else {
				    cP *= (aV.getCount()/conceptPriors.get(c));
				}
			}
		}	
			
		return (cP == 1) ? (double)1/tSet.getNumberOfConcepts() : cP;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the tSet
	 */
	public TrainingSet getTset() {
		return tSet;
	}	
}

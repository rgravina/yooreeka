package iweb2.ch5.usecase.email;

import iweb2.ch4.similarity.JaccardCoefficient;
import iweb2.ch5.classification.bayes.NaiveBayes;
import iweb2.ch5.ontology.core.AttributeValue;
import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch5.usecase.email.data.Email;
import iweb2.ch5.usecase.email.data.EmailData;
import iweb2.ch5.usecase.email.data.EmailDataset;

import java.util.HashMap;
import java.util.Map;

public class EmailClassifier extends NaiveBayes {
    
    private EmailDataset emailDataset;
    private int topNTerms;
    private boolean verbose = true;
    private double jaccardThreshold=0.25;
    
    public EmailClassifier(EmailDataset emailDataset, int topNTerms) {
        super("EmailClassifier", emailDataset.getTrainingSet(topNTerms));
        this.emailDataset = emailDataset;
        this.topNTerms = topNTerms;
    }
        
    @Override
	public boolean train() {
        
        if( emailDataset.getSize() == 0) {
            System.out.println("Can't train classifier - training dataset is empty.");
            return false;
        }

        for(String attrName : getTset().getAttributeNameSet()) {
            trainOnAttribute(attrName);
        }
        
        super.train();
        
        return true;
    }

    @Override
	protected void calculateConditionalProbabilities() {

        p = new HashMap<Concept, Map<Attribute, AttributeValue>>();
        
        for (Instance i : tSet.getInstances().values()) {
        	
            // In this specific implementation we have exactly one attribute
        	// In general, you need a loop over the attributes
            Attribute a = i.getAtrributes()[0];
                
            Map<Attribute, AttributeValue> aMap = p.get(i.getConcept());
            
            if ( aMap == null ) {
                aMap = new HashMap<Attribute, AttributeValue>();
                p.put(i.getConcept(), aMap);
            }
            
/**
 * TODO: 5.3
 */
            AttributeValue bestAttributeValue = findBestAttributeValue(aMap, a);
            
            if( bestAttributeValue != null ) {
            
            	bestAttributeValue.count();
            
            } else {
                AttributeValue aV = new AttributeValue(a.getValue());
                // register attribute as representative attribute
                aMap.put(a, aV);
            }
        }       
    }
    
    @Override
	public double getProbability(Instance i, Concept c) {
        
        double cP=1;

        for (Attribute a : i.getAtrributes()) {
            
            if ( a != null && attributeList.contains(a.getName()) ) {

                Map<Attribute, AttributeValue> aMap = p.get(c);
                
                AttributeValue bestAttributeValue = findBestAttributeValue(aMap, a);
                
                if ( bestAttributeValue == null) {
                
                	// the specific attribute value is not present for the current concept.
					// Can you justify the following estimate?
					// Can you think of a better choice?
                	cP *= ((double) 1 / (tSet.getSize()+1));
                
                } else {
                                  
                    cP *= (bestAttributeValue.getCount()/conceptPriors.get(c));
                }
            }
        }   
        return (cP == 1) ? (double)1/tSet.getNumberOfConcepts() : cP;
    }
    
    
    /*
     * Finds best match for attribute value among existing attribute value representatives.
     * 
     * @param aMap map of all attribute representatives.
     * @param a new attribute to compare against
     * @return representative attribute that is the best match for a new attribute 
     * or null if no satisfactory match was found. 
     */
    private AttributeValue findBestAttributeValue(Map<Attribute, AttributeValue> aMap, Attribute a) {
        
    	JaccardCoefficient jaccardCoeff = new JaccardCoefficient();

        String aValue = (String)a.getValue();
        String[] aTerms = aValue.split(" ");
        Attribute bestMatch = null;
        double bestSim = 0.0;

        /*
         * Here we only check representative attribute values. Other attribute values
         * associated with representative attribute values will be 
         * ignored by this implementation.
         */
        for(Attribute attr : aMap.keySet()) {
            String attrValue = (String)attr.getValue();
            String[] attrTerms = attrValue.split(" ");
            double sim = jaccardCoeff.similarity(aTerms, attrTerms);
            if( sim > jaccardThreshold && sim > bestSim) {
                bestSim = sim;
                bestMatch = attr;
            }
        }
        
        return aMap.get(bestMatch);
    }
    
    @Override
	public Concept classify(Instance instance) {
        return super.classify(instance);
    }
    
    public String classify(Email email) {
        EmailInstance i = emailDataset.toEmailInstance(email, topNTerms);
        Concept c = classify(i);
        if( verbose ) {
            System.out.println("Classified " + email.getId() + " as " + c.getName());
        }
        return c.getName();
    }

    public void sample() {
    	
    	Email email;
    	// TRAINING SET
    	System.out.println("________________________________________________");
    	System.out.println("Validating with emails from the training dataset");
    	System.out.println("________________________________________________");
    	email = emailDataset.findEmailById("biz-04.html");
    	classify(email);
    	
    	email = emailDataset.findEmailById("usa-03.html");
    	classify(email);
    	
    	//TEST SET
    	System.out.println("_______________________________________________");
    	System.out.println("Testing with unseen emails");
    	System.out.println("_______________________________________________");

    	EmailDataset testEmailDS = EmailData.createTestDataset();
    	email = testEmailDS.findEmailById("biz-01.html");
    	classify(email);
    	
    	email = testEmailDS.findEmailById("sport-01.html");
    	classify(email);
    	
    	email = testEmailDS.findEmailById("usa-01.html");
    	classify(email);
    	
    	email = testEmailDS.findEmailById("world-01.html");
    	classify(email);
    	
    	email = testEmailDS.findEmailById("spam-biz-01.html");
    	classify(email);
    }
    
	/**
	 * @return the jaccardThreshold
	 */
	public double getJaccardThreshold() {
		return jaccardThreshold;
	}

	/**
	 * @param jaccardThreshold the jaccardThreshold to set
	 */
	public void setJaccardThreshold(double jaccardThreshold) {
		this.jaccardThreshold = jaccardThreshold;
	}
        
}

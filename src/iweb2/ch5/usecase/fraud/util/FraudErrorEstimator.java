package iweb2.ch5.usecase.fraud.util;

import iweb2.ch5.classification.core.intf.Classifier;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch5.usecase.fraud.DTFraudClassifier;
import iweb2.ch5.usecase.fraud.NNFraudClassifier;
import iweb2.ch5.usecase.fraud.TransactionConcept;
import iweb2.ch5.usecase.fraud.data.Transaction;
import iweb2.ch5.usecase.fraud.data.TransactionDataset;
import iweb2.ch5.usecase.fraud.data.TransactionInstanceBuilder;

public class FraudErrorEstimator {

    private Classifier classifier;
    private TransactionInstanceBuilder instanceBuilder;
    private TransactionDataset testDS;
    
    private int correctCount = 0;
    private int incorrectValidCount = 0;
    private int incorrectFraudCount = 0;
    private int totalFraudTxnsCount = 0;
    
    public FraudErrorEstimator(TransactionDataset testDS, 
            NNFraudClassifier classifier) {
        
        this.testDS = testDS;
        
        if (classifier.isVerbose()) {
        	classifier.setVerbose(false);
        }
        
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();        
    }
    
    public FraudErrorEstimator(TransactionDataset testDS, 
            DTFraudClassifier classifier) {
        
        this.testDS = testDS;
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();        
    }
    
    public void run() {
    	
    	
        for(Transaction txn : testDS.getTransactions() ) {
            Instance i = instanceBuilder.createInstance(txn);
            Concept concept = classifier.classify(i);
            Concept expectedConcept = new TransactionConcept(txn.isFraud());
            
            if( txn.isFraud() ) {
                totalFraudTxnsCount++;
            }
            
            if( concept.getName().equals(expectedConcept.getName())) {
                correctCount++;
            }
            else {
                // Print classified instance
                i.print();
                if( !txn.isFraud() ) {
                    incorrectValidCount++;
                }
                else {
                    incorrectFraudCount++;
                }
            }            
        }
        
        System.out.println(
                "Total test dataset txns: " + testDS.getSize() + 
                ", Number of fraud txns:" + getTotalFraudTxnsCount());
        
        System.out.println("Classified correctly: " + getCorrectCount() + 
                ", Misclassified valid txns: " + getIncorrectValidCount() + 
                ", Misclassified fraud txns: " + getIncorrectFraudCount());
        
        int misclassifiedTotal = getIncorrectFraudCount() + getIncorrectValidCount();
    }
    

    public int getCorrectCount() {
        return correctCount;
    }


    public int getIncorrectValidCount() {
        return incorrectValidCount;
    }


    public int getIncorrectFraudCount() {
        return incorrectFraudCount;
    }


    public int getTotalFraudTxnsCount() {
        return totalFraudTxnsCount;
    }
    
    
}

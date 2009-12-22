package iweb2.ch6.usecase.credit.util;

import iweb2.ch5.classification.core.intf.Classifier;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch6.usecase.credit.BaggingCreditClassifier;
import iweb2.ch6.usecase.credit.BoostingCreditClassifier;
import iweb2.ch6.usecase.credit.CreditConcept;
import iweb2.ch6.usecase.credit.DTCreditClassifier;
import iweb2.ch6.usecase.credit.NBCreditClassifier;
import iweb2.ch6.usecase.credit.NNCreditClassifier;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;

public class CreditErrorEstimator {

    private Classifier classifier;
    private UserInstanceBuilder instanceBuilder;
    private UserDataset testDS;
    private ClassifierResults classifierResults;
    
    int[][] confusionMatrix = new int[5][5];

    private int correctCount = 0;
    private int misclassifiedInstanceCount = 0;
    private boolean verbose = true;

    public CreditErrorEstimator(UserDataset testDS, 
            BoostingCreditClassifier classifier) {
        
        this.testDS = testDS;
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();   
        this.classifierResults = 
            new ClassifierResults(classifier.getName(), testDS.getSize());
    }
    
    public CreditErrorEstimator(UserDataset testDS, 
            BaggingCreditClassifier classifier) {
        
        this.testDS = testDS;
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();   
        this.classifierResults = 
            new ClassifierResults(classifier.getName(), testDS.getSize());
    }
    
    public CreditErrorEstimator(UserDataset testDS, 
            DTCreditClassifier classifier) {
        
        this.testDS = testDS;
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();   
        this.classifierResults = 
            new ClassifierResults(classifier.getName(), testDS.getSize());
    }

    public CreditErrorEstimator(UserDataset testDS, 
            NBCreditClassifier classifier) {
        
        this.testDS = testDS;
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();
        this.classifierResults = 
            new ClassifierResults(classifier.getName(), testDS.getSize());
    }

    public CreditErrorEstimator(UserDataset testDS, 
            NNCreditClassifier classifier) {
        
        this.testDS = testDS;
        this.classifier = classifier;
        this.instanceBuilder = classifier.getInstanceBuilder();    
        this.classifierResults = 
            new ClassifierResults(classifier.getName(), testDS.getSize());
    }
    
    
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void run() {
    	
    	correctCount = 0;
    	misclassifiedInstanceCount = 0;
    	
        int idx = 0;
                
        for (int i=0; i < 5; i++) {
        	for (int j=0; j < 5; j++) {
        		confusionMatrix[i][j] =0;
            }	
        }
        
        long tStart = System.currentTimeMillis();
        
        for(User user : testDS.getUsers() ) {
            
            Instance instance = instanceBuilder.createInstance(user);
            Concept concept = classifier.classify(instance);
            Concept expectedConcept = new CreditConcept(user.getCategory());
            
            String actualCreditLabel = expectedConcept.getName();
            String predictedCreditLabel = concept.getName();
            
            // Build the confusion matrix
            int i = CreditConcept.getIndex(actualCreditLabel);
            int j = CreditConcept.getIndex(predictedCreditLabel);
            
            confusionMatrix[i][j]++;
            
            if( actualCreditLabel.equals(predictedCreditLabel)) {
            	
                correctCount++;
                
                classifierResults.setResult(idx, true);
            
            } else {
            	// Uncomment the following lines to see the details of the misclassifications
//            	System.out.print("Classified as: " + concept.getName() + " ");
//                instance.print();
                
                misclassifiedInstanceCount++;
                
                classifierResults.setResult(idx, false);
            }            

            idx++;
        }

        if( verbose ) {
        	
        	long tEnd = System.currentTimeMillis();
        	
            // SUMMARY
        	System.out.println(" Classification completed in "+0.001*(tEnd-tStart)+" seconds.\n");
        	int totalCount = testDS.getSize();
        	
            System.out.println(" Total test dataset txns: " + totalCount );
            
            System.out.println("    Classified correctly: " + getCorrectCount() + 
                    ", Misclassified: " + getMisclassifiedInstanceCount() );
            
            System.out.println("                Accuracy: "+ getAccuracy());
            System.out.println("___________________________________________________________\n");
            // DETAILS
            System.out.println("                CONFUSION MATRIX");
            System.out.println("___________________________________________________________\n");
            
            System.out.printf("%4s", "");
            for (int i=0; i < 5; i++) {
            	System.out.printf("%7s", CreditConcept.getLabel(i));
            }
            System.out.println();
    
            for (int i=0; i < 5; i++) {
            	System.out.printf("%4s", CreditConcept.getLabel(i));
            	for (int j=0; j < 5; j++) {
            		System.out.printf("%7s", confusionMatrix[i][j]);
                }
                System.out.println();
            }
            System.out.println("___________________________________________________________\n");
            
        }
    }

    public double getAccuracy() {
    	return (double) correctCount / (double) testDS.getSize();
    }
    
    public int getCorrectCount() {
        return correctCount;
    }

    public int getMisclassifiedInstanceCount() {
        return this.misclassifiedInstanceCount;
    }

    public ClassifierResults getResults() {
        return classifierResults;
    }

	/**
	 * @return the confusionMatrix
	 */
	public int[][] getConfusionMatrix() {
		return confusionMatrix;
	}

    
}

package iweb2.ch6.usecase.credit;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.classification.core.intf.Classifier;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch6.boosting.BoostingARCX4Classifier;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.util.UserInstanceBuilder;

public class BoostingCreditClassifier extends BoostingARCX4Classifier {

    private UserInstanceBuilder instanceBuilder;
    
    private ClassifierMemberType classifierType;
    
    public BoostingCreditClassifier(UserDataset ds) {
    
        this(BoostingCreditClassifier.class.getSimpleName(), 
                ds, new UserInstanceBuilder(false));
        
    }
    
    public BoostingCreditClassifier(String name, UserDataset ds, UserInstanceBuilder instanceBuilder) {
        this(name, instanceBuilder, instanceBuilder.createTrainingSet(ds));
    }

    public BoostingCreditClassifier(String name, 
            UserInstanceBuilder instanceBuilder, TrainingSet tSet) {
        
        super(name, tSet);
        
        this.instanceBuilder = instanceBuilder;
    }

    public UserInstanceBuilder getInstanceBuilder() {
        return instanceBuilder;
    }
    
    public Concept classify(User user) {

        if (verbose) {
            System.out.println("User:\n  >> "+user.toString());
        }
        
        return classify(instanceBuilder.createInstance(user));
    }
    
    @Override
    public Classifier getClassifierForTraining(TrainingSet set) {
        
        Classifier baseClassifier = null;
        
        switch(classifierType) {
            case NEURAL_NETWORK:
                NNCreditClassifier nnClassifier = new NNCreditClassifier(set);
                nnClassifier.setLearningRate(0.01);
                nnClassifier.useDefaultAttributes();
                baseClassifier = nnClassifier;                
                break;
            case DECISION_TREE:
                DTCreditClassifier dtClassifier = new DTCreditClassifier(set);
                dtClassifier.useDefaultAttributes();
                dtClassifier.setPruneAfterTraining(true);
                baseClassifier = dtClassifier;
                break;
            case NAIVE_BAYES:
                NBCreditClassifier nbClassifier = new NBCreditClassifier(set);
                nbClassifier.useDefaultAttributes();
                baseClassifier = nbClassifier;                
                break;
            default: 
                throw new RuntimeException("Invalid classifier member type!");
        }
        
        return baseClassifier;
    }

	/**
	 * @return the classifierType
	 */
	public ClassifierMemberType getClassifierType() {
		return classifierType;
	}

	/**
	 * @param classifierType the classifierType to set
	 */
	public void setClassifierType(String type) {
		
		if (type.equalsIgnoreCase("decision tree")) {
			this.classifierType = ClassifierMemberType.DECISION_TREE;			
		} else if (type.equalsIgnoreCase("neural network")) {
			this.classifierType = ClassifierMemberType.NEURAL_NETWORK;
		} else if (type.equalsIgnoreCase("naive bayes")) {
			this.classifierType = ClassifierMemberType.NAIVE_BAYES;
		}
	}
}

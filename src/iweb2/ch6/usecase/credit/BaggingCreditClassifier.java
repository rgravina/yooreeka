package iweb2.ch6.usecase.credit;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch6.ensemble.ClassifierEnsemble;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.util.BootstrapTrainingSetBuilder;
import iweb2.ch6.usecase.credit.util.UserInstanceBuilder;


public class BaggingCreditClassifier extends ClassifierEnsemble {

    private UserInstanceBuilder instanceBuilder;
    private BootstrapTrainingSetBuilder bootstrapTSetBuilder;
    
    public BaggingCreditClassifier(UserDataset ds) {

        super(BaggingCreditClassifier.class.getSimpleName());
        
        /* Creating instance builder for this classifier */
        instanceBuilder = new UserInstanceBuilder(false);
        

        /* Creating original training set that will be used to generate 
         * bootstrap sets */
        TrainingSet originalTSet = instanceBuilder.createTrainingSet(ds); 
        
        bootstrapTSetBuilder = new BootstrapTrainingSetBuilder(originalTSet);          
    }
    
    public TrainingSet getBootstrapSet() {
    	return bootstrapTSetBuilder.buildBootstrapSet();
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
    
}

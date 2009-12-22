package iweb2.ch6.usecase.credit;

import iweb2.ch5.classification.bayes.NaiveBayes;
import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.util.UserInstanceBuilder;

public class NBCreditClassifier extends NaiveBayes {
    
    private UserInstanceBuilder instanceBuilder;
    
    public NBCreditClassifier(UserDataset ds) {
        this( createDefaultClassifierName(), ds);
    }
    
    public NBCreditClassifier(String name, UserDataset ds) {
        this( name, ds, createDefaultInstanceBuilder() );
    }

    public NBCreditClassifier(String name, UserDataset ds, UserInstanceBuilder instanceBuilder) {

        this(name, instanceBuilder.createTrainingSet(ds), instanceBuilder);
        
    }

    public NBCreditClassifier(TrainingSet ts) {

        super( createDefaultClassifierName(), ts);

        this.instanceBuilder = createDefaultInstanceBuilder();
    }
    
    
    public NBCreditClassifier(String name, TrainingSet ts, 
            UserInstanceBuilder instanceBuilder) {

        super(name, ts);

        this.instanceBuilder = instanceBuilder; 
    }
    

    private static String createDefaultClassifierName() {
        return NBCreditClassifier.class.getSimpleName();
    }
    
    private static UserInstanceBuilder createDefaultInstanceBuilder() {
        // using Instance Builder configured to produce instances with String attributes        
        return new UserInstanceBuilder(false);        
    }
    
    
    @Override
	public Concept classify(Instance instance) {
        return super.classify(instance);
    }
    
    public Concept classify(User user) {
        return classify(instanceBuilder.createInstance(user));        
    }
    
    public Concept classify(User u, boolean print) {
    	Concept c = classify(u);
    	if (print) {
    		System.out.println("Actual ---> "+u.getCategory()+"\nAssigned -> "+c.getName());
    	}
    	return c;
    }
    
    public void useDefaultAttributes() {
        trainOnAttribute(CreditInstance.ATTR_NAME_JOB_CLASS);
        trainOnAttribute(CreditInstance.ATTR_NAME_INCOME_TYPE);
        trainOnAttribute(CreditInstance.ATTR_NAME_AGE);    
        trainOnAttribute(CreditInstance.ATTR_NAME_CAR_OWNERSHIP);
        trainOnAttribute(CreditInstance.ATTR_NAME_CREDIT_SCORE);
        trainOnAttribute(CreditInstance.ATTR_NAME_MORTGAGE_DOWN_PAYMENT);
        trainOnAttribute(CreditInstance.ATTR_NAME_MOTOR_BICYCLE_OWNERSHIP);
        trainOnAttribute(CreditInstance.ATTR_NAME_OTHER_PROPERTY_OWNERSHIP);
        trainOnAttribute(CreditInstance.ATTR_NAME_CRIMINAL_RECORD);
        trainOnAttribute(CreditInstance.ATTR_NAME_BANKRUPTCY);
        trainOnAttribute(CreditInstance.ATTR_NAME_RETIREMENT_ACCOUNT);        
    }

    public UserInstanceBuilder getInstanceBuilder() {
        return this.instanceBuilder;
    }
    
}

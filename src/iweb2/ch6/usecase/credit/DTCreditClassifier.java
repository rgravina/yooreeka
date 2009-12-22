package iweb2.ch6.usecase.credit;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.classification.dtree.DecisionTreeClassifier;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.util.UserInstanceBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DTCreditClassifier extends DecisionTreeClassifier {

    private static final long serialVersionUID = 5491106283513021975L;
    
    private UserInstanceBuilder instanceBuilder;

    private boolean pruneAfterTraining;
    
    public DTCreditClassifier(UserDataset ds) {
        this(createDefaultClassifierName(), ds);
    }
    
    public DTCreditClassifier(String name, UserDataset ds) {
        this( name, ds, createDefaultInstanceBuilder() );
        
    }

    public DTCreditClassifier(String name, UserDataset ds, 
            UserInstanceBuilder instanceBuilder) {

        this(name, instanceBuilder.createTrainingSet(ds), instanceBuilder);
    }
    
    public DTCreditClassifier(TrainingSet ts) {
        this(createDefaultClassifierName(), ts, createDefaultInstanceBuilder() );
    }
    
    public DTCreditClassifier(String name, TrainingSet ts, 
            UserInstanceBuilder instanceBuilder) {
        
        super(name, ts);
        
        this.instanceBuilder = instanceBuilder;
        this.pruneAfterTraining = true;
        
    }
    
    private static String createDefaultClassifierName() {
        return DTCreditClassifier.class.getSimpleName();
    }
    
    private static UserInstanceBuilder createDefaultInstanceBuilder() {
        // using Instance Builder configured to produce instances with String attributes        
        return new UserInstanceBuilder(false);        
    }
    
    public static void saveClassifier(
            String filename, DTCreditClassifier o) {

        try {
            File f = new File(filename);
            FileOutputStream foutStream = new FileOutputStream(f);
            BufferedOutputStream boutStream = new BufferedOutputStream(foutStream);
            ObjectOutputStream objOutputStream = new ObjectOutputStream(boutStream);
            objOutputStream.writeObject(o); 
            objOutputStream.flush();
            boutStream.close();
        }
        catch(IOException e) {
            throw new RuntimeException(
                    "Error while saving data into file: '" + filename + "'", e);         
        }
        
        System.out.println("saved classifier in file: " + filename);        
    }
    
    public static DTCreditClassifier loadClassifier(String filename) {
        
        Object o = null;
        File f = new File(filename);
        if( f.exists() ) {
            try {
                FileInputStream fInStream = new FileInputStream(f);
                BufferedInputStream bufInStream = new BufferedInputStream(fInStream);
                ObjectInputStream objInStream = new ObjectInputStream(bufInStream);
                o = objInStream.readObject();
                objInStream.close();
            }
            catch(Exception e) {
                throw new RuntimeException(
                        "Error while loading data from file: '" + filename + "'", e);            
            }
        }
        else {
            throw new IllegalArgumentException("File doesn't exist: '" + filename + "'."); 
        }
        
        System.out.println("loaded classifier from file: " + filename);
        
        return (DTCreditClassifier)o;

    }
    
    @Override
	public boolean train() {
        boolean result = super.train();
        if( result && pruneAfterTraining ) {
            this.pruneTree();
        }
        return result;
    }
    
    public Concept classify(User u) {
    	return classify(instanceBuilder.createInstance(u));
    }
        
    public Concept classify(User u, boolean print) {
    	Concept c = classify(u);
    	if (print) {
    		System.out.println("Actual ---> "+u.getCategory()+"\nAssigned -> "+c.getName());
    	}
    	return c;
    }
    
    public void useDefaultAttributes() {
        trainOnAttribute(CreditInstance.ATTR_NAME_JOB_CLASS, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_INCOME_TYPE, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_AGE, true);    
        trainOnAttribute(CreditInstance.ATTR_NAME_CAR_OWNERSHIP, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_CREDIT_SCORE, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_MORTGAGE_DOWN_PAYMENT, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_MOTOR_BICYCLE_OWNERSHIP, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_OTHER_PROPERTY_OWNERSHIP, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_CRIMINAL_RECORD, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_BANKRUPTCY, true);
        trainOnAttribute(CreditInstance.ATTR_NAME_RETIREMENT_ACCOUNT, true);        
    }
    
    public UserInstanceBuilder getInstanceBuilder() {
        return this.instanceBuilder;
    }

    public boolean isPruneAfterTraining() {
        return pruneAfterTraining;
    }

    public void setPruneAfterTraining(boolean pruneAfterTraining) {
        this.pruneAfterTraining = pruneAfterTraining;
    }
    
    
}

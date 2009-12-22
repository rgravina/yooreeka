package iweb2.ch5.usecase.fraud;

import iweb2.ch5.classification.dtree.DecisionTreeClassifier;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.usecase.fraud.data.Transaction;
import iweb2.ch5.usecase.fraud.data.TransactionDataset;
import iweb2.ch5.usecase.fraud.data.TransactionInstanceBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DTFraudClassifier extends DecisionTreeClassifier {

    private static final long serialVersionUID = 5491106283513021975L;
    
    private TransactionInstanceBuilder instanceBuilder;
    
    public DTFraudClassifier(TransactionDataset ds) {
        this(DTFraudClassifier.class.getSimpleName(), ds);
    }
    
    public DTFraudClassifier(String name, TransactionDataset ds) {

        super(name, ds.createTrainingDataset());
        this.instanceBuilder = ds.getInstanceBuilder(); 
    }

    public TransactionInstanceBuilder getInstanceBuilder() {
        return instanceBuilder;
    }

    public void setInstanceBuilder(TransactionInstanceBuilder instanceBuilder) {
        this.instanceBuilder = instanceBuilder;
    }
    
    @Override
	protected Concept createConcept(String category) {
        return new TransactionConcept(category);
    }
    
    public static void saveClassifier(
            String filename, DTFraudClassifier o) {

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
    
    public static DTFraudClassifier loadClassifier(String filename) {
        
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
        
        return (DTFraudClassifier)o;

    }
    
    public Concept classify(Transaction t) {
    	return classify(instanceBuilder.createInstance(t));
    }
        
    
    public void useDefaultAttributes() {
        trainOnAttribute(TransactionInstance.ATTR_NAME_N_DESCRIPTION, false);
        trainOnAttribute(TransactionInstance.ATTR_NAME_N_LOCATION, false);
        trainOnAttribute(TransactionInstance.ATTR_NAME_N_TXN_AMT, false);

    }
}

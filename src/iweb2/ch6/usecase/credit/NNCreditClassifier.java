package iweb2.ch6.usecase.credit;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.classification.core.intf.Classifier;
import iweb2.ch5.ontology.core.DoubleAttribute;
import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.util.UserInstanceBuilder;
import iweb2.util.config.IWeb2Config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NNCreditClassifier 
    implements Classifier, java.io.Serializable {

    private static final long serialVersionUID = 8584476885427513654L;

    private static final String SERIALIZATION_PATH = IWeb2Config.getHome()+"\\data\\ch06\\";

    private boolean verbose=false;

    private String name;
    
    /*
     * Neural Network that will be used by this classifier.
     */
    private UserCreditNN nn;     
    
    private int DEFAULT_TRAINING_ITERATIONS = 10;

    /*
     * Number of times to feed training instances into the network during training. 
     */
    private int nTrainingIterations = DEFAULT_TRAINING_ITERATIONS;


    private double DEFAULT_LEARNING_RATE = 0.025;
    
    /*
     * Learning rate that will be used in NN training.
     */
    private double learningRate = DEFAULT_LEARNING_RATE;
    
    private transient TrainingSet ts;
    
    private UserInstanceBuilder instanceBuilder;
    
    /*
     * Attribute names that should be used as Neural Network inputs.
     */
    private List<String> availableAttributeNames;    

    public NNCreditClassifier(UserDataset ds) {
        this(createDefaultClassifierName(), ds);
    }
    
    public NNCreditClassifier(String name, UserDataset ds) {
        // using Instance Builder configured to produce instances with Double attributes
        this( name, ds, createDefaultInstanceBuilder() );
    }

    public NNCreditClassifier(String name, UserDataset ds, UserInstanceBuilder instanceBuilder) {
        this( name, instanceBuilder.createTrainingSet(ds), instanceBuilder );
    }

    public NNCreditClassifier(TrainingSet ts) {
        this(createDefaultClassifierName(), ts, createDefaultInstanceBuilder());
    }
    
    
    public NNCreditClassifier(String name, TrainingSet ts, 
            UserInstanceBuilder instanceBuilder) {

        this.name = name;

        this.ts = ts;
        
        this.instanceBuilder = instanceBuilder;
        
        this.availableAttributeNames = new ArrayList<String>();
        
        nn = createNeuralNetwork();
    }
    
    private static String createDefaultClassifierName() {
        return NNCreditClassifier.class.getSimpleName();
    }
    
    private static UserInstanceBuilder createDefaultInstanceBuilder() {
        // using Instance Builder configured to produce instances with Double attributes        
        return new UserInstanceBuilder(true);        
    }
    
    
    public void trainOnAttribute(String name) {
        availableAttributeNames.add(name);
    }
    
    public UserInstanceBuilder getInstanceBuilder() {
        return this.instanceBuilder;
    }
    
    public Concept classify(User user) {
    	if (verbose) {
    		System.out.println("User:\n  >> "+user.toString());
    	}
    	return classify(instanceBuilder.createInstance(user));
    }

    public Concept classify(User u, boolean print) {
    	Concept c = classify(u);
    	if (print) {
    		System.out.println("Actual ---> "+u.getCategory()+"\nAssigned -> "+c.getName());
    	}
    	return c;
    }
    
    public Concept classify(Instance instance) {
        

        double[] x = createNNInputs( instance );

        double[] y = nn.classify(x);
        
        Concept c = createConceptFromNNOutput(y);
        
        if (verbose) {
        	System.out.println("\nAssessment:\n  >> This is a "+c.getName());
        }
        return c;
    }

    public boolean train() {
    	
    	long t0 = System.currentTimeMillis();
    	
        if( ts == null ) {
            throw new RuntimeException(
                        "Can't train classifier - training dataset is null.");
        }

        if( nn.getInputNodeCount() != availableAttributeNames.size()) {
            throw new RuntimeException(
                    "Number of attributes doesn't match with the number of input nodes." +
                    "Attributes: " + availableAttributeNames.size() + 
                    ", Input nodes: " + nn.getInputNodeCount());
        }
        
        trainNeuralNetwork(nTrainingIterations);
        
        System.out.print("       Neural network training completed in ");
        System.out.println((System.currentTimeMillis()-t0)+" (ms)");

        return true;
    }
    
    
    private void trainNeuralNetwork(int nIterations) {

        for(int i = 1; i <= nIterations; i++) {
            for(Instance instance : ts.getInstances().values()) {
                double[] nnInput = createNNInputs(instance);
                double[] nnExpectedOutput = createNNOutputs(instance);
                
                nn.train(nnInput, nnExpectedOutput);
            }
            
            if (verbose) {
            	System.out.println("finished training pass: " + i + " out of " + nIterations);            
            }
        }  
    }
    
    public double[] createNNInputs(Instance instance) {
        
        /*
         * Converting all String attributes into Double attributes.
         */
        Instance convertedInstance = instanceBuilder.createInstance(instance);
        
        int nInputNodes = nn.getInputNodeCount();
        
        double[] x = new double[nInputNodes];     
        
        for(int i = 0; i < nInputNodes; i++) {
            
            String attrName = this.availableAttributeNames.get(i);
            Attribute a = convertedInstance.getAttributeByName(attrName);
            
            if( a instanceof DoubleAttribute ) {
                x[i] = (Double)a.getValue();
            }
            else {
                if( a == null ) {
                    throw new RuntimeException("Failed to find attribute with name: '" +  
                            attrName + "'. Instance: " + convertedInstance.toString());
                }
                else {
                    throw new RuntimeException("Invalid attribute type. Only " + 
                            DoubleAttribute.class.getSimpleName() + " attribute" +
                            " types can be used in NN. Actual attribute type: " +
                            a.getClass().getSimpleName());
                }
            }
            
            
        }
        
        return x;
    }

    public double[] createNNOutputs(Instance i) {
        
        int nOutputNodes = nn.getOutputNodeCount();
        
        double[] y = new double[nOutputNodes];
        for(int n = 0; n < nOutputNodes; n++) {
            String category = i.getConcept().getName();
            y[n] = getOutputValue(n, category);
        }
        
        return y;
    }
    
    String[] categories = new String[] {
            CreditConcept.CONCEPT_LABEL_EX, 
            CreditConcept.CONCEPT_LABEL_VG,
            CreditConcept.CONCEPT_LABEL_GD,
            CreditConcept.CONCEPT_LABEL_BD,
            CreditConcept.CONCEPT_LABEL_DN
    };
    
    private double getOutputValue(int i, String category) {
        if( categories[i].equals(category) ) {
            return 1.0;
        }
        else {
            return 0.0;  
        }
    }
    
    private Concept createConceptFromNNOutput(double[] y) {

        int categoryIndex = 0;
        for(int i = 1, n = y.length; i < n; i++) {
            if( y[i] > y[categoryIndex] ) {
                categoryIndex = i;
            }
        }

        return new CreditConcept(categories[categoryIndex]);
    }
    
    
    private UserCreditNN createNeuralNetwork() {
        
        String nnName = "NNUserCreditClassifierNN";
        
        UserCreditNN nn = new UserCreditNN(nnName);
        // set custom parameters and recreate the network 
        nn.setLearningRate(learningRate);
        nn.removeAllNodesAndLayers();
        nn.create();
        return nn;
    }
    
    public void save() {

    	String filename = SERIALIZATION_PATH + this.getName();
        try {
            File f = new File(filename);
            FileOutputStream foutStream = new FileOutputStream(f);
            BufferedOutputStream boutStream = new BufferedOutputStream(foutStream);
            ObjectOutputStream objOutputStream = new ObjectOutputStream(boutStream);
            objOutputStream.writeObject(this); 
            objOutputStream.flush();
            boutStream.close();
        }
        catch(IOException e) {
            throw new RuntimeException(
                    "Error while saving data into file: '" + filename + "'", e);         
        }
        
        System.out.println("saved classifier in file: " + filename);        
    }
    
    public static NNCreditClassifier load(String filename) {
        
        Object o = null;
        File f = new File(SERIALIZATION_PATH+filename);
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
        
        return (NNCreditClassifier)o;

    }

    public void setNTrainingIterations(int trainingIterations) {
        nTrainingIterations = trainingIterations;
    }

    /** 
     * This methods facilitates the loading of training attributes 
     */
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
	
	
}

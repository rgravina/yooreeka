package iweb2.ch6.usecase.credit.util;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.ontology.intf.Instance;

import java.util.Map;
import java.util.Random;

/**
 * Builds bootstrap training sets from the original training set.
 */
public class BootstrapTrainingSetBuilder {

    private TrainingSet originalTrainingSet;
    
    /**
     * 
     * @param originalTrainingSet bootstrap training sets will be derived from 
     * this training set.
     * @param bootstrapSampleSize size of bootstrap training sets that should be
     * produced.
     */
    public BootstrapTrainingSetBuilder(TrainingSet originalTrainingSet) {
    	
        this.originalTrainingSet = originalTrainingSet;
    }

    public TrainingSet buildBootstrapSet() {
        
        int N = originalTrainingSet.getSize();

        Map<Integer, Instance> instances = originalTrainingSet.getInstances();
        
        Instance[] selectedInstances = new Instance[N];
        /*
         * Building a new training set of size N by sampling N instances
         * from the original data set with replacement. As a result, some 
         * instances from the original data set will be missing and some 
         * will be duplicated.
         */
    	Random rnd = new Random();
    	
    	//pick a center
    	int center = rnd.nextInt(N);

        int countN =0;        
        
        while (countN < N) {
        	
        	if (countN % (N/5) == 0) {
        		center = rnd.nextInt(N);
        	}
        	
        	int selectedInstanceId = pickInstanceId(N,center);
        	
            Instance selectedInstance = instances.get(selectedInstanceId); 	
            selectedInstances[countN] = selectedInstance;
            countN++;
        }
        
        TrainingSet tS = new TrainingSet(selectedInstances);
        
        return tS;
    }
    
    private int pickInstanceId(int N, int center) {

    	Random rnd = new Random();
        boolean loop = true;
        int selectedInstanceId=-1;

        //create the scale factor
    	double scale = (N/2) / 4.0d;
    	
        while (loop) {
	    	
	    	// center the distribution to be N/2 left and right of the center with almost certainty
	        selectedInstanceId = new Double(center + rnd.nextGaussian()*scale).intValue();
	        
	        // do not break the loop unless we found a valid instance
	        if (selectedInstanceId >=0 && selectedInstanceId < N) {
	        	loop=false;
	        }
        }        
    	return selectedInstanceId;
    }
    
}

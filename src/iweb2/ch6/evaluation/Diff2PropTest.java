package iweb2.ch6.evaluation;

import iweb2.ch6.usecase.credit.util.ClassifierResults;

public class Diff2PropTest extends Test {

    private ClassifierResults c1;
    private ClassifierResults c2;

    
    public Diff2PropTest(ClassifierResults c1, ClassifierResults c2) {
        this.c1 = c1;
        this.c2 = c2;
        
        calculate();
    }
    
    @Override
	protected void calculate() {
    	
        double n = c1.getN();
        double p = 0.5 * (c1.getAccuracy() + c2.getAccuracy());
        double a = c1.getAccuracy() - c2.getAccuracy();
        double b = ( 2.0 * p * ( 1 - p ) ) / n;
        z = a / Math.sqrt(b);
        
    }
    
    double z = 0.0;
    
    public double getDiscrepancyStatistic() {
        return z;
    }
    
    
    public boolean different() {
        /*
         * Confidence interval: 0.05
         * Null hypothesis - classifiers are the same
         * Null hypothesis is rejected if |z| > 1.96
         */ 
        if( Math.abs(z) > 1.96) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
	public void evaluate() {
    	
    	print("_____________________________________________________");
        print("Evaluating classifiers " + c1.getClassifierId() + 
                " and " + c2.getClassifierId() + ":");

        print("_____________________________________________________");
        print(c1.getClassifierId() + " accuracy: " + c1.getAccuracy());
        print(c2.getClassifierId() + " accuracy: " + c2.getAccuracy());
        print("_____________________________________________________");
        
        print("Confidence Interval             : 0.05");
        print("Statistic threshold (Std Normal): 1.96");
        
        String tmp;
        if (different()) {
        	tmp=">";
        } else {
        	tmp="<=";
        }
        print("_____________________________________________________");

        print("|z| = " + z + tmp +"1.96");
        
        print("The classifiers are different: " + String.valueOf(different()).toUpperCase());
    }
}

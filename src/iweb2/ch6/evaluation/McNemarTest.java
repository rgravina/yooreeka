package iweb2.ch6.evaluation;

import iweb2.ch6.usecase.credit.util.ClassifierResults;

public class McNemarTest extends Test {

    private ClassifierResults c1;
    private ClassifierResults c2;

    /*
     * Using 'n??' notation. First '?' represents result for first classifier.
     * Second '?' represents result for the second classifier. 
     * 0 - misclassification, 1 - correct classification. 
     */
    
    private int n11 = 0; // both classifiers were correct
    private int n10 = 0; // first is correct, second incorrect
    private int n01 = 0; // first incorrect, second correct
    private int n00 = 0; // both incorrect
    
    public McNemarTest(ClassifierResults c1, ClassifierResults c2) {
        this.c1 = c1;
        this.c2 = c2;
        
        calculate();
    }
    
    @Override
	protected void calculate() {
        int n = c1.getN();
        
        
        for(int i = 0; i < n; i++) {
            if( c1.getResult(i) && c2.getResult(i) ) {
                n11++;
            }
            else if( c1.getResult(i) && !c2.getResult(i) ) {
                n10++;
            }
            else if( !c1.getResult(i) && c2.getResult(i) ) {
                n01++;
            }
            else {
                n00++;
            }
        }
        
        double a = Math.abs(n01 - n10) - 1;
        chi2 = a * a / (n01 + n10);

        
    }
    
    double chi2 = 0.0;
    
    public double getDiscrepancyStatistic() {
        return chi2;
    }
    
    
    public boolean different() {
        // using level of significance 0.05, 1 degree of freedom: 
        // reject null hypothesis if chi2 > 3.841 
        if( chi2 > 3.841) {
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
        print("N = " + c1.getN() + 
                ", n00=" + n00 + 
                ", n10=" + n10 +
                ", n01=" + n01 +
                ", n11=" + n11);
        print("_____________________________________________________");        
        
        print("Confidence Interval             : 0.05");
        print("Degrees of Freedom              : 1");
        print("Statistic threshold (Chi-square): 3.841");
        
        String tmp;
        if (different()) {
        	tmp=">";
        } else {
        	tmp="<=";
        }
        print("_____________________________________________________");
        
        print("Chi2 = " + chi2 + tmp +"3.841");
        
        print("The two classifiers are different: " + String.valueOf(different()).toUpperCase());
    }
    
    public int getN11() {
        return n11;
    }

    public int getN10() {
        return n10;
    }

    public int getN00() {
        return n00;
    }
}

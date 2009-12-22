package iweb2.ch6.evaluation;

import iweb2.ch6.usecase.credit.util.ClassifierResults;

public class CochransQTest extends Test {

    private ClassifierResults c1;
    private ClassifierResults c2;
    private ClassifierResults c3;

    private double L = 3.0;
    
    public CochransQTest(ClassifierResults c1, ClassifierResults c2, ClassifierResults c3) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        
        calculate();
    }
    
    @Override
	protected void calculate() {
        int n = c1.getN();
        
        /*
         * Total number of correct classifications among all classifiers.
         */
        double T = calculateT();

        double T2 = 0.0;
        
        for(int i = 0; i < n; i++) {
            double x = 0.0;
            
            if( c1.getResult(i) ) {
                x++;
            }
            if( c2.getResult(i) ) {
                x++;
            }
            if( c3.getResult(i) ) {
                x++;
            }
            
            T2 += (x * x);
        }
        
        double sum = 0.0;
        sum  =   (double)c1.getNCorrect() * c1.getNCorrect() +
                 (double)c2.getNCorrect() * c2.getNCorrect() +
                 (double)c3.getNCorrect() * c3.getNCorrect() ;

        double a = L * sum;
        
        q = (L - 1) * (a - T * T) / (L * T - T2);
        
    }
    
    private double q = 0.0;
    
    
    /*
     * Calculates total number of correct classifications among all 
     * classifiers.
     */
    private int calculateT() {
        return c1.getNCorrect() + c2.getNCorrect() + c3.getNCorrect();
    }
    
    
    public double getDiscrepancyStatistic() {
        return q;
    }
    
    
    
    
    public boolean different() {
        // Confidence interval: 0.05
        // Null hypothesis: classifiers are the same
        // Degrees of freedom L - 1 = 2
        // Rejected if q > 5.991
        if( q > 5.991) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
	public void evaluate() {
        print("_____________________________________________________");

        print("Evaluating classifiers " + 
                c1.getClassifierId() + 
                ", " + c2.getClassifierId() + 
                ", " + c3.getClassifierId() +
                ":");
        print("_____________________________________________________");
        print(c1.getClassifierId() + " accuracy: " + c1.getAccuracy());
        print(c2.getClassifierId() + " accuracy: " + c2.getAccuracy());
        print(c3.getClassifierId() + " accuracy: " + c3.getAccuracy());        
        print("_____________________________________________________");
        
        print("Confidence Interval             : 0.05");
        print("Degrees of Freedom              : 2");
        print("Statistic threshold (chi-square): 5.991");
        
        String tmp;
        if (different()) {
        	tmp=">";
        } else {
        	tmp="<=";
        }
        print("_____________________________________________________");

        print("Q = " + q + tmp +"5.991");

        print("The classifiers are different: " + String.valueOf(different()).toUpperCase());
    }
    
}

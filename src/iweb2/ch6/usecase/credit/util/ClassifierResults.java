package iweb2.ch6.usecase.credit.util;

public class ClassifierResults {
    private String classifierId;
    private boolean[] results;
    private int nCorrect;
    
    public ClassifierResults(String classifierId, int n) {
        this.classifierId = classifierId;
        this.results = new boolean[n];
        this.nCorrect = 0;
    }
    
    public boolean getResult(int i) {
        return results[i];
    }
    
    public void setResult(int i, boolean value) {
        results[i] = value;
        if( value ) {
            nCorrect++;
        }
    }
    
    public String getClassifierId() {
        return classifierId;
    }
    
    public int getN() {
        return results.length;
    }
    
    public int getNCorrect() {
        return nCorrect;
    }
    
    public double getAccuracy() {
        return (double)nCorrect / (double)results.length;
    }
}

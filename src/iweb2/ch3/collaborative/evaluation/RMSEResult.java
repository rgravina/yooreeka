package iweb2.ch3.collaborative.evaluation;

public class RMSEResult {
    private String type;
    private long testSize;
    private double similarityThreshold;
    private double error;
    
    public RMSEResult(String type, long testSize, double simThreshold, double error) {
        this.type = type;
        this.testSize = testSize;
        this.similarityThreshold = simThreshold;
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTestSize() {
        return testSize;
    }

    public void setTestSize(long testSize) {
        this.testSize = testSize;
    }

    public double getSimilarityThreshold() {
        return similarityThreshold;
    }

    public void setSimilarityThreshold(double similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
    
    @Override
	public String toString() {
        return "RMSE (testSize=" + getTestSize() + 
        ", type=" + getType() + 
        ", similarityThreshold=" + getSimilarityThreshold() +
        "): " + getError();        
    }
}

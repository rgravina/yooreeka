package iweb2.ch5.classification.nn.intf;

import java.util.List;

public interface Layer extends java.io.Serializable {
    int getId();
    String getType(); // input, output, hidden
    List<Node> getNodes();
    
    public void setInputValues(double[] x);
    public void setExpectedOutputValues(double[] x);
    public double[] getValues();
        
    
    public void calculate();
    public void propagate();
    
    public void calculateWeightAdjustments();
    public void updateWeights();
    public void printWeights();    
}


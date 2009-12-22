package iweb2.ch5.classification.nn.intf;

import java.util.List;

public interface Node extends java.io.Serializable {
    String getNodeId();
    List<Link> getOutlinks();
    List<Link> getInlinks();
    void addInlink(Link inlink);
    void addOutlink(Link outlink);

    /**
     * Net Activation
     * 
     * @return
     */
    double calculateActivation();
    
    /**
     * Activation function
     * 
     * @return
     */
    double fireNeuron();
    
    /**
     * Activation function derivative 
     * @return
     */
    double fireNeuronDerivative();

    void calculateWeightAdjustments();
    void updateWeights();
    
    double getOutput();
    
    public void propagate();
    public void calculate();

    /*
     * For backpropagation
     */
    void setExpectedOutput(double d);
    
    double getNodeError();
    
    void setBias(double b);
    double getBias();
    void setBiasDelta(double bd);
    double getBiasDelta();
    
    /*
     * Learning rate that will be used in training
     */
    double getLearningRate();
    void setLearningRate(double learningRate);
}

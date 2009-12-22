package iweb2.ch5.classification.nn;

import iweb2.ch5.classification.nn.intf.Link;
import iweb2.ch5.classification.nn.intf.Node;

import java.util.ArrayList;
import java.util.List;

abstract class BaseNode implements Node {

    protected String nodeId;
    protected double x; // input value
    protected double y; // output value
    protected double bias;
    protected double biasDelta;
    protected List<Link> inlinks;
    protected List<Link> outlinks;
    
    protected double learningRate;
    
    public BaseNode(String nodeId) {
        this.nodeId = nodeId;
        this.inlinks = new ArrayList<Link>();
        this.outlinks = new ArrayList<Link>();
    }
    
    public void calculate() {
        this.x = calculateActivation();
        this.y = fireNeuron();
    }
    
    public void propagate() {
        for(Link outL : outlinks) {
            outL.setValue(y);
        }
    }

    public String getNodeId() {
        return nodeId;
    }

    public List<Link> getInlinks() {
        return inlinks;
    }

    public double getOutputValue() {
        return y;
    }

    public List<Link> getOutlinks() {
        return outlinks;
    }


    public double calculateActivation() {
        double result = bias;
        for(Link inL : inlinks) {
            result += inL.getWeight() * inL.getValue();
        }
        x = result;
        return x;
    }
    
    public abstract double fireNeuron();
    
    public abstract double fireNeuronDerivative();
    
    // Should it be at the link level?
    public double inputF(List<Link> inputs) {
        if( inputs == null || inputs.size() == 0 ) {
            return y;
        }
        else {
            double result = bias;
            for(Link inL : inputs) {
                result += inL.getWeight() * inL.getValue();
            }
            return result;
        }
    }

    public double getLastInput() {
        return x;
    }
    
    public double getLastOutput() {
        return y;
    }

    public double getOutput() {
        return y;
    }

    public void setOutput(double y) {
        this.y = y;
    }
    
    // 
    public double getNodeError() {
        // For output node
        if( outlinks == null || outlinks.size() == 0) {
            double d = expectedOutput;
            /*
             * Assuming E = 1/2 * ( d - y )^2
             */
            //return (d - y) * (1 - y) * y;
            return (d - y) * fireNeuronDerivative();

        } else { // for hidden node
            double s = 0.0;
            
            for(Link outlink : outlinks) {
                Node node = outlink.getToNode();
                s += node.getNodeError() * outlink.getWeight();
            }
            
            return fireNeuronDerivative() * s;  
        }
    }
    
    public void calculateWeightAdjustments() {
        double err = getNodeError();

        for(Link link : getInlinks()) {
            double y = link.getValue();                
            double dW = learningRate *  y * err;
            link.setWeightDelta(link.getWeightDelta() + dW);
        }
            
        // Bias adjustments
        setBiasDelta( getBiasDelta() + learningRate * 1 * err);
    }

    public void updateWeights() {    

        for(Link link : getInlinks()) {
            link.setWeight( link.getWeight() + link.getWeightDelta() );
            link.setWeightDelta(0.0);
        }

        // Bias adjustments
        setBias( getBias() + getBiasDelta());
        setBiasDelta(0.0);
    }
    
    /*
     * Used in training mode.
     */
    private double expectedOutput;
    
    public void setExpectedOutput(double d) {
        this.expectedOutput = d;
    }

    public void setBias(double b) {
        this.bias = b;
    }
    
    public double getBias() {
        return bias;
    }
    
    public void setBiasDelta(double db) {
        this.biasDelta = db;
    }
    
    public double getBiasDelta() {
        return biasDelta;
    }
    
    public void addInlink(Link inlink) {
        inlinks.add(inlink);
    }

    public void addOutlink(Link outlink) {
        outlinks.add(outlink);
    }
    
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
    
    public double getLearningRate() {
        return this.learningRate;
    }
}

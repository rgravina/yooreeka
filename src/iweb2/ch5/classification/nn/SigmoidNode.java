package iweb2.ch5.classification.nn;


public class SigmoidNode extends BaseNode {
    
    private static final long serialVersionUID = 5289776407864851871L;

    public SigmoidNode(String nodeId) {
        super(nodeId);
    }
    
    @Override
	public double fireNeuron() {
        // Sigmoid
    	y = Math.tanh(x);
        return y;
    }
    
    @Override
	public double fireNeuronDerivative() {
        return (1 - y*y);
    }
}

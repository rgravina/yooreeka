package iweb2.ch5.classification.nn;


public class LinearNode extends BaseNode {

    private static final long serialVersionUID = -6052548906001921511L;
    
    private double a = 0.0;
    private double b = 0.0;
    
    public LinearNode(String nodeId) {
        this(nodeId, 1.0, 0.0);
    }

    public LinearNode(String nodeId, double a, double b) {
        super(nodeId);
        this.a = a;
        this.b = b;
    }

    @Override
	public double fireNeuronDerivative() {
        return a;
    }
    
    @Override
    public double fireNeuron() {
        return a * x + b;
    }
}

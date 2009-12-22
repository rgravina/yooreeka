package iweb2.ch5.classification.nn;

import iweb2.ch5.classification.nn.intf.Link;
import iweb2.ch5.classification.nn.intf.Node;


public class BaseLink implements Link {

    private static final long serialVersionUID = 6462508677299269035L;
    
    private Node fromNode;
    private Node toNode;
    private double value;
    private double weight;
    private double weightDelta;
    
    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public void setFromNode(Node unit) {
        this.fromNode = unit;
    }

    public void setToNode(Node unit) {
        this.toNode = unit;
    }

    public void setValue(double x) {
        this.value = x;
    }

    public void setWeight(double w) {
        this.weight = w;
    }

    public double getWeightDelta() {
        return weightDelta;
    }

    public void setWeightDelta(double dw) {
        weightDelta = dw;
    }
}

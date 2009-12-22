package iweb2.ch5.classification.nn.intf;

public interface Link extends java.io.Serializable {
    double getValue();
    double getWeightDelta();
    double getWeight();
    Node getFromNode();
    Node getToNode();
    
    void setWeightDelta(double dw);
    void setWeight(double w);
    void setValue(double x);    
    void setFromNode(Node fromNode);
    void setToNode(Node toNode);
}

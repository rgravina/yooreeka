package iweb2.ch5.classification.nn;

import java.util.ArrayList;
import java.util.List;

import iweb2.ch5.classification.nn.intf.Layer;
import iweb2.ch5.classification.nn.intf.Link;
import iweb2.ch5.classification.nn.intf.Node;

public class BaseLayer implements Layer {

    private static final long serialVersionUID = -1482668413756729940L;
    
    private int layerId;
    private List<Node> nodes;
    
    public BaseLayer(int layerId) {
        this.layerId = layerId;
        this.nodes = new ArrayList<Node>();
    }
    
    public int getId() {
        return layerId;
    }

    public double[] getValues() {

        double[] y = new double[nodes.size()];

        for(int i = 0, n = y.length; i < n; i++) {
            y[i] = nodes.get(i).getOutput();
        }
        
        return y;
    }

    public void calculateWeightAdjustments() {
        for(Node node : nodes) {
            node.calculateWeightAdjustments();
        }
    }
    
    public void updateWeights() {
        for(Node node : nodes) {
            node.updateWeights();
        }
    }
    
    public void setExpectedOutputValues(double[] d) {
        if( nodes.size() != d.length ) {
            throw new RuntimeException("Invalid layer configuration. " +
                    "Layer id: " + layerId +
                    ", Expected number of nodes: " + d.length + 
                    ", Actual number of nodes: " + nodes.size());
        }
        
        for(int i =  0, n = d.length; i < n; i++) {
            Node node = nodes.get(i);
            node.setExpectedOutput(d[i]);
        }
    }
    
    public void setInputValues(double[] x) {
        if( nodes.size() != x.length ) {
            throw new RuntimeException("Invalid layer configuration. " +
                    "Layer id: " + layerId +
                    ", Expected number of nodes: " + x.length + 
                    ", Actual number of nodes: " + nodes.size());
        }
        
        for(int i =  0, n = x.length; i < n; i++) {
            Node node = nodes.get(i);
            Link inlink = node.getInlinks().get(0); 
            inlink.setValue(x[i]);
        }
    }
    
    public void addNode(Node n) {
        nodes.add(n);
    }
    
    public List<Node> getNodes() {
        return nodes;
    }

    public String getType() {
        return "";
    }
    
    public void calculate() {
        for(Node node : nodes) {
            node.calculate();
        }
    }
    
    public void propagate() {
        for(Node node : nodes) {
            node.propagate();
        }
    }
    
    public void printWeights() {
        for(Node n : nodes) {
            for(Link link : n.getInlinks()) {
                System.out.println(link.getFromNode().getNodeId() + "->" + n.getNodeId() + ":" + link.getWeight());
            }
        }
    }

}

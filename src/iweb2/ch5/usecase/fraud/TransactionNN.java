package iweb2.ch5.usecase.fraud;

import iweb2.ch5.classification.nn.BaseNN;
import iweb2.ch5.classification.nn.intf.Layer;

public class TransactionNN extends BaseNN {

    private static final long serialVersionUID = -3840865001527729603L;
    

    public TransactionNN(String name) {
        super(name);
        
        createNN351();
    }
    
    /*
     * Creates: 3 -> 5 -> 1 network. 
     */
    private void createNN351() {
        
        
        // 1. Define Layers, Nodes and Node Biases
        Layer inputLayer = createInputLayer(
                0, // layer id 
                3  // number of nodes 
                );

        Layer hiddenLayer = createHiddenLayer(
                1, // layer id 
                5, // number of nodes
                new double[] {1, 1.5, 1, 0.5, 1} // node biases
                );
        
        Layer outputLayer = createOutputLayer(
                2, // layer id 
                1, // number of nodes 
                new double[] {1.5} // node biases
                );        
        
        
        setInputLayer(inputLayer);
        setOutputLayer(outputLayer);
        addHiddenLayer(hiddenLayer);

        // 2. Define links and weights between nodes
        // Id format: <layerId:nodeIdwithinLayer>
        
        // Weights for links from Input Layer to Hidden Layer
        setLink("0:0", "1:0", 0.25);
        setLink("0:0", "1:1", -0.5);
        setLink("0:0", "1:2", 0.25);
        setLink("0:0", "1:3", 0.25);
        setLink("0:0", "1:4", -0.5);
        
        setLink("0:1", "1:0", 0.25);
        setLink("0:1", "1:1", -0.5);
        setLink("0:1", "1:2", 0.25);
        setLink("0:1", "1:3", 0.25);
        setLink("0:1", "1:4", -0.5);
                        
        setLink("0:2", "1:0", 0.25);
        setLink("0:2", "1:1", -0.5);
        setLink("0:2", "1:2", 0.25);
        setLink("0:2", "1:3", 0.25);
        setLink("0:2", "1:4", -0.5);

        // Weights for links from Hidden Layer to Output Layer
        
        setLink("1:0", "2:0", -0.5);
        setLink("1:1", "2:0", 0.5);
        setLink("1:2", "2:0", -0.5);
        setLink("1:3", "2:0", -0.5);
        setLink("1:4", "2:0", 0.5);
        
        if ( isVerbose() ) {
        	System.out.println("NN created");
        }
        
    }
  
}

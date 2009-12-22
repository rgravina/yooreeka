package iweb2.ch6.usecase.credit;

import iweb2.ch5.classification.nn.BaseNN;
import iweb2.ch5.classification.nn.intf.Layer;

public class UserCreditNN extends BaseNN {

    private static final long serialVersionUID = 5049921699478904263L;

    public UserCreditNN(String name) {
        super(name);
        
        create();
    }
    
    public void create() {
        createNN_11_7_5();
    }
  
    private void createNN_11_7_5() {

        
        // 1. Define Layers, Nodes and Node Biases
        Layer inputLayer = createInputLayer(
                0, // layer id 
                11  // number of nodes 
                );

        Layer hiddenLayer = createHiddenLayer(
                1, // layer id 
                7, // number of nodes
                new double[] { 0.5, -1, 1.5, 0.5, 1, -0.2, 0.1 } // node biases
                );
        
        Layer outputLayer = createOutputLayer(
                2, // layer id 
                5, // number of nodes 
                new double[] {-1.5, 0.5, -1, 0.5, 1} // node biases
                );        
        
        
        setInputLayer(inputLayer);
        setOutputLayer(outputLayer);
        addHiddenLayer(hiddenLayer);

        // 2. Define links and weights between nodes
        // Id format: <layerId:nodeIdwithinLayer>
        
        // Weights for links from Input Layer to Hidden Layer
        setLink("0:0", "1:0", 0.25);
        setLink("0:0", "1:1", -0.7);
        setLink("0:0", "1:2", 0.25);
        setLink("0:0", "1:3", 0.25);
        setLink("0:0", "1:4", -0.3);
        setLink("0:0", "1:5", 0.25);
        setLink("0:0", "1:6", -0.5);
        
        setLink("0:1", "1:0", 0.25);
        setLink("0:1", "1:1", -0.5);
        setLink("0:1", "1:2", 0.25);
        setLink("0:1", "1:3", 0.25);
        setLink("0:1", "1:4", 0.5);
        setLink("0:1", "1:5", 0.25);
        setLink("0:1", "1:6", 0.5);
        
        setLink("0:2", "1:0", 0.25);
        setLink("0:2", "1:1", -0.5);
        setLink("0:2", "1:2", 0.25);
        setLink("0:2", "1:3", 0.25);
        setLink("0:2", "1:4", -0.5);
        setLink("0:2", "1:5", 0.25);
        setLink("0:2", "1:6", -0.5);

        setLink("0:3", "1:0", 0.25);
        setLink("0:3", "1:1", -0.5);
        setLink("0:3", "1:2", -0.25);
        setLink("0:3", "1:3", -0.25);
        setLink("0:3", "1:4", -0.5);
        setLink("0:3", "1:5", 0.25);
        setLink("0:3", "1:6", 0.5);

        setLink("0:4", "1:0", 0.25);
        setLink("0:4", "1:1", -0.5);
        setLink("0:4", "1:2", 0.25);
        setLink("0:4", "1:3", 0.25);
        setLink("0:4", "1:4", -0.5);
        setLink("0:4", "1:5", 0.25);
        setLink("0:4", "1:6", -0.5);
        
        setLink("0:5", "1:0", 0.25);
        setLink("0:5", "1:1", -0.5);
        setLink("0:5", "1:2", 0.25);
        setLink("0:5", "1:3", 0.25);
        setLink("0:5", "1:4", -0.5);
        setLink("0:5", "1:5", 0.25);
        setLink("0:5", "1:6", -0.5);

        setLink("0:6", "1:0", -0.25);
        setLink("0:6", "1:1", 0.5);
        setLink("0:6", "1:2", -0.25);
        setLink("0:6", "1:3", 0.25);
        setLink("0:6", "1:4", -0.5);
        setLink("0:6", "1:5", 0.25);
        setLink("0:6", "1:6", 0.5);

        setLink("0:7", "1:0", 0.25);
        setLink("0:7", "1:1", -0.5);
        setLink("0:7", "1:2", 0.25);
        setLink("0:7", "1:3", 0.25);
        setLink("0:7", "1:4", -0.5);
        setLink("0:7", "1:5", 0.25);
        setLink("0:7", "1:6", -0.5);

        setLink("0:8", "1:0", 0.25);
        setLink("0:8", "1:1", -0.5);
        setLink("0:8", "1:2", 0.25);
        setLink("0:8", "1:3", 0.25);
        setLink("0:8", "1:4", -0.5);
        setLink("0:8", "1:5", 0.25);
        setLink("0:8", "1:6", 0.8);

        setLink("0:9", "1:0", 0.25);
        setLink("0:9", "1:1", 0.5);
        setLink("0:9", "1:2", -0.25);
        setLink("0:9", "1:3", -0.25);
        setLink("0:9", "1:4", 0.5);
        setLink("0:9", "1:5", 0.25);
        setLink("0:9", "1:6", 0.5);

        setLink("0:10", "1:0", 0.25);
        setLink("0:10", "1:1", -0.5);
        setLink("0:10", "1:2", 0.25);
        setLink("0:10", "1:3", 0.25);
        setLink("0:10", "1:4", 0.5);
        setLink("0:10", "1:5", 0.25);
        setLink("0:10", "1:6", -0.5);
        
        // Weights for links from Hidden Layer to Output Layer
        
        setLink("1:0", "2:0", -0.5);
        setLink("1:1", "2:0", 0.5);
        setLink("1:2", "2:0", 0.5);
        setLink("1:3", "2:0", 0.5);
        setLink("1:4", "2:0", 0.5);
        setLink("1:5", "2:0", -0.5);
        setLink("1:6", "2:0", 0.5);
        
        setLink("1:0", "2:1", -0.5);
        setLink("1:1", "2:1", 0.5);
        setLink("1:2", "2:1", -0.5);
        setLink("1:3", "2:1", -0.5);
        setLink("1:4", "2:1", 0.5);
        setLink("1:5", "2:1", -0.5);
        setLink("1:6", "2:1", 0.5);
        
        setLink("1:0", "2:2", -0.5);
        setLink("1:1", "2:2", 0.5);
        setLink("1:2", "2:2", -0.5);
        setLink("1:3", "2:2", -0.5);
        setLink("1:4", "2:2", 0.5);
        setLink("1:5", "2:2", -0.5);
        setLink("1:6", "2:2", 0.5);

        setLink("1:0", "2:3", -0.5);
        setLink("1:1", "2:3", 0.5);
        setLink("1:2", "2:3", -0.5);
        setLink("1:3", "2:3", -0.5);
        setLink("1:4", "2:3", 0.5);
        setLink("1:5", "2:3", -0.5);
        setLink("1:6", "2:3", 0.5);

        setLink("1:0", "2:4", -0.5);
        setLink("1:1", "2:4", 0.5);
        setLink("1:2", "2:4", -0.5);
        setLink("1:3", "2:4", -0.5);
        setLink("1:4", "2:4", 0.5);
        setLink("1:5", "2:4", -0.5);
        setLink("1:6", "2:4", 0.5);
        
        if (isVerbose()) {
        	System.out.println("NN created");
        }
        
    }
    
}

package iweb2.ch5.classification.nn;

import java.util.Arrays;

import iweb2.ch5.classification.nn.intf.Layer;


public class XORNetwork extends BaseNN {

    private static final long serialVersionUID = -511246579251846775L;
    

    public XORNetwork(String name) {
        super(name);
    }

    /*
     * Creates: 2 -> 3 -> 1 network. 
     */
    public void create() {
        
        
        // 1. Define Layers, Nodes and Node Biases
        Layer inputLayer = createInputLayer(
                0, // layer id 
                2  // number of nodes 
                );

        Layer hiddenLayer = createHiddenLayer(
                1, // layer id 
                3, // number of nodes
                new double[] {1, 1.5, 1} // node biases
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
        setLink("0:0", "1:0", 0.25);
        setLink("0:0", "1:1", -0.5);
        setLink("0:0", "1:2", 0.25);
        
        setLink("0:1", "1:0", 0.25);
        setLink("0:1", "1:1", -0.5);
        setLink("0:1", "1:2", 0.25);
                
        setLink("1:0", "2:0", -0.5);
        setLink("1:1", "2:0", 0.5);
        setLink("1:2", "2:0", -0.5);
        
        System.out.println("NN created");
        
    }
    
    public static void main(String[] args) {
        XORNetwork nn = new XORNetwork("XOR Test");

        nn.create();
        
        System.out.println("Classification using untrained network:");
        
        double[] x = {0, 0};
        double[] y = nn.classify(x);
        
        //Results before training
        
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));
        
        x = new double[] {0, 1};
        y = nn.classify(x);
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));
        
        x = new double[] {1, 0};
        y = nn.classify(x);
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));
        
        x = new double[] {1, 1};
        y = nn.classify(x);
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));        

        
        System.out.println("Training...");

       double nearZero = 0;
       for(int i = 0; i < 1024; i++) {
    	       	   
           nn.train(new double[] {nearZero, nearZero}, new double[] {0.0});
           nn.train(new double[] {1-nearZero, 1-nearZero}, new double[] {0.0});
           nn.train(new double[] {1-nearZero, nearZero}, new double[] {1.0});
           nn.train(new double[] {nearZero, 1-nearZero}, new double[] {1.0});
    	   nearZero = 0.0d + Math.random()*0.0001d;

           //nn.printWeights();
       }
       
        System.out.println("Trained");
        
        
        // After training
        
        System.out.println("Classification using trained network:");
        
        x = new double[] {0, 0};
        y = nn.classify(x);
        
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));
        
        x = new double[] {0, 1};
        y = nn.classify(x);
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));
        
        x = new double[] {1, 0};
        y = nn.classify(x);
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));
        
        x = new double[] {1, 1};
        y = nn.classify(x);
        System.out.println(Arrays.toString(x) + " -> " + Arrays.toString(y));        
        
    }
    

}

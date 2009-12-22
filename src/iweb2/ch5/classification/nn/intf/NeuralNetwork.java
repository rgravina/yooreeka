package iweb2.ch5.classification.nn.intf;


public interface NeuralNetwork {

	public abstract void setInputLayer(Layer inputLayer);

	public abstract void addHiddenLayer(Layer hiddenLayer);

	public abstract double[] classify(double[] x);

	// trains NN with one training sample at a time
	public abstract void train(double[] tX, double[] tY);

	public abstract void printWeights();

	public abstract Layer createInputLayer(int layerId, int nNodes);

	public abstract Layer createHiddenLayer(int layerId, int nNodes,
			double[] bias);

	public abstract Layer createOutputLayer(int layerId, int nNodes,
			double[] bias);

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param name the name to set
	 */
	public abstract void setName(String name);

	public abstract int getInputNodeCount();

	public abstract int getOutputNodeCount();

}
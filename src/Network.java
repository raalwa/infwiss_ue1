import java.lang.reflect.Array;
import java.util.ArrayList;

public class Network {
    private ArrayList<DataVector> data;
    private GroundTruth oracle;
    private int[] numberOfNodes = {32, 2};

    private InputLayer inputLayer;
    private HiddenLayer hiddenLayer;
    private OutputLayer outputLayer;

    private ArrayList<Double> errorVector;

    public Network(ArrayList<DataVector> data, GroundTruth oracle){
        this.oracle = oracle;
        this.data = data;
        init();
    }

    public void init(){
        int inputSize = data.get(0).getVector().size();

        inputLayer = new InputLayer(inputSize);
        hiddenLayer = new HiddenLayer(inputSize, numberOfNodes[0]);
        outputLayer = new OutputLayer(numberOfNodes[0], numberOfNodes[1]);

        inputLayer.setChild(hiddenLayer);
        hiddenLayer.setChild(outputLayer);
        hiddenLayer.setParent(inputLayer);
        outputLayer.setParent(hiddenLayer);

    }

    public void feedSample(){
        //randomise

        //for(int i = 0; i < data.size(); i++){
        ArrayList<Double> output = inputLayer.feedSample(data.get(0).getVector());
            System.out.println("Output-Vektor: " + output);
            errorVector = error(output,0);
            ArrayList<Double> predictedVector = predict(output, 0);

            System.out.println("Error-Vektor: " + errorVector);
            System.out.println("Predicted-Vektor: " + predictedVector);
        //}
    }

    public void backprop(){
        outputLayer.backprop(errorVector);
        hiddenLayer.backprop(outputLayer.getSumList());
    }

    //error as vector
    public ArrayList<Double> error(ArrayList<Double> output, int dataIndex){
        ArrayList<Double> errorVector = new ArrayList<>();
        for(int dimension = 0; dimension < output.size(); dimension++){
            errorVector.add(oracle.getTruths().get(dataIndex)[dimension] - output.get(dimension));
        }
        return errorVector;
    }

    public ArrayList<Double> predict(ArrayList<Double> output, int dataIndex){
        //rounded version of our output vector
        ArrayList<Double> roundedVector = output;
        for(int i = 0; i < output.size(); i++){
            double roundedValue = Math.round(roundedVector.get(i));
            roundedVector.set(i, roundedValue);
        }
        return roundedVector;
    }
}

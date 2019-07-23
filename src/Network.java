import java.util.ArrayList;

public class Network {
    private ArrayList<DataVector> data;
    private ArrayList<GroundTruth> oracle;
    private int[] numberOfNodes = {32, 2};

    private InputLayer inputLayer;
    private HiddenLayer hiddenLayer;
    private OutputLayer outputLayer;


    public void init(){

        //init data from excel

        //init GroundTruth from excel

        int inputSize = data.get(0).getVector().size();

        InputLayer inputLayer = new InputLayer(inputSize);
        HiddenLayer hiddenLayer = new HiddenLayer(inputSize, numberOfNodes[0]);
        OutputLayer outputLayer = new OutputLayer(numberOfNodes[0], numberOfNodes[1]);

        inputLayer.setChild(hiddenLayer);
        hiddenLayer.setChild(outputLayer);
        hiddenLayer.setParent(inputLayer);
        outputLayer.setParent(hiddenLayer);

    }

    public void feedSample(){
        //randomise
        for(int i = 0; i < data.size(); i++){
            DataVector output = inputLayer.feedSample(data.get(i));
            error(output,i);
            predict(output, i);
        }
    }

    public void backprop(){
        ArrayList<Double> errorVector = error();
        outputLayer.backprop(errorVector);
        hiddenLayer.backprop();
    }

    //error as vector


    //error added up
    public ArrayList<Double> error(DataVector output, int dataIndex){
        ArrayList<Double> errorVector = new ArrayList<>();
        for(int dimension = 0; dimension < output.getVector().size(); dimension++){
            errorVector.add(oracle.get(dataIndex).getTruths().get(dimension) - output.getVector().get(dimension));
        }
        return errorVector;
    }

    public DataVector predict(DataVector output, int dataIndex){
        //rounded version of our output vector
        DataVector roundedVector = output;
        for(int i = 0; i < output.getVector().size(); i++){
            double roundedValue = Math.round(roundedVector.getVector().get(i));
            roundedVector.getVector().set(i, roundedValue);
        }
        return roundedVector;
    }
}

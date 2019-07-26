import java.lang.reflect.Array;
import java.util.ArrayList;

public class Network {
    private ArrayList<DataVector> data;
    private GroundTruth oracle;
    private int[] numberOfNodes = {32, 4};

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

        //number of nodes in InputLayer equals number of dimensions of data
        inputLayer = new InputLayer(inputSize);
        hiddenLayer = new HiddenLayer(inputSize, numberOfNodes[0]);
        outputLayer = new OutputLayer(numberOfNodes[0], numberOfNodes[1]);

        //allows communication between layers (for feedForward)
        inputLayer.setChild(hiddenLayer);
        hiddenLayer.setChild(outputLayer);
        hiddenLayer.setParent(inputLayer);
        outputLayer.setParent(hiddenLayer);

    }

    public void epoch(int epoches){
        int c = 0; //counts correct predictions
        for (int k = 0; k < epoches; k++){
            //trains with 1/4 of the dataset
            for(int j = 0; j < data.size();j++){
                //chooses random data from dataset
                int random = (int)Math.round(Math.random()*(data.size()-1));
                feedSample(random);
                backprop();
            }
            //tests with 1/4 of the dataset
            for(int j = 0; j < data.size()/4; j++){
                //chooses random data to test with
                int random = (int)Math.round(Math.random()*(data.size()-1));
                if(feedSample(random)){
                    c++;
                }
            }
            System.out.println("------------------------------------------------");
            System.out.println(c + "/" + data.size()/4 + " der predictions waren korrekt");
            c = 0;
        }
    }

    public boolean feedSample(int i){
        //forwards data to first layer
        ArrayList<Double> output = inputLayer.feedSample(data.get(i).getVector());
        errorVector = error(output,i);
        ArrayList<Double> predictedVector = predict(output);

        /*System.out.println("----------------------------------------------------------------------");
        System.out.println("Ground-Truth: ["+ oracle.getTruths().get(i)[0] +", " + oracle.getTruths().get(i)[1] + ", " + oracle.getTruths().get(i)[2] + ", " + oracle.getTruths().get(i)[3] + "]");
        System.out.println("Output-Vektor: "+ output);
        System.out.println("Error-Vektor: " + errorVector);
        System.out.println("Predicted-Vektor: " + predictedVector);*/

        //returns how many inputs are predicted correctly
        boolean guess = true;
        for(int l = 0; l < errorVector.size(); l++){
            if(Math.abs(errorVector.get(l))>0.5){
                guess = false;
            }
        }
        return guess;
    }

    public void backprop(){
        //outputLayer needs error values to compute new weights
        outputLayer.backprop(errorVector);

        //part of the backprop in hiddenLayer is computed in outputLayer (because some of the values from outputLayer are needed, e.g. derivations, errors and weights)
        hiddenLayer.backprop(outputLayer.getSumList());
        //one value of the sumList equals: SUM[error(I)*derivation(I)*weights(output -> hidden)]
    }

    //error as vector
    public ArrayList<Double> error(ArrayList<Double> output, int dataIndex){
        //errorvector consists of all target values minus output values
        ArrayList<Double> errorVector = new ArrayList<>();
        for(int dimension = 0; dimension < output.size(); dimension++){
            errorVector.add(oracle.getTruths().get(dataIndex)[dimension] - output.get(dimension));
        }
        return errorVector;
    }

    public ArrayList<Double> predict(ArrayList<Double> output){
        //rounded version of our output vector
        ArrayList<Double> roundedVector = output;
        for(int i = 0; i < output.size(); i++){
            double roundedValue = Math.round(roundedVector.get(i));
            roundedVector.set(i, roundedValue);
        }
        return roundedVector;
    }
}

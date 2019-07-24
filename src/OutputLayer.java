import java.lang.reflect.Array;
import java.util.ArrayList;

public class OutputLayer extends Layer {

    private ArrayList<Double> predecessorValues;
    //sumList stores the sum of error*derivation*weights for hiddenLayer
    private ArrayList<Double> sumList;

    public OutputLayer(int v, int a){
        super(v,a);
    }

    @Override
    public ArrayList<Double> feedSample(ArrayList<Double> predecessorValues){
        this.predecessorValues = predecessorValues;
        ArrayList<Double> dimensions = new ArrayList<Double>();
        for (int i = 0; i < neurons.size(); i++){
            //jedem Neuron die passenden Gewichte und den hiddenLayer-vector übergeben
            ArrayList<Double> correspondingWeights = new ArrayList<Double>();
            for (int j = 0; j < predecessorValues.size(); j++){
                correspondingWeights.add(weights[j][i]);
            }
            double dimension = neurons.get(i).feedForward(predecessorValues,correspondingWeights);
            dimensions.add(dimension);
        }
        return dimensions;
    }

    public void backprop(ArrayList<Double> errorVector){
        //k iteriert über die Knoten im Output-Layer
        for(int k = 0; k<weights.length; k++){
            double error = errorVector.get(k);
            double derivation = neurons.get(k).getDerivation();
            //j iteriert über die Gewichte vom HiddenLayer zum jeweiligen Knoten
            for(int j = 0; j<weights[k].length; j++){
                weights[k][j] = weights[k][j] - learningrate * error * derivation * predecessorValues.get(j);
            }
        }
        populateSumlist(errorVector);
    }

    public void populateSumlist(ArrayList<Double> errorVector){
        double[][] computedWeights = weights;
        for(int k = 0; k < computedWeights.length; k++){
            double error = errorVector.get(k);
            double derivation = neurons.get(k).getDerivation();
            for(int j = 0; j <computedWeights[k].length; j++){
                computedWeights[k][j] *= error*derivation;
            }
        }
        for(int i = 0; i < computedWeights[0].length; i++){
            double sum = 0;
            for(int k = 0; k < computedWeights.length; k++){
                sum += computedWeights[k][i];
            }
            sumList.add(sum);
        }
    }

    public ArrayList<Double> getSumList(){
        return sumList;
    }
}

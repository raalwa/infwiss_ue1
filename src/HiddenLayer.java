import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class HiddenLayer extends Layer {
    public HiddenLayer(int v, int a){
        super(v,a);
    }

    private ArrayList<Double> predecessorValues;

    @Override
    public ArrayList<Double> feedSample(ArrayList<Double> predecessorValues){
        //System.out.println("Weights in Hidden Layer: " + weights[0][0]);
        this.predecessorValues = predecessorValues;
        ArrayList<Double> dimensions = new ArrayList<Double>();
        for (int i = 0; i < neurons.size(); i++){
            //jedem Neuron die passenden Gewichte und den input-vector übergeben
            ArrayList<Double> correspondingWeights = new ArrayList<Double>();
            for (int j = 0; j < predecessorValues.size() ; j++){
                correspondingWeights.add(weights[j][i]);
            }
            double dimension = neurons.get(i).feedForward(predecessorValues,correspondingWeights);
            dimensions.add(dimension);
            //System.out.println("Dimensionen des Vektors von Hidden an Output: " + dimension);
        }

        return child.feedSample(dimensions);
    }

    public void backprop(ArrayList<Double> sumList){
        //Iteration über eigene Gewichte
        //Vorgängerwerte
        //lernrate
        //Ableitungen der Knoten
        //Summenliste aus Output-Layer
        for(int h = 0; h < neurons.size(); h++){
            double currentDerivation = neurons.get(h).getDerivation();
            double currentSum = sumList.get(h);
            for(int c = 0; c < predecessorValues.size(); c++){
                double currentInputValue = predecessorValues.get(c);
                double currentWeight = weights[c][h];
                //System.out.println("Weight vor backprop: "+ currentWeight);
                weights[c][h] = currentWeight - learningrate*currentInputValue*currentDerivation*currentSum;
                //System.out.println("Weight nach backprop: "+ weights[c][h]);
            }
        }
    }
}

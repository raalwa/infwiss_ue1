import java.util.ArrayList;

public class OutputLayer extends Layer {
    private DataVector predecessorValues;
    public OutputLayer(int v, int a){
        super(v,a);
    }

    @Override
    public DataVector feedSample(DataVector predecessorValues){
        this.predecessorValues = predecessorValues;
        ArrayList<Double> dimensions = new ArrayList<Double>();
        for (int i = 0; i < neurons.size(); i++){
            //jedem Neuron die passenden Gewichte und den vector übergeben
            ArrayList<Double> correspondingWeights = new ArrayList<Double>();
            for (int j = 0; j <weights.length; j++){
                correspondingWeights.add(weights[i][j]);
            }
            double dimension = neurons.get(i).feedForward(predecessorValues.getVector(),correspondingWeights);
            dimensions.add(dimension);
        }
        return new DataVector(dimensions);
    }

    public void backprop(ArrayList<Double> errorVector){
        //k iterioert über die Knoten im Output-Layer
        for(int k = 0; k<weights.length; k++){
            double error = errorVector.get(k);
            double derivation = neurons.get(k).getDerivation();
            double sum = error * derivation;
            //j iteriert über die Gewichte vom HiddenLayer zum jeweiligen Knoten
            for(int j = 0; j<weights[k].length; j++){
                weights[k][j] = weights[k][j] - learningrate * error * derivation * predecessorValues.getVector().get(j);

            }
        }
    }
}

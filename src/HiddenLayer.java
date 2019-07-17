import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class HiddenLayer extends Layer {
    public HiddenLayer(int v, int a){
        super(v,a);
    }

    @Override
    public DataVector feedSample(DataVector predecessorValues){
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
        return child.feedSample(new DataVector(dimensions));
    }
}

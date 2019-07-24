import java.lang.reflect.Array;
import java.util.ArrayList;

public class InputLayer extends Layer {
    public InputLayer(int a){
        super(a);
    }

    @Override
    public ArrayList<Double> feedSample(ArrayList<Double> vector){
        return child.feedSample(vector);
    }
}

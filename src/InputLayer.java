import java.util.ArrayList;

public class InputLayer extends Layer {
    public InputLayer(int a){
        super(a);
    }

    @Override
    public DataVector feedSample(DataVector vector){
        return child.feedSample(vector);
    }
}

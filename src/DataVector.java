import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataVector {
    private ArrayList<Double> vector;

    public DataVector(ArrayList<Double> vector){
        vector.add(-1.0);
        this.vector = vector;
    }

    public ArrayList<Double> getVector(){
        return vector;
    }
}

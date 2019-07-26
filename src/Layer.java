import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Layer {
    protected ArrayList<Neuron> neurons;
    protected double[][] weights;
    protected Layer parent;
    protected Layer child;
    protected double learningrate = 0.5;


    //constructor for Input-Layer
    public Layer(int a){
        neurons = new ArrayList<>();
        for(int i = 0; i < a; i++){
            addNeuron();
        }
    }

    //contructor for hidden-Layer and Output-Layer
    public Layer(int v, int a){
        this(a);
        weights = new double[v][a];
        for(int i = 0; i < weights.length;i++){
            for(int j = 0; j < weights[i].length;j++){
                weights[i][j] = Math.random();
            }
        }
    }

    protected void addNeuron(){
        Neuron e = new Neuron();
        neurons.add(e);
    }

    protected void setParent(Layer parent){
        this.parent = parent;
    }

    protected void setChild(Layer child){
        this.child = child;
    }

    protected abstract ArrayList<Double> feedSample(ArrayList<Double> vector);

    public double[][] getWeights() {
        return weights;
    }
}

import java.util.ArrayList;

public class Neuron {
    private double activation;
    private double derivation;

    public double feedForward(ArrayList<Double> inputs, ArrayList<Double> weights){
        double scalar = scalar(inputs, weights);
        activation = Math.tanh(scalar);
        derivation = 1 - Math.pow(activation, 2);
        return activation;
    }

    private double scalar(ArrayList<Double> inputs, ArrayList<Double> weights){
        double scalar = 0;
        for(int i = 0; i<inputs.size(); i++){
            scalar += inputs.get(i) * weights.get(i);
        }
        return scalar;
    }

    public double getActivation(){
        return activation;
    }

    public  double getDerivation(){
        return derivation;
    }
}
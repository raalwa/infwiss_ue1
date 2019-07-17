import java.util.ArrayList;

public class GroundTruth {
    private ArrayList<Double> truths; //vector

    public GroundTruth(ArrayList<Double> truths){
        this.truths = truths;
    }

    public ArrayList<Double> getTruths(){
        return truths;
    }

    //enum for kodierung
}

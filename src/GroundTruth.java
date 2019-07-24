import java.util.ArrayList;

public class GroundTruth {
    private double[] TCROSSING = {0,0};
    private final double[] PLUSCROSSING = {0,1};
    private final double[] RIGHTTURN = {1,0};
    private final double[] LEFTTURN = {1,1};

    private ArrayList<double[]> encodedTruths;

    public GroundTruth(ArrayList<String> decodedTruths){
        encodedTruths = new ArrayList<>();
        for(int i = 0; i < decodedTruths.size(); i++){
            if (decodedTruths.get(i).equals("tCrossing")){
                encodedTruths.add(TCROSSING);
            }
            else if(decodedTruths.get(i).equals("plusCrossing")){
                encodedTruths.add((PLUSCROSSING));
            }
            else if(decodedTruths.get(i).equals("rightTurn")){
                encodedTruths.add(RIGHTTURN);
            }
            else if(decodedTruths.get(i).equals("leftTurn")){
                encodedTruths.add(LEFTTURN);
            }
        }
    }

    public ArrayList<double[]> getTruths(){
        return encodedTruths;
    }
}

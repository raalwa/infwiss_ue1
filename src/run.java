import sun.nio.ch.Net;

import java.util.ArrayList;

public class run {
    private static final String FILENAME = "C:\\Users\\Raphael\\Documents\\infwiss_ue1\\src\\samples_4_classes_normalized.csv";

    private static void init(){
        CSVReader reader = new CSVReader(FILENAME);
        GroundTruth groundTruth =new GroundTruth(reader.getGroundTruth());
        ArrayList<DataVector> sampleVectors = reader.getsampleVectors();
        Network network = new Network(sampleVectors, groundTruth);
        network.epoch();
    }
    public static void main(String [] args) {
        init();
    }
}

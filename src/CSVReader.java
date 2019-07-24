import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

    private String filename;
    private ArrayList<String[]> allData;
    private ArrayList<String> GroundTruth;
    private ArrayList<DataVector> sampleVectors;

    public CSVReader(String filename){
        this.filename = filename;
        sampleVectors = new ArrayList<>();
        GroundTruth = new ArrayList<>();
        readData();
        seperateValues();
    }

    public void readData(){

        allData = new ArrayList<>();

        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");
                allData.add(values);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void seperateValues(){
        for(int i = 0; i<allData.size();i++){
            ArrayList<Double> dimensions = new ArrayList<>();
            for(int j = 0; j < allData.get(i).length-1; j++) {
                dimensions.add(Double.parseDouble(allData.get(i)[j]));
                if (j == allData.get(i).length - 2) {
                    GroundTruth.add(allData.get(i)[j + 1]);
                }
            }
            sampleVectors.add(new DataVector(dimensions));
        }


    }

    public ArrayList<String> getGroundTruth() {
        return GroundTruth;
    }

    public ArrayList<DataVector> getsampleVectors(){
        return sampleVectors;
    }


}
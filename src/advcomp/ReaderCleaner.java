package advcomp;
import java.io.*;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;


public class ReaderCleaner {

    String passengerDataLoc = "C:\\Users\\charlie\\IdeaProjects\\advanced_computing_map_reduce\\AComp_Passenger_data.csv";
    String airportsLatLongLoc = "C:\\Users\\charlie\\IdeaProjects\\advanced_computing_map_reduce\\Top30_airports_LatLong.csv";

    private ArrayList<PassengerData> PassengerDataList = new ArrayList<PassengerData>();
    private ArrayList<AirportData> AirportDataList = new ArrayList<AirportData>();

    void read(){

        BufferedReader br = null;

        PassengerData pd;
        AirportData ad;

        String line = "";

        try{
            // read passenger data CSV line by line
            br = new BufferedReader(new FileReader(passengerDataLoc));
            int skippedLineCount = 0;
            while((line = br.readLine()) != null) {

                String[] passDataLine = line.split(",");

                // remove empty lines and missing fields
                if (passDataLine.length == 6 && !isEmptyStringArray(passDataLine))
                {
                    // convert line to PassengerData object and add to array list
                    pd = new PassengerData(passDataLine[0],passDataLine[1],passDataLine[2],passDataLine[3],Integer.parseInt(passDataLine[4]),Integer.parseInt(passDataLine[5]));
                    PassengerDataList.add(pd);
                }
                else
                {
                    //System.out.print("empty or incorrect number of data fields \n");
                    skippedLineCount = skippedLineCount + 1;
                }
            }
            //System.out.print(skippedLineCount);
            //System.out.print("\n");

            br.close();

            // read airport data CSV line by line
            br = new BufferedReader(new FileReader(airportsLatLongLoc));
            while((line = br.readLine()) != null) {

                String[] airportDataLine = line.split(",");

                // remove empty lines and missing fields
                if(airportDataLine.length == 4 && !isEmptyStringArray(airportDataLine)){

                    // convert line to AirportData object and add to array list
                    ad = new AirportData(airportDataLine[0], airportDataLine[1],Double.parseDouble(airportDataLine[2]), Double.parseDouble(airportDataLine[3]));
                    AirportDataList.add(ad);
                }
                else
                {
                    //System.out.print("empty or incorrect number of data fields \n");
                }

            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
        e.printStackTrace();
        }
    }

    public boolean isEmptyStringArray(String[] array) {  //checks for empty strings and 0
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null || array[i].equals("0")) {
                //System.out.print("empty data fields");
                return true;
            }
        }
        return false;
    }

    public ArrayList<PassengerData> getPassengerDataList() {
        return PassengerDataList;
    }

    public ArrayList<AirportData> getAirportDataList() {
        return AirportDataList;
    }



}

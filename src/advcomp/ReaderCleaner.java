package advcomp;
import java.io.*;
import java.util.ArrayList;


public class ReaderCleaner {

    String passengerDataLoc = "C:\\Users\\charlie\\IdeaProjects\\advanced_computing_map_reduce\\AComp_Passenger_data.csv";
    String airportsLatLongLoc = "C:\\Users\\charlie\\IdeaProjects\\advanced_computing_map_reduce\\Top30_airports_LatLong.csv";

    void read(){

        BufferedReader br = null;
        ArrayList<String> PassengerData = new ArrayList<String>();

        try{
            br = new BufferedReader(new FileReader(passengerDataLoc));



        }
        catch (FileNotFoundException e){
            e.printStackTrace();

        }


    }

}

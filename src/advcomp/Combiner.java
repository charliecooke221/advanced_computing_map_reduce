package advcomp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combiner {
    int job;
    private HashMap<String, ArrayList<String>> originAirportPassengerIdsHM = new HashMap<String, ArrayList<String>>();


    public Combiner(int jobNo) {
        job = jobNo;
    }

    public void combineOriginAirportPassengerIdsHM(List<Map.Entry<String,String>> orgAirPassPairList){
        // combines the orginAir/passengerId tuples into hashmap

        ArrayList<String> originAirports = new ArrayList<>();

        // get array of airports
        for(int i = 0; i < orgAirPassPairList.size(); i++)
        {
            Map.Entry<String,String> pair = orgAirPassPairList.get(i);
            String airport = pair.getKey();
            if(!originAirports.contains(airport)){
                originAirports.add(airport);
            }
        }

        // for each airport get array of passenger ids and add to hashmap
        for(String orgAirport : originAirports){

            ArrayList<String> passengerIDs = new ArrayList<>();

            for(int i = 0; i < orgAirPassPairList.size(); i++)
            {
                if(orgAirport.equals(orgAirPassPairList.get(i).getKey())){
                    passengerIDs.add(orgAirPassPairList.get(i).getValue());
                }

                originAirportPassengerIdsHM.put(orgAirport,passengerIDs);
            }

        }

//        System.out.print(originAirportPassengerIdsHM);
//        System.out.print("\n");
    }

    // job1 take tuples hashmap and condense into key-sting array hashmap

    // job 2


    public HashMap<String, ArrayList<String>> getOriginAirportPassengerIdsHM() {
        return originAirportPassengerIdsHM;
    }
}

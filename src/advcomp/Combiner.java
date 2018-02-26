package advcomp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combiner {
    int job;
    private HashMap<String, ArrayList<String>> originAirportPassengerIdsHM = new HashMap<>();

    private HashMap<String, ArrayList<FlightData>> flightIdFlightDatasHM = new HashMap<>();


    public Combiner(int jobNo) {
        job = jobNo;
    }

    public void combineOriginAirportPassengerIdsHM(List<Map.Entry<String,String>> orgAirPassPairList){ // cannot overload due to raw type legacy
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
    }

    public void combineFlightIDPassengerData(List<Map.Entry<String,FlightData>> flightIdFlightDataPairList){ // combine flight id, flight data tuples to common keys

        //get all flight ids

        ArrayList<String> flightIds = new ArrayList<>();

        for(Map.Entry<String,FlightData> pair: flightIdFlightDataPairList ){

            String everyFlightId = pair.getKey();

            //System.out.print(everyFlightId + "\n");

            if(!flightIds.contains(everyFlightId)){
                flightIds.add(everyFlightId);
                //System.out.print("ADDED? \n");

            }
        }

        //System.out.print(flightIds + "\n");
        // for each flightID get array of flight data and add to hashmap

        for(String flightid : flightIds){
            ArrayList<FlightData> flightDatas = new ArrayList<>();

            for (Map.Entry<String,FlightData> flightPair: flightIdFlightDataPairList ){

                if(flightid.equals(flightPair.getKey())){
                    flightDatas.add(flightPair.getValue());
                }

                flightIdFlightDatasHM.put(flightid,flightDatas);
            }
        }
    }


    public HashMap<String, ArrayList<String>> getOriginAirportPassengerIdsHM() {
        return originAirportPassengerIdsHM;
    }

    public HashMap<String, ArrayList<FlightData>> getFlightIdFlightDatasHM() {
        return flightIdFlightDatasHM;
    }
}

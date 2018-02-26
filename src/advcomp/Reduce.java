package advcomp;

import java.util.ArrayList;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class Reduce {

    Map.Entry<String,Integer> outputJob1;

    ArrayList<ArrayList<String>> outputJob2;

    public Reduce(String primaryKey, ArrayList<Combiner> combiners, int job){ //overloaded constructors

        switch (job){
            case 1:
                countValuesForKey(primaryKey,combiners);
            case 2:
                FlightsForKey(primaryKey,combiners);
        }
    }

    private void countValuesForKey(String primaryKey,ArrayList<Combiner> combiners){
        int numberOfValuesForPrimaryKey = 0;

        for(Combiner combiner : combiners){ //aggregate outputJob1 of each combiner
            if(combiner.getOriginAirportPassengerIdsHM().get(primaryKey) != null){ //if combiner outputJob1 contains primary key
                numberOfValuesForPrimaryKey = numberOfValuesForPrimaryKey + combiner.getOriginAirportPassengerIdsHM().get(primaryKey).size(); //add values to count
            }
        }

        //System.out.print(primaryKey + " " + numberOfValuesForPrimaryKey + "\n");
        outputJob1 = new SimpleEntry<>(primaryKey, numberOfValuesForPrimaryKey); //save key/count pair
    }

    private void FlightsForKey(String primaryKey,ArrayList<Combiner> combiners){ // reduce primary key hashmap to array of all data

        ArrayList<FlightData> flights = new ArrayList<>();
        for(Combiner combiner : combiners) { //aggregate loop through all combiner outputs get relevent array

            //System.out.print(combiner.getFlightIdFlightDatasHM());  // combiners output is empty
            if(combiner.getFlightIdFlightDatasHM().get(primaryKey) != null){

                flights.addAll(combiner.getFlightIdFlightDatasHM().get(primaryKey));
                //System.out.print("test");
            }

            ArrayList<ArrayList<String>> flightsData = new ArrayList<>();
            for(FlightData flight: flights){
                //System.out.print(flight);
                ArrayList<String> flightData = new ArrayList<>();
                flightData.add(primaryKey);
                flightData.add(flight.getPassengerId());
                flightData.add(flight.getOriginAirport());
                flightData.add(flight.getDestinationAirport());
                flightData.add(flight.getDepartureTime());
                flightData.add(flight.getArrivalTime());
                flightData.add(flight.getTotalFlightTime());

                flightsData.add(flightData);
            }
            outputJob2 = flightsData;
        }
    }

    public Map.Entry<String, Integer> getOutputJob1() {
        return outputJob1;
    }

    public ArrayList<ArrayList<String>> getOutputJob2() {
        return outputJob2;
    }
}

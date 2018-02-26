package advcomp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;

public class Mapper implements Runnable {

    private Thread t;
    private int job;
    private String threadName;
    private List<PassengerData> passengerDataList = new ArrayList<PassengerData>();
    private List<AirportData> airportDataList = new ArrayList<AirportData>();
    // private HashMap<String, String> hm = new HashMap<String, String>();
    private List<Map.Entry<String,String>> orgAirport_passengerIdPairList = new ArrayList<>();
    private List<Map.Entry<String,FlightData>> flightID_passengerDataList = new ArrayList<>();


    public Mapper(ArrayList<PassengerData> passDataList,List<AirportData> airDataList,int jobNo, String name){

        passengerDataList = passDataList;
        airportDataList = airDataList;
        job = jobNo;
        threadName = name;

        System.out.print("Creating " + threadName + "\n");
    }
    public void run(){      //main logic
        System.out.print("Running " + threadName + "\n");

        // get all airport codes in airportdatalist
        ArrayList<String> allAirportCodes = getAllAirportCodes(airportDataList);

        for(int i = 0; i < passengerDataList.size(); i++){ // for each line

            PassengerData passengerDataLine = passengerDataList.get(i);

            if(formatChecker(passengerDataLine)){ // checks all variables format is correct

                // perform objective tasks
                switch (job){
                    case 1:
                        // Determine the number of flights from each airport (i.e. Departures); include a list of any airports not used (use airdata as key)

                        if(allAirportCodes.contains(passengerDataLine.getOriginAirport())) // check for airport code inconsistencies
                        {
                                //hm.put(passengerDataLine.getOriginAirport(), passengerDataLine.getPassengerId());

                                // create a list of key,value pairs
                                Entry<String,String> originPassIdpair = new SimpleEntry<>(passengerDataLine.getOriginAirport(),passengerDataLine.getPassengerId());
                                orgAirport_passengerIdPairList.add(originPassIdpair);
                        }

                        break;

                    case 2:
                        // map flight id to rest of passenger data
                        // key: flight id. Values: flight data.

                        // convert epoch dates to new format
                        Integer departureInt =  passengerDataLine.getDepartureTime();
                        long departureEpoc = departureInt.longValue() * 1000;
                        Date departureDate = new Date(departureEpoc);
                        DateFormat format = new SimpleDateFormat("HH:mm:ss");
                        String departureTime = format.format(departureDate);

                        Calendar c = Calendar.getInstance();
                        c.setTime(departureDate);
                        c.add(Calendar.MINUTE,passengerDataLine.getTotalFlightTime());

                        Date arrivalDate = c.getTime();
                        String arrivalTime = format.format(arrivalDate);
                        String totalFlightTime = String.valueOf(passengerDataLine.getTotalFlightTime());

                        FlightData flightData = new FlightData(passengerDataLine.getPassengerId(),passengerDataLine.getOriginAirport(),passengerDataLine.getDestinationAirport(),departureTime,arrivalTime,totalFlightTime);

                        Entry<String,FlightData> flightIDPassDataPair = new SimpleEntry<>(passengerDataLine.getFlightId(),flightData);
                        flightID_passengerDataList.add(flightIDPassDataPair);

                    default:
                        break;

                }
            }


        }

//        System.out.print(orgAirport_passengerIdPairList.size());
//        System.out.print("\n");

    }

    public void start(){
        System.out.print("Starting " + threadName + "\n");
        if (t == null){
            t = new Thread(this, threadName);
            t.start();
        }
    }



    public Boolean formatChecker(PassengerData passLine) {


        if(!passLine.getPassengerId().matches("([A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z][A-Z][0-9])") || passLine.getPassengerId().length() != 10){
//            System.out.print("passenger data wrong format" + "\n");
//            System.out.print(passLine.getPassengerId() + "\n");

            return false;
        }
        if(!passLine.getFlightId().matches("([A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z])")|| passLine.getFlightId().length() != 8){
            //System.out.print("flight id wrong format");
            return false;
        }
        if(!passLine.getOriginAirport().matches("([A-Z][A-Z][A-Z])") || passLine.getOriginAirport().length() !=3){
//            System.out.print("origin airport data wrong format" + "\n");
//            System.out.print(passLine.getOriginAirport() + "\n");
            return false;
        }
        if(!passLine.getDestinationAirport().matches("([A-Z][A-Z][A-Z])") || passLine.getOriginAirport().length() !=3){
//            System.out.print("dest airport data wrong format" + "\n");
//            System.out.print(passLine.getOriginAirport() + "\n");
            return false;
        }

        else{
            return true;
        }
    }


    private ArrayList<String> getAllAirportCodes(List<AirportData> airData)
    {
        ArrayList<String> codes = new ArrayList<String>();

        for (AirportData airObj : airData)
        {
            if(!codes.contains(airObj.getAirportCode())){
                codes.add(airObj.getAirportCode());
            }
        }

        return codes;
    }

    public List<Entry<String, String>> getOrgAirport_passengerIdPairList() {
        return orgAirport_passengerIdPairList;
    }

    public List<Entry<String, FlightData>> getFlightID_passengerDataList() {
        return flightID_passengerDataList;
    }

    //    public HashMap<String, String> getHm() {
//        return hm;
//    }
}

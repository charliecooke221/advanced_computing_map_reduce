package advcomp;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Map implements Runnable {

    private Thread t;
    private int job;
    private String threadName;
    private List<PassengerData> passengerDataList = new ArrayList<PassengerData>();
    private List<AirportData> airportDataList = new ArrayList<AirportData>();
    private HashMap<String, String> hm = new HashMap<String, String>();

    public Map(ArrayList<PassengerData> passDataList,List<AirportData> airDataList,int jobNo, String name){

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

                //System.out.print(passengerDataLine.getPassengerId() + "\n");

                // perform objective tasks
                switch (job){
                    case 1:
                        // Determine the number of flights from each airport (i.e. Departures); include a list of any airports not used (use airdata as key)

                        if(allAirportCodes.contains(passengerDataLine.getOriginAirport())) // check for airport code inconsistencies
                        {
                                hm.put(passengerDataLine.getOriginAirport(), passengerDataLine.getPassengerId());
                        }

                        break;

                    default:
                        break;


                }
            }


        }

        System.out.print(hm);

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
            System.out.print("passenger data wrong format" + "\n");
            System.out.print(passLine.getPassengerId() + "\n");
            return false;
        }

        if(!passLine.getOriginAirport().matches("([A-Z][A-Z][A-Z])") || passLine.getOriginAirport().length() !=3){
            System.out.print("origin airport data wrong format" + "\n");
            System.out.print(passLine.getOriginAirport() + "\n");
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

    public HashMap<String, String> getHm() {
        return hm;
    }
}

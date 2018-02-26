package advcomp;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;

public class MasterControlNode {

    int job = 3;
    int threadCount = 4;
    private ArrayList<PassengerData> passengerDataList;
    private ArrayList<AirportData> airportDataList;


    public MasterControlNode(ArrayList<PassengerData> passDataList, ArrayList<AirportData> airDataList){
        passengerDataList = passDataList;
        airportDataList = airDataList;
    }

    public void MapReduce() {

        ArrayList<ArrayList<PassengerData>> dataChunks = SplitPassengerArrayListIntoChunks(passengerDataList, threadCount);

        ArrayList<Mapper> mappers = new ArrayList<>();
        ArrayList<Thread> mappersThreads = new ArrayList<>();
        ArrayList<Combiner> combiners = new ArrayList<>();

        for(int i = 0; i < threadCount; i++){  // instatiate mapper threads

            ArrayList<PassengerData> tempPassengerDataArrayList = dataChunks.get(i);

            String threadName = String.format("Mapper%d", i);
            Mapper map = new Mapper(tempPassengerDataArrayList, airportDataList,job,threadName);
            Thread mapThread = new Thread(map);
            mapThread.start();
            mappers.add(map);

            Combiner combiner = new Combiner(job);

            mappersThreads.add(mapThread);
            combiners.add(combiner);
        }

        // run combiners as threads terminate
        for(int i = 0; i < mappersThreads.size(); i++){

            try {
               mappersThreads.get(i).join();
               switch (job){
                   case 1:
                       combiners.get(i).combineOriginAirportPassengerIdsHM(mappers.get(i).getOrgAirport_passengerIdPairList());

                   case 2:
                       combiners.get(i).combineFlightIDPassengerData(mappers.get(i).getFlightID_passengerDataList());

                   case 3:
                       combiners.get(i).combineOriginAirportPassengerIdsHM(mappers.get(i).getFlightID_passengerIdPairList());
               }

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        //create and run reducers for each primary key
        ArrayList<Reduce> reducers = new ArrayList<>();
        switch (job){

            case 1:  //objective1 each airport in airport data

                ArrayList<String> airportCodes = new ArrayList<>();


                // get each possible primary key
                for (AirportData airObj : airportDataList)
                {
                    if(!airportCodes.contains(airObj.getAirportCode())){
                        airportCodes.add(airObj.getAirportCode());
                    }
                }

                //create and run reducer for each primary key
                for(String orgAirport : airportCodes){
                    Reduce reduce = new Reduce(orgAirport,combiners,job);
                    reducers.add(reduce);
                }

                readPresentAndWriteReducersOutputCountJob(reducers,1);
                break;

            case 2: //objective2 each reducer is flightid get relevent flightdatas

                ArrayList<String> flightIdsJob1 = new ArrayList<>();

                for(PassengerData passdata: passengerDataList){
                    if(!flightIdsJob1.contains(passdata.getFlightId())){
                        flightIdsJob1.add(passdata.getFlightId());
                    }
                }

                for(String flightId : flightIdsJob1) {
                    Reduce reduce = new Reduce(flightId,combiners,job);
                    reducers.add(reduce);
                }

                readPresentAndWriteReducersOutputJob2(reducers);
                break;

            case 3: //task3 each reducer flightid count passengerids.

                ArrayList<String> flightIdsJob2 = new ArrayList<>();

                for(PassengerData passdata: passengerDataList){
                    if(!flightIdsJob2.contains(passdata.getFlightId())){
                        flightIdsJob2.add(passdata.getFlightId());
                    }
                }

                for(String flightId : flightIdsJob2){
                    Reduce reduce = new Reduce(flightId,combiners,1);
                    reducers.add(reduce);
                }
                readPresentAndWriteReducersOutputCountJob(reducers,3);
                break;

            default:
                break;

        }
    }


    public ArrayList<ArrayList<PassengerData>> SplitPassengerArrayListIntoChunks(ArrayList<PassengerData> passData, int noOfChunks)
    {
        int arrayLen = passData.size();
        int chunkSize = arrayLen / noOfChunks;

        ArrayList<ArrayList<PassengerData>> dataChunks = new ArrayList<ArrayList<PassengerData>>();

        int endIndex = chunkSize;
        int startIndex = 0;

        for (int i = 0; i < noOfChunks; i++){

            ArrayList<PassengerData> passengerDataListChunk = new ArrayList<PassengerData>(passData.subList(startIndex,endIndex ));
            dataChunks.add(passengerDataListChunk);

            startIndex = startIndex + chunkSize;
            endIndex = endIndex + chunkSize;
        }

        //System.out.print(dataChunks.get(0).size());
        return dataChunks;
    }

    public void readPresentAndWriteReducersOutputCountJob(ArrayList<Reduce> reducers,int jobId)
    {
        List<Map.Entry<String,Integer>> orgAirport_countList = new ArrayList<>();

        // get each outputJob1 from reducers
        for(Reduce reducer : reducers) {
            orgAirport_countList.add(reducer.getOutputJob1());
        }
        System.out.print(orgAirport_countList); // print reducers combined outputJob1

        // write combined outputJob1 to CSV
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("Output"+ jobId +".csv"));
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,Integer>  entry : orgAirport_countList) {
                sb.append(entry.getKey());
                sb.append(",");
                sb.append(entry.getValue());
                sb.append("\n");
            }
            br.write(sb.toString());
            br.close();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }

    public void readPresentAndWriteReducersOutputJob2(ArrayList<Reduce> reducers)
    {
        StringBuilder sb = new StringBuilder();
        for(Reduce reducer : reducers) {

                for(ArrayList<String> flight : reducer.getOutputJob2()){
                    System.out.print(flight + "\n"); // print each flight to console
                    for(String value : flight){
                        sb.append(value);
                        sb.append(",");
                    }
                    sb.append("\n");
                }
        }
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("Output2.csv"));
            br.write(sb.toString());
            br.close();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }



    }
}

package advcomp;

import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;

public class MasterControlNode {

    int job = 1;
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

        for(int i = 0; i < threadCount; i++){

            ArrayList<PassengerData> tempPassengerDataArrayList = dataChunks.get(i);

            String threadName = String.format("Mapper%d", i);

            Mapper map = new Mapper(tempPassengerDataArrayList, airportDataList,job,threadName);

            Thread mapThread = new Thread(map);

            mapThread.start();

            mappers.add(map);

            Combiner combiner = new Combiner(job);

            mappersThreads.add(mapThread);
            combiners.add(combiner);

            //mappers.add(new Map(tempPassengerDataArrayList,))

            // instantiate new map objects for thread count
        }

        // combine results
        for(int i = 0; i < mappersThreads.size(); i++){

            try {

               mappersThreads.get(i).join();
               combiners.get(i).combineOriginAirportPassengerIdsHM(mappers.get(i).getOrgAirport_passengerIdPairList());

            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        //create reducers for each primary key


        switch (job){
            case 1:  //objective1 each airport in airport data

                ArrayList<String> airportCodes = new ArrayList<>();
                ArrayList<Reduce> reducers = new ArrayList<>();

                // get each possible primary key
                for (AirportData airObj : airportDataList)
                {
                    if(!airportCodes.contains(airObj.getAirportCode())){
                        airportCodes.add(airObj.getAirportCode());
                    }
                }

                //create and run reducer for each primary key
                for(String orgAirport : airportCodes){
                    Reduce reduce = new Reduce(orgAirport,combiners);
                    reducers.add(reduce);
                }

                readPresentAndWriteReducersOutput(reducers);

            case 2: //objective2 each flight id

            case 3: //task3 flight id
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

    public void readPresentAndWriteReducersOutput(ArrayList<Reduce> reducers)
    {
        List<Map.Entry<String,Integer>> orgAirport_countList = new ArrayList<>();

        // get each output from reducers
        for(Reduce reducer : reducers) {
            orgAirport_countList.add(reducer.getOutput());
        }
        System.out.print(orgAirport_countList); // print reducers combined output

        // write combined output to CSV
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("Output.csv"));
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
}

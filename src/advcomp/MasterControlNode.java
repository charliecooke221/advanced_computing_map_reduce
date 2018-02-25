package advcomp;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

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


        ArrayList<PassengerData> test1 = dataChunks.get(1);

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

        for(int i = 0; i < mappersThreads.size(); i++){

            try {

               mappersThreads.get(i).join();

               combiners.get(i).combineOriginAirportPassengerIdsHM(mappers.get(i).getOrgAirport_passengerIdPairList());


            }
            catch (InterruptedException e){
                e.printStackTrace();
            }


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
}
